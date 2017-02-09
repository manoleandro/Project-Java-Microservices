package br.org.ons.exemplo.web.rest;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.exemplo.common.Apuracao;
import br.org.ons.exemplo.common.AtualizarUsinaCommand;
import br.org.ons.exemplo.common.CriarUsinaCommand;
import br.org.ons.exemplo.common.ExcluirUsinaCommand;
import br.org.ons.exemplo.common.Usina;
import br.org.ons.exemplo.config.mq.CommandBus;
import br.org.ons.exemplo.domain.CadastroUsina;
import br.org.ons.exemplo.repository.CadastroUsinaRepository;
import br.org.ons.exemplo.web.rest.util.HeaderUtil;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.platform.common.util.IdGenerator;

/**
 * REST controller for managing CadastroUsina.
 */
@RestController
@RequestMapping("/api")
public class CadastroUsinaResource {

	private static final String CADASTRO_USINA = "cadastroUsina";

	private final Logger log = LoggerFactory.getLogger(CadastroUsinaResource.class);
        
    @Inject
    private CadastroUsinaRepository cadastroUsinaRepository;
    
    @Inject
    private CommandBus commandBus; 

	@Inject
	private ObjectMapper objectMapper;

    /**
     * POST  /cadastro-usinas : Create a new cadastroUsina.
     *
     * @param cadastroUsina the cadastroUsina to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cadastroUsina, or with status 400 (Bad Request) if the cadastroUsina has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cadastro-usinas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCadastroUsina(@RequestBody CadastroUsina cadastroUsina) throws URISyntaxException {
        log.debug("REST request to save CadastroUsina : {}", cadastroUsina);
        if (cadastroUsina.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(CADASTRO_USINA, "idexists", "A new cadastroUsina cannot already have an ID")).body(null);
        }
        Usina usina = new Usina();
        usina.setId(IdGenerator.newId());
        usina.setNomeCurto(cadastroUsina.getNomeCurto());
        usina.setTipoUsina(cadastroUsina.getTipoUsina());
        usina.setPotenciaCalculo(cadastroUsina.getPotenciaCalculo());
        CriarUsinaCommand command = new CriarUsinaCommand();
        command.setUsina(usina);
        
        CommandMessage<CriarUsinaCommand> commandMessage = new CommandMessage<>();
        commandMessage.setCommand(command);
        commandMessage.getMetadata().setId(IdGenerator.newId());
        commandMessage.getMetadata().setCorrelationId(null); // Root command
        commandMessage.getMetadata().setAggregateId(usina.getId());
		commandMessage.getMetadata().setMajorVersion(null); // Default timeline
		commandMessage.getMetadata().setMinorVersion(0L); // First Version
        commandMessage.getMetadata().setTimelineDate(ZonedDateTime.now());
        
		ResultMessage<?> result = commandBus.sendAndWait(commandMessage);
        log.debug("createCadastroUsina.ResultMessage : {}", result);
        
        return ResponseEntity.status(result.getStatusCode()).build();
    }

    /**
     * PUT  /cadastro-usinas : Updates an existing cadastroUsina.
     *
     * @param cadastroUsina the cadastroUsina to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cadastroUsina,
     * or with status 400 (Bad Request) if the cadastroUsina is not valid,
     * or with status 500 (Internal Server Error) if the cadastroUsina couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cadastro-usinas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCadastroUsina(@RequestBody CadastroUsina cadastroUsina) throws URISyntaxException {
        log.debug("REST request to update CadastroUsina : {}", cadastroUsina);
        if (cadastroUsina.getId() == null) {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(CADASTRO_USINA, "idnull", "The ID for the cadastroUsina cannot be null")).body(null);
        }
        Usina usina = new Usina();
        usina.setId(cadastroUsina.getId());
        usina.setNomeCurto(cadastroUsina.getNomeCurto());
        usina.setTipoUsina(cadastroUsina.getTipoUsina());
        usina.setPotenciaCalculo(cadastroUsina.getPotenciaCalculo());
        AtualizarUsinaCommand command = new AtualizarUsinaCommand();
        command.setUsina(usina);
        
        CommandMessage<AtualizarUsinaCommand> commandMessage = new CommandMessage<>();
        commandMessage.setCommand(command);
        commandMessage.getMetadata().setId(IdGenerator.newId());
        commandMessage.getMetadata().setCorrelationId(null); // Root command
		commandMessage.getMetadata().setAggregateId(usina.getId());
		commandMessage.getMetadata().setMajorVersion(Long.valueOf(cadastroUsina.getVersion().split("\\.")[0]));
		commandMessage.getMetadata().setMinorVersion(Long.valueOf(cadastroUsina.getVersion().split("\\.")[1]));
        commandMessage.getMetadata().setTimelineDate(ZonedDateTime.now());

		ResultMessage<?> result = commandBus.sendAndWait(commandMessage);
        log.debug("updateCadastroUsina.ResultMessage : {}", result);
        
        return ResponseEntity.status(result.getStatusCode()).build();
    }

    /**
     * GET  /cadastro-usinas : get all the cadastroUsinas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cadastroUsinas in body
     */
    @RequestMapping(value = "/cadastro-usinas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CadastroUsina> getAllCadastroUsinas() {
        log.debug("REST request to get all CadastroUsinas");
        return IterableConverter.toList(cadastroUsinaRepository.findAll());
    }

