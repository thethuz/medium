package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Story;
import com.mycompany.myapp.repository.StoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Story.
 */
@Service
@Transactional
public class StoryService {

    private final Logger log = LoggerFactory.getLogger(StoryService.class);
    
    @Inject
    private StoryRepository storyRepository;

    /**
     * Save a story.
     *
     * @param story the entity to save
     * @return the persisted entity
     */
    public Story save(Story story) {
        log.debug("Request to save Story : {}", story);
        Story result = storyRepository.save(story);
        return result;
    }

    /**
     *  Get all the stories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Story> findAll(Pageable pageable) {
        log.debug("Request to get all Stories");
        Page<Story> result = storyRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one story by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Story findOne(Long id) {
        log.debug("Request to get Story : {}", id);
        Story story = storyRepository.findOne(id);
        return story;
    }

    /**
     *  Delete the  story by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Story : {}", id);
        storyRepository.delete(id);
    }
}
