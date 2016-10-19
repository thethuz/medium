package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Story;
import com.mycompany.myapp.service.StoryService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Story.
 */
@RestController
@RequestMapping("/api")
public class StoryResource {

    private final Logger log = LoggerFactory.getLogger(StoryResource.class);

    @Inject
    private StoryService storyService;

    /**
     * POST  /stories : Create a new story.
     *
     * @param story the story to create
     * @return the ResponseEntity with status 201 (Created) and with body the new story, or with status 400 (Bad Request) if the story has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stories",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Story> createStory(@Valid @RequestBody Story story) throws URISyntaxException {
        log.debug("REST request to save Story : {}", story);
        if (story.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("story", "idexists", "A new story cannot already have an ID")).body(null);
        }
        Story result = storyService.save(story);
        return ResponseEntity.created(new URI("/api/stories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("story", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stories : Updates an existing story.
     *
     * @param story the story to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated story,
     * or with status 400 (Bad Request) if the story is not valid,
     * or with status 500 (Internal Server Error) if the story couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stories",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Story> updateStory(@Valid @RequestBody Story story) throws URISyntaxException {
        log.debug("REST request to update Story : {}", story);
        if (story.getId() == null) {
            return createStory(story);
        }
        Story result = storyService.save(story);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("story", story.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stories : get all the stories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of stories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/stories",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Story>> getAllStories(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Stories");
        Page<Story> page = storyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /stories/:id : get the "id" story.
     *
     * @param id the id of the story to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the story, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/stories/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Story> getStory(@PathVariable Long id) {
        log.debug("REST request to get Story : {}", id);
        Story story = storyService.findOne(id);
        return Optional.ofNullable(story)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stories/:id : delete the "id" story.
     *
     * @param id the id of the story to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/stories/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        log.debug("REST request to delete Story : {}", id);
        storyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("story", id.toString())).build();
    }

}
