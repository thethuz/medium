package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MediumApp;

import com.mycompany.myapp.domain.Love;
import com.mycompany.myapp.repository.LoveRepository;
import com.mycompany.myapp.service.LoveService;

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
 * Test class for the LoveResource REST controller.
 *
 * @see LoveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MediumApp.class)
public class LoveResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Long DEFAULT_STORY_ID = 1L;
    private static final Long UPDATED_STORY_ID = 2L;

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;
    private static final String DEFAULT_USER_NAME = "AAAAA";
    private static final String UPDATED_USER_NAME = "BBBBB";

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TIME_STR = dateTimeFormatter.format(DEFAULT_TIME);

    @Inject
    private LoveRepository loveRepository;

    @Inject
    private LoveService loveService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLoveMockMvc;

    private Love love;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LoveResource loveResource = new LoveResource();
        ReflectionTestUtils.setField(loveResource, "loveService", loveService);
        this.restLoveMockMvc = MockMvcBuilders.standaloneSetup(loveResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Love createEntity(EntityManager em) {
        Love love = new Love()
                .storyId(DEFAULT_STORY_ID)
                .userId(DEFAULT_USER_ID)
                .userName(DEFAULT_USER_NAME)
                .time(DEFAULT_TIME);
        return love;
    }

    @Before
    public void initTest() {
        love = createEntity(em);
    }

    @Test
    @Transactional
    public void createLove() throws Exception {
        int databaseSizeBeforeCreate = loveRepository.findAll().size();

        // Create the Love

        restLoveMockMvc.perform(post("/api/loves")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(love)))
                .andExpect(status().isCreated());

        // Validate the Love in the database
        List<Love> loves = loveRepository.findAll();
        assertThat(loves).hasSize(databaseSizeBeforeCreate + 1);
        Love testLove = loves.get(loves.size() - 1);
        assertThat(testLove.getStoryId()).isEqualTo(DEFAULT_STORY_ID);
        assertThat(testLove.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testLove.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testLove.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void getAllLoves() throws Exception {
        // Initialize the database
        loveRepository.saveAndFlush(love);

        // Get all the loves
        restLoveMockMvc.perform(get("/api/loves?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(love.getId().intValue())))
                .andExpect(jsonPath("$.[*].storyId").value(hasItem(DEFAULT_STORY_ID.intValue())))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
                .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
                .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME_STR)));
    }

    @Test
    @Transactional
    public void getLove() throws Exception {
        // Initialize the database
        loveRepository.saveAndFlush(love);

        // Get the love
        restLoveMockMvc.perform(get("/api/loves/{id}", love.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(love.getId().intValue()))
            .andExpect(jsonPath("$.storyId").value(DEFAULT_STORY_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingLove() throws Exception {
        // Get the love
        restLoveMockMvc.perform(get("/api/loves/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLove() throws Exception {
        // Initialize the database
        loveService.save(love);

        int databaseSizeBeforeUpdate = loveRepository.findAll().size();

        // Update the love
        Love updatedLove = loveRepository.findOne(love.getId());
        updatedLove
                .storyId(UPDATED_STORY_ID)
                .userId(UPDATED_USER_ID)
                .userName(UPDATED_USER_NAME)
                .time(UPDATED_TIME);

        restLoveMockMvc.perform(put("/api/loves")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLove)))
                .andExpect(status().isOk());

        // Validate the Love in the database
        List<Love> loves = loveRepository.findAll();
        assertThat(loves).hasSize(databaseSizeBeforeUpdate);
        Love testLove = loves.get(loves.size() - 1);
        assertThat(testLove.getStoryId()).isEqualTo(UPDATED_STORY_ID);
        assertThat(testLove.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testLove.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testLove.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteLove() throws Exception {
        // Initialize the database
        loveService.save(love);

        int databaseSizeBeforeDelete = loveRepository.findAll().size();

        // Get the love
        restLoveMockMvc.perform(delete("/api/loves/{id}", love.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Love> loves = loveRepository.findAll();
        assertThat(loves).hasSize(databaseSizeBeforeDelete - 1);
    }
}
