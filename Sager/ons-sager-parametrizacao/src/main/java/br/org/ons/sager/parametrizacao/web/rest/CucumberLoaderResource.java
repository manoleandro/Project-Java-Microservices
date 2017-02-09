package br.org.ons.sager.parametrizacao.web.rest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.keyvalue.core.KeyValueTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import br.org.ons.platform.common.util.IdGenerator;

@RestController
@RequestMapping("/api")
@Profile("cucumber")
public class CucumberLoaderResource {
	
	 private final Logger log = LoggerFactory.getLogger(CucumberLoaderResource.class);
	
	   @Inject
	    private ObjectMapper objectMapper;
	    
	    @Inject
	    private KeyValueTemplate keyValueTemplate;
	
	 @RequestMapping(value = "/load",
		        method = RequestMethod.POST,
		        produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Void> load(
	    		@RequestBody String jsonLoad, 
	    		@RequestParam String domainClassName){
	    	
	    	List<?> jsonArray;
				
				TypeFactory typeFactory = objectMapper.getTypeFactory();
				try {
					keyValueTemplate.delete(Class.forName(domainClassName));
					jsonArray = objectMapper.readValue(jsonLoad, typeFactory.constructCollectionType(List.class, Class.forName(domainClassName)) );
					for (Object object : jsonArray) {
						Method handle = Class.forName(domainClassName).getDeclaredMethod("setId", String.class);
						handle.invoke(object, IdGenerator.newId());
						keyValueTemplate.insert(object);
					}
				} catch (ClassNotFoundException | IOException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					log.error(e.getMessage());
					return ResponseEntity.badRequest().build();
				}
				
	    	 return ResponseEntity.ok().build();
	    	
	    }
	 
	 @RequestMapping(value = "/load-jsonFile",
		        method = RequestMethod.POST,
		        produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Void> loadJsonFiles(
			 	@RequestParam String jsonFileName, 
	    		@RequestParam String domainClassName){
	    	
	    	List<?> jsonArray;
				
				TypeFactory typeFactory = objectMapper.getTypeFactory();
				try {
					keyValueTemplate.delete(Class.forName(domainClassName));
					
					jsonArray = objectMapper.readValue(getClass().getResourceAsStream("/cucumber/"+jsonFileName),
							typeFactory.constructCollectionType(List.class, Class.forName(domainClassName)) );
					for (Object object : jsonArray) {
						Method handle = Class.forName(domainClassName).getDeclaredMethod("setId", String.class);
						handle.invoke(object, IdGenerator.newId());
						keyValueTemplate.insert(object);
					}
				} catch (ClassNotFoundException | IOException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					log.error(e.getMessage());
					return ResponseEntity.badRequest().build();
				}
				
	    	 return ResponseEntity.ok().build();
	    	
	    }

}
