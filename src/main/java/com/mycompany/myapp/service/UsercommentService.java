package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Usercomment;
import com.mycompany.myapp.repository.UsercommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Usercomment.
 */
@Service
@Transactional
public class UsercommentService {

    private final Logger log = LoggerFactory.getLogger(UsercommentService.class);
    
    @Inject
    private UsercommentRepository usercommentRepository;

    /**
     * Save a usercomment.
     *
     * @param usercomment the entity to save
     * @return the persisted entity
     */
    public Usercomment save(Usercomment usercomment) {
        log.debug("Request to save Usercomment : {}", usercomment);
        Usercomment result = usercommentRepository.save(usercomment);
        return result;
    }

    /**
     *  Get all the usercomments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Usercomment> findAll(Pageable pageable) {
        log.debug("Request to get all Usercomments");
        Page<Usercomment> result = usercommentRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one usercomment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Usercomment findOne(Long id) {
        log.debug("Request to get Usercomment : {}", id);
        Usercomment usercomment = usercommentRepository.findOne(id);
        return usercomment;
    }

    /**
     *  Delete the  usercomment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Usercomment : {}", id);
        usercommentRepository.delete(id);
    }
}
