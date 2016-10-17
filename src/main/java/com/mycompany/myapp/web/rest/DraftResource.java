package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Draft;

import com.mycompany.myapp.repository.DraftRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Draft.
 */
@RestController
@RequestMapping("/api")
public class DraftResource {

    private final Logger log = LoggerFactory.getLogger(DraftResource.class);
        
    @Inject
    private DraftRepository draftRepository;

    /**
     * POST  /drafts : Create a new draft.
     *
     * @param draft the draft to create
     * @return the ResponseEntity with status 201 (Created) and with body the new draft, or with status 400 (Bad Request) if the draft has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/drafts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Draft> createDraft(@RequestBody Draft draft) throws URISyntaxException {
        log.debug("REST request to save Draft : {}", draft);
        if (draft.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("draft", "idexists", "A new draft cannot already have an ID")).body(null);
        }
        Draft result = draftRepository.save(draft);
        return ResponseEntity.created(new URI("/api/drafts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("draft", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drafts : Updates an existing draft.
     *
     * @param draft the draft to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated draft,
     * or with status 400 (Bad Request) if the draft is not valid,
     * or with status 500 (Internal Server Error) if the draft couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/drafts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Draft> updateDraft(@RequestBody Draft draft) throws URISyntaxException {
        log.debug("REST request to update Draft : {}", draft);
        if (draft.getId() == null) {
            return createDraft(draft);
        }
        Draft result = draftRepository.save(draft);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("draft", draft.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drafts : get all the drafts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of drafts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/drafts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Draft>> getAllDrafts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Drafts");
        Page<Draft> page = draftRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/drafts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /drafts/:id : get the "id" draft.
     *
     * @param id the id of the draft to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the draft, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/drafts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Draft> getDraft(@PathVariable Long id) {
        log.debug("REST request to get Draft : {}", id);
        Draft draft = draftRepository.findOne(id);
        return Optional.ofNullable(draft)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /drafts/:id : delete the "id" draft.
     *
     * @param id the id of the draft to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/drafts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDraft(@PathVariable Long id) {
        log.debug("REST request to delete Draft : {}", id);
        draftRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("draft", id.toString())).build();
    }

}
