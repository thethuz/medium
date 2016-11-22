package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MediumApp;

import com.mycompany.myapp.domain.Story;
import com.mycompany.myapp.repository.StoryRepository;
import com.mycompany.myapp.service.StoryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StoryResource REST controller.
 *
 * @see StoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MediumApp.class)
public class StoryResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final String DEFAULT_CONTENT = "AAAAA";
    private static final String UPDATED_CONTENT = "BBBBB";
    private static final String DEFAULT_AUTHOR_NAME = "AAAAA";
    private static final String UPDATED_AUTHOR_NAME = "BBBBB";

    private static final ZonedDateTime DEFAULT_TIME_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TIME_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TIME_CREATED_STR = dateTimeFormatter.format(DEFAULT_TIME_CREATED);
    private static final String DEFAULT_PLACE_CREATED = "AAAAA";
    private static final String UPDATED_PLACE_CREATED = "BBBBB";

    private static final Integer DEFAULT_TIME_TO_READ = 3;
    private static final Integer UPDATED_TIME_TO_READ = 2;
    private static final String DEFAULT_CATEGORY = "AAAAA";
    private static final String UPDATED_CATEGORY = "BBBBB";

    private static final Integer DEFAULT_NUMBER_OF_LOVE = 1;
    private static final Integer UPDATED_NUMBER_OF_LOVE = 2;

    private static final Integer DEFAULT_NUMBER_OF_COMMENT = 1;
    private static final Integer UPDATED_NUMBER_OF_COMMENT = 2;
    private static final String DEFAULT_AUTHOR = "AAAAA";
    private static final String UPDATED_AUTHOR = "BBBBB";

    @Inject
    private StoryRepository storyRepository;

    @Inject
    private StoryService storyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStoryMockMvc;

    private Story story;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StoryResource storyResource = new StoryResource();
        ReflectionTestUtils.setField(storyResource, "storyService", storyService);
        this.restStoryMockMvc = MockMvcBuilders.standaloneSetup(storyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Story createEntity(EntityManager em) {
        Story story = new Story()
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .authorName(DEFAULT_AUTHOR_NAME)
                .timeCreated(DEFAULT_TIME_CREATED)
                .placeCreated(DEFAULT_PLACE_CREATED)
                .timeToRead(DEFAULT_TIME_TO_READ)
                .category(DEFAULT_CATEGORY)
                .numberOfLove(DEFAULT_NUMBER_OF_LOVE)
                .numberOfComment(DEFAULT_NUMBER_OF_COMMENT)
                .author(DEFAULT_AUTHOR);
        return story;
    }

    @Before
    public void initTest() {
        story = createEntity(em);
    }

    @Test
    @Transactional
    public void createStory() throws Exception {
        int databaseSizeBeforeCreate = storyRepository.findAll().size();

        // Create the Story

        restStoryMockMvc.perform(post("/api/stories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(story)))
                .andExpect(status().isCreated());

        // Validate the Story in the database
        List<Story> stories = storyRepository.findAll();
        assertThat(stories).hasSize(databaseSizeBeforeCreate + 1);
        Story testStory = stories.get(stories.size() - 1);
        assertThat(testStory.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testStory.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testStory.getAuthorName()).isEqualTo(DEFAULT_AUTHOR_NAME);
        assertThat(testStory.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testStory.getPlaceCreated()).isEqualTo(DEFAULT_PLACE_CREATED);
        assertThat(testStory.getTimeToRead()).isEqualTo(DEFAULT_TIME_TO_READ);
        assertThat(testStory.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testStory.getNumberOfLove()).isEqualTo(DEFAULT_NUMBER_OF_LOVE);
        assertThat(testStory.getNumberOfComment()).isEqualTo(DEFAULT_NUMBER_OF_COMMENT);
        assertThat(testStory.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
    }

    @Test
    @Transactional
    public void getAllStories() throws Exception {
        // Initialize the database
        storyRepository.saveAndFlush(story);

        // Get all the stories
        restStoryMockMvc.perform(get("/api/stories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(story.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].authorName").value(hasItem(DEFAULT_AUTHOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(DEFAULT_TIME_CREATED_STR)))
                .andExpect(jsonPath("$.[*].placeCreated").value(hasItem(DEFAULT_PLACE_CREATED.toString())))
                .andExpect(jsonPath("$.[*].timeToRead").value(hasItem(DEFAULT_TIME_TO_READ)))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].numberOfLove").value(hasItem(DEFAULT_NUMBER_OF_LOVE)))
                .andExpect(jsonPath("$.[*].numberOfComment").value(hasItem(DEFAULT_NUMBER_OF_COMMENT)))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())));
    }

    @Test
    @Transactional
    public void getStory() throws Exception {
        // Initialize the database
        storyRepository.saveAndFlush(story);

        // Get the story
        restStoryMockMvc.perform(get("/api/stories/{id}", story.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(story.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.authorName").value(DEFAULT_AUTHOR_NAME.toString()))
            .andExpect(jsonPath("$.timeCreated").value(DEFAULT_TIME_CREATED_STR))
            .andExpect(jsonPath("$.placeCreated").value(DEFAULT_PLACE_CREATED.toString()))
            .andExpect(jsonPath("$.timeToRead").value(DEFAULT_TIME_TO_READ))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.numberOfLove").value(DEFAULT_NUMBER_OF_LOVE))
            .andExpect(jsonPath("$.numberOfComment").value(DEFAULT_NUMBER_OF_COMMENT))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStory() throws Exception {
        // Get the story
        restStoryMockMvc.perform(get("/api/stories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStory() throws Exception {
        // Initialize the database
        storyService.save(story);

        int databaseSizeBeforeUpdate = storyRepository.findAll().size();

        // Update the story
        Story updatedStory = storyRepository.findOne(story.getId());
        updatedStory
                .title(UPDATED_TITLE)
                .content(UPDATED_CONTENT)
                .authorName(UPDATED_AUTHOR_NAME)
                .timeCreated(UPDATED_TIME_CREATED)
                .placeCreated(UPDATED_PLACE_CREATED)
                .timeToRead(UPDATED_TIME_TO_READ)
                .category(UPDATED_CATEGORY)
                .numberOfLove(UPDATED_NUMBER_OF_LOVE)
                .numberOfComment(UPDATED_NUMBER_OF_COMMENT)
                .author(UPDATED_AUTHOR);

        restStoryMockMvc.perform(put("/api/stories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedStory)))
                .andExpect(status().isOk());

        // Validate the Story in the database
        List<Story> stories = storyRepository.findAll();
        assertThat(stories).hasSize(databaseSizeBeforeUpdate);
        Story testStory = stories.get(stories.size() - 1);
        assertThat(testStory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testStory.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testStory.getAuthorName()).isEqualTo(UPDATED_AUTHOR_NAME);
        assertThat(testStory.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testStory.getPlaceCreated()).isEqualTo(UPDATED_PLACE_CREATED);
        assertThat(testStory.getTimeToRead()).isEqualTo(UPDATED_TIME_TO_READ);
        assertThat(testStory.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testStory.getNumberOfLove()).isEqualTo(UPDATED_NUMBER_OF_LOVE);
        assertThat(testStory.getNumberOfComment()).isEqualTo(UPDATED_NUMBER_OF_COMMENT);
        assertThat(testStory.getAuthor()).isEqualTo(UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void deleteStory() throws Exception {
        // Initialize the database
        storyService.save(story);

        int databaseSizeBeforeDelete = storyRepository.findAll().size();

        // Get the story
        restStoryMockMvc.perform(delete("/api/stories/{id}", story.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Story> stories = storyRepository.findAll();
        assertThat(stories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
