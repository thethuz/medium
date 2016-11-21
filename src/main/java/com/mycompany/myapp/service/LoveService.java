package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Love;
import com.mycompany.myapp.repository.LoveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Love.
 */
@Service
@Transactional
public class LoveService {

    private final Logger log = LoggerFactory.getLogger(LoveService.class);
    
    @Inject
    private LoveRepository loveRepository;

    /**
     * Save a love.
     *
     * @param love the entity to save
     * @return the persisted entity
     */
    public Love save(Love love) {
        log.debug("Request to save Love : {}", love);
        Love result = loveRepository.save(love);
        return result;
    }

    /**
     *  Get all the loves.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Love> findAll() {
        log.debug("Request to get all Loves");
        List<Love> result = loveRepository.findAll();

        return result;
    }

    /**
     *  Get one love by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Love findOne(Long id) {
        log.debug("Request to get Love : {}", id);
        Love love = loveRepository.findOne(id);
        return love;
    }

    /**
     *  Delete the  love by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Love : {}", id);
        loveRepository.delete(id);
    }
}
