package br.org.ons.platform.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.platform.domain.Record;
import br.org.ons.platform.repository.RecordRepository;
import br.org.ons.platform.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Record.
 */
@RestController
@RequestMapping("/api")
public class RecordResource {

	private static final Logger LOG = LoggerFactory.getLogger(RecordResource.class);
        
	private static final String RECORD_ENTITY = "record";
	
    private RecordRepository recordRepository;
    
    @Inject
    public RecordResource(RecordRepository recordRepository) {
		this.recordRepository = recordRepository;
	}

	/**
     * POST  /records : Create a new record.
     *
     * @param record the record to create
     * @return the ResponseEntity with status 201 (Created) and with body the new record, or with status 400 (Bad Request) if the record has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/records",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Record> createRecord(@RequestBody Record record) throws URISyntaxException {
        LOG.debug("REST request to save Record : {}", record);
        Record result = recordRepository.save(record);
        return ResponseEntity.created(new URI("/api/records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(RECORD_ENTITY, result.getId()))
            .body(result);
    }

    /**
     * PUT  /records : Updates an existing record.
     *
     * @param record the record to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated record,
     * or with status 400 (Bad Request) if the record is not valid,
     * or with status 500 (Internal Server Error) if the record couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/records",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Record> updateRecord(@RequestBody Record record) throws URISyntaxException {
        LOG.debug("REST request to update Record : {}", record);
        if (record.getId() == null) {
            return ResponseEntity.badRequest().body(record);
        }
        Record result = recordRepository.save(record);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(RECORD_ENTITY, record.getId()))
            .body(result);
    }

    /**
     * GET  /records : get all the records.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of records in body
     */
    @RequestMapping(value = "/records",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Record> getAllRecords(@RequestParam(required=false) String timelineId) {
    	LOG.debug("REST request to get all Records: timelineId = {}", timelineId);
    	if (timelineId == null) {
    		return recordRepository.findAll();
    	}
    	return recordRepository.findByTimelineIdOrderByMinorVersion(timelineId);
    }

    /**
     * GET  /records/:id : get the "id" record.
     *
     * @param id the id of the record to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the record, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/records/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Record> getRecord(@PathVariable String id) {
        LOG.debug("REST request to get Record : {}", id);
        Record record = recordRepository.findOne(id);
        return Optional.ofNullable(record)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /records/:id : delete the "id" record.
     *
     * @param id the id of the record to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/records/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteRecord(@PathVariable String id) {
        LOG.debug("REST request to delete Record : {}", id);
        recordRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(RECORD_ENTITY, id)).build();
    }

}
