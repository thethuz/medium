package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Love;
import com.mycompany.myapp.service.LoveService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Love.
 */
@RestController
@RequestMapping("/api")
public class LoveResource {

    private final Logger log = LoggerFactory.getLogger(LoveResource.class);
        
    @Inject
    private LoveService loveService;

    /**
     * POST  /loves : Create a new love.
     *
     * @param love the love to create
     * @return the ResponseEntity with status 201 (Created) and with body the new love, or with status 400 (Bad Request) if the love has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/loves",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Love> createLove(@RequestBody Love love) throws URISyntaxException {
        log.debug("REST request to save Love : {}", love);
        if (love.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("love", "idexists", "A new love cannot already have an ID")).body(null);
        }
        Love result = loveService.save(love);
        return ResponseEntity.created(new URI("/api/loves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("love", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loves : Updates an existing love.
     *
     * @param love the love to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated love,
     * or with status 400 (Bad Request) if the love is not valid,
     * or with status 500 (Internal Server Error) if the love couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/loves",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Love> updateLove(@RequestBody Love love) throws URISyntaxException {
        log.debug("REST request to update Love : {}", love);
        if (love.getId() == null) {
            return createLove(love);
        }
        Love result = loveService.save(love);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("love", love.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loves : get all the loves.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of loves in body
     */
    @RequestMapping(value = "/loves",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Love> getAllLoves() {
        log.debug("REST request to get all Loves");
        return loveService.findAll();
    }

    /**
     * GET  /loves/:id : get the "id" love.
     *
     * @param id the id of the love to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the love, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/loves/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Love> getLove(@PathVariable Long id) {
        log.debug("REST request to get Love : {}", id);
        Love love = loveService.findOne(id);
        return Optional.ofNullable(love)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /loves/:id : delete the "id" love.
     *
     * @param id the id of the love to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/loves/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLove(@PathVariable Long id) {
        log.debug("REST request to delete Love : {}", id);
        loveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("love", id.toString())).build();
    }

}
