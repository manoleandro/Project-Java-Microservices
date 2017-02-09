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
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.platform.domain.Timeline;
import br.org.ons.platform.repository.TimelineRepository;
import br.org.ons.platform.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Timeline.
 */
@RestController
@RequestMapping("/api")
public class TimelineResource {

	private static final Logger LOG = LoggerFactory.getLogger(TimelineResource.class);
        
	private static final String TIMELINE_ENTITY = "timeline";
	
    private TimelineRepository timelineRepository;
    
    @Inject
    public TimelineResource(TimelineRepository timelineRepository) {
		this.timelineRepository = timelineRepository;
	}

	/**
     * POST  /timelines : Create a new timeline.
     *
     * @param timeline the timeline to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeline, or with status 400 (Bad Request) if the timeline has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/timelines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Timeline> createTimeline(@RequestBody Timeline timeline) throws URISyntaxException {
        LOG.debug("REST request to save Timeline : {}", timeline);
        Timeline result = timelineRepository.save(timeline);
        return ResponseEntity.created(new URI("/api/timelines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(TIMELINE_ENTITY, result.getId()))
            .body(result);
    }

    /**
     * PUT  /timelines : Updates an existing timeline.
     *
     * @param timeline the timeline to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeline,
     * or with status 400 (Bad Request) if the timeline is not valid,
     * or with status 500 (Internal Server Error) if the timeline couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/timelines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Timeline> updateTimeline(@RequestBody Timeline timeline) throws URISyntaxException {
        LOG.debug("REST request to update Timeline : {}", timeline);
        if (timeline.getId() == null) {
            return ResponseEntity.badRequest().body(timeline);
        }
        Timeline result = timelineRepository.save(timeline);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(TIMELINE_ENTITY, timeline.getId()))
            .body(result);
    }

    /**
     * GET  /timelines : get all the timelines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of timelines in body
     */
    @RequestMapping(value = "/timelines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Timeline> getAllTimelines() {
        LOG.debug("REST request to get all Timelines");
        return timelineRepository.findAll();
    }

    /**
     * GET  /timelines/:id : get the "id" timeline.
     *
     * @param id the id of the timeline to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeline, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/timelines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Timeline> getTimeline(@PathVariable String id) {
        LOG.debug("REST request to get Timeline : {}", id);
        Timeline timeline = timelineRepository.findOne(id);
        return Optional.ofNullable(timeline)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /timelines/:id : delete the "id" timeline.
     *
     * @param id the id of the timeline to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/timelines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteTimeline(@PathVariable String id) {
        LOG.debug("REST request to delete Timeline : {}", id);
        timelineRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(TIMELINE_ENTITY, id)).build();
    }

}
