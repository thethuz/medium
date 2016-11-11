package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MediumApp;

import com.mycompany.myapp.domain.Usercomment;
import com.mycompany.myapp.repository.UsercommentRepository;
import com.mycompany.myapp.service.UsercommentService;

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
 * Test class for the UsercommentResource REST controller.
 *
 * @see UsercommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MediumApp.class)
public class UsercommentResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Integer DEFAULT_STORY_ID = 1;
    private static final Integer UPDATED_STORY_ID = 2;

    private static final String DEFAULT_COMMENT_CONTENT = "";
    private static final String UPDATED_COMMENT_CONTENT = "";

    private static final Integer DEFAULT_USER_COMMENT_ID = 1;
    private static final Integer UPDATED_USER_COMMENT_ID = 2;
    private static final String DEFAULT_USER_COMMENT_NAME = "AAAAA";
    private static final String UPDATED_USER_COMMENT_NAME = "BBBBB";

    private static final ZonedDateTime DEFAULT_TIME_COMMENTED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TIME_COMMENTED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TIME_COMMENTED_STR = dateTimeFormatter.format(DEFAULT_TIME_COMMENTED);

    @Inject
    private UsercommentRepository usercommentRepository;

    @Inject
    private UsercommentService usercommentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUsercommentMockMvc;

    private Usercomment usercomment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UsercommentResource usercommentResource = new UsercommentResource();
        ReflectionTestUtils.setField(usercommentResource, "usercommentService", usercommentService);
        this.restUsercommentMockMvc = MockMvcBuilders.standaloneSetup(usercommentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usercomment createEntity(EntityManager em) {
        Usercomment usercomment = new Usercomment()
                .storyID(DEFAULT_STORY_ID)
                .commentContent(DEFAULT_COMMENT_CONTENT)
                .userCommentID(DEFAULT_USER_COMMENT_ID)
                .userCommentName(DEFAULT_USER_COMMENT_NAME)
                .timeCommented(DEFAULT_TIME_COMMENTED);
        return usercomment;
    }

    @Before
    public void initTest() {
        usercomment = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsercomment() throws Exception {
        int databaseSizeBeforeCreate = usercommentRepository.findAll().size();

        // Create the Usercomment

        restUsercommentMockMvc.perform(post("/api/usercomments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usercomment)))
                .andExpect(status().isCreated());

        // Validate the Usercomment in the database
        List<Usercomment> usercomments = usercommentRepository.findAll();
        assertThat(usercomments).hasSize(databaseSizeBeforeCreate + 1);
        Usercomment testUsercomment = usercomments.get(usercomments.size() - 1);
        assertThat(testUsercomment.getStoryID()).isEqualTo(DEFAULT_STORY_ID);
        assertThat(testUsercomment.getCommentContent()).isEqualTo(DEFAULT_COMMENT_CONTENT);
        assertThat(testUsercomment.getUserCommentID()).isEqualTo(DEFAULT_USER_COMMENT_ID);
        assertThat(testUsercomment.getUserCommentName()).isEqualTo(DEFAULT_USER_COMMENT_NAME);
        assertThat(testUsercomment.getTimeCommented()).isEqualTo(DEFAULT_TIME_COMMENTED);
    }

    @Test
    @Transactional
    public void getAllUsercomments() throws Exception {
        // Initialize the database
        usercommentRepository.saveAndFlush(usercomment);

        // Get all the usercomments
        restUsercommentMockMvc.perform(get("/api/usercomments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(usercomment.getId().intValue())))
                .andExpect(jsonPath("$.[*].storyID").value(hasItem(DEFAULT_STORY_ID)))
                .andExpect(jsonPath("$.[*].commentContent").value(hasItem(DEFAULT_COMMENT_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].userCommentID").value(hasItem(DEFAULT_USER_COMMENT_ID)))
                .andExpect(jsonPath("$.[*].userCommentName").value(hasItem(DEFAULT_USER_COMMENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].timeCommented").value(hasItem(DEFAULT_TIME_COMMENTED_STR)));
    }

    @Test
    @Transactional
    public void getUsercomment() throws Exception {
        // Initialize the database
        usercommentRepository.saveAndFlush(usercomment);

        // Get the usercomment
        restUsercommentMockMvc.perform(get("/api/usercomments/{id}", usercomment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usercomment.getId().intValue()))
            .andExpect(jsonPath("$.storyID").value(DEFAULT_STORY_ID))
            .andExpect(jsonPath("$.commentContent").value(DEFAULT_COMMENT_CONTENT.toString()))
            .andExpect(jsonPath("$.userCommentID").value(DEFAULT_USER_COMMENT_ID))
            .andExpect(jsonPath("$.userCommentName").value(DEFAULT_USER_COMMENT_NAME.toString()))
            .andExpect(jsonPath("$.timeCommented").value(DEFAULT_TIME_COMMENTED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingUsercomment() throws Exception {
        // Get the usercomment
        restUsercommentMockMvc.perform(get("/api/usercomments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsercomment() throws Exception {
        // Initialize the database
        usercommentService.save(usercomment);

        int databaseSizeBeforeUpdate = usercommentRepository.findAll().size();

        // Update the usercomment
        Usercomment updatedUsercomment = usercommentRepository.findOne(usercomment.getId());
        updatedUsercomment
                .storyID(UPDATED_STORY_ID)
                .commentContent(UPDATED_COMMENT_CONTENT)
                .userCommentID(UPDATED_USER_COMMENT_ID)
                .userCommentName(UPDATED_USER_COMMENT_NAME)
                .timeCommented(UPDATED_TIME_COMMENTED);

        restUsercommentMockMvc.perform(put("/api/usercomments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUsercomment)))
                .andExpect(status().isOk());

        // Validate the Usercomment in the database
        List<Usercomment> usercomments = usercommentRepository.findAll();
        assertThat(usercomments).hasSize(databaseSizeBeforeUpdate);
        Usercomment testUsercomment = usercomments.get(usercomments.size() - 1);
        assertThat(testUsercomment.getStoryID()).isEqualTo(UPDATED_STORY_ID);
        assertThat(testUsercomment.getCommentContent()).isEqualTo(UPDATED_COMMENT_CONTENT);
        assertThat(testUsercomment.getUserCommentID()).isEqualTo(UPDATED_USER_COMMENT_ID);
        assertThat(testUsercomment.getUserCommentName()).isEqualTo(UPDATED_USER_COMMENT_NAME);
        assertThat(testUsercomment.getTimeCommented()).isEqualTo(UPDATED_TIME_COMMENTED);
    }

    @Test
    @Transactional
    public void deleteUsercomment() throws Exception {
        // Initialize the database
        usercommentService.save(usercomment);

        int databaseSizeBeforeDelete = usercommentRepository.findAll().size();

        // Get the usercomment
        restUsercommentMockMvc.perform(delete("/api/usercomments/{id}", usercomment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Usercomment> usercomments = usercommentRepository.findAll();
        assertThat(usercomments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
