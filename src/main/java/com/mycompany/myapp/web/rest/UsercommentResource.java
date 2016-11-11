package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Usercomment;
import com.mycompany.myapp.service.UsercommentService;
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
 * REST controller for managing Usercomment.
 */
@RestController
@RequestMapping("/api")
public class UsercommentResource {

    private final Logger log = LoggerFactory.getLogger(UsercommentResource.class);
        
    @Inject
    private UsercommentService usercommentService;

    /**
     * POST  /usercomments : Create a new usercomment.
     *
     * @param usercomment the usercomment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usercomment, or with status 400 (Bad Request) if the usercomment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/usercomments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Usercomment> createUsercomment(@RequestBody Usercomment usercomment) throws URISyntaxException {
        log.debug("REST request to save Usercomment : {}", usercomment);
        if (usercomment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("usercomment", "idexists", "A new usercomment cannot already have an ID")).body(null);
        }
        Usercomment result = usercommentService.save(usercomment);
        return ResponseEntity.created(new URI("/api/usercomments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("usercomment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /usercomments : Updates an existing usercomment.
     *
     * @param usercomment the usercomment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usercomment,
     * or with status 400 (Bad Request) if the usercomment is not valid,
     * or with status 500 (Internal Server Error) if the usercomment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/usercomments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Usercomment> updateUsercomment(@RequestBody Usercomment usercomment) throws URISyntaxException {
        log.debug("REST request to update Usercomment : {}", usercomment);
        if (usercomment.getId() == null) {
            return createUsercomment(usercomment);
        }
        Usercomment result = usercommentService.save(usercomment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("usercomment", usercomment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /usercomments : get all the usercomments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of usercomments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/usercomments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Usercomment>> getAllUsercomments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Usercomments");
        Page<Usercomment> page = usercommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/usercomments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /usercomments/:id : get the "id" usercomment.
     *
     * @param id the id of the usercomment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usercomment, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/usercomments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Usercomment> getUsercomment(@PathVariable Long id) {
        log.debug("REST request to get Usercomment : {}", id);
        Usercomment usercomment = usercommentService.findOne(id);
        return Optional.ofNullable(usercomment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /usercomments/:id : delete the "id" usercomment.
     *
     * @param id the id of the usercomment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/usercomments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUsercomment(@PathVariable Long id) {
        log.debug("REST request to delete Usercomment : {}", id);
        usercommentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("usercomment", id.toString())).build();
    }

}