    /**
     * GET  /cadastro-usinas/:id : get the "id" cadastroUsina.
     *
     * @param id the id of the cadastroUsina to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cadastroUsina, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cadastro-usinas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CadastroUsina> getCadastroUsina(@PathVariable String id) {
        log.debug("REST request to get CadastroUsina : {}", id);
        CadastroUsina cadastroUsina = cadastroUsinaRepository.findOne(id);
        return Optional.ofNullable(cadastroUsina)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cadastro-usinas/:id : delete the "id" cadastroUsina.
     *
     * @param id the id of the cadastroUsina to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cadastro-usinas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCadastroUsina(@PathVariable String id) {
        log.debug("REST request to delete CadastroUsina : {}", id);
        ExcluirUsinaCommand command = new ExcluirUsinaCommand();
        
        CommandMessage<ExcluirUsinaCommand> commandMessage = new CommandMessage<>();
        commandMessage.setCommand(command);
        commandMessage.getMetadata().setId(IdGenerator.newId());
        commandMessage.getMetadata().setCorrelationId(null); // Root command
        commandMessage.getMetadata().setAggregateId(id);
		commandMessage.getMetadata()
				.setMajorVersion(Long.valueOf(cadastroUsinaRepository.findOne(id).getVersion().split("\\.")[0]));
		commandMessage.getMetadata()
				.setMinorVersion(Long.valueOf(cadastroUsinaRepository.findOne(id).getVersion().split("\\.")[1]));
        commandMessage.getMetadata().setTimelineDate(ZonedDateTime.now());

		ResultMessage<?> result = commandBus.sendAndWait(commandMessage);
        log.debug("deleteCadastroUsina.ResultMessage : {}", result);
        
        return ResponseEntity.status(result.getStatusCode()).build();
    }
    
	@RequestMapping(value = "/cadastro-usinas/{id}/apuracoes", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Apuracao>> getApuracoes(@PathVariable String id) {
		log.debug("REST request to get Apuracoes : {}", id);
		CadastroUsina cadastroUsina = cadastroUsinaRepository.findOne(id);
		List<Apuracao> apuracoes = null;
		try {
			log.debug("CADASTRO {}", cadastroUsina);
			log.debug("APURACOES {}", cadastroUsina.getApuracoes());
			apuracoes = objectMapper.readValue(cadastroUsina.getApuracoes(), new TypeReference<List<Apuracao>>() {});
		} catch (Exception e) {
			log.error("Erro ao obter apurações", e);
		}
		return Optional.ofNullable(apuracoes).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
