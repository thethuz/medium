package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MediumApp;

import com.mycompany.myapp.domain.Draft;
import com.mycompany.myapp.repository.DraftRepository;

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
 * Test class for the DraftResource REST controller.
 *
 * @see DraftResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MediumApp.class)
public class DraftResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final Long UPDATED_AUTHOR_ID = 2L;

    private static final ZonedDateTime DEFAULT_TIME_CREATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TIME_CREATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TIME_CREATE_STR = dateTimeFormatter.format(DEFAULT_TIME_CREATE);
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final String DEFAULT_CONTENT = "AAAAA";
    private static final String UPDATED_CONTENT = "BBBBB";
    private static final String DEFAULT_CATEGORY = "AAAAA";
    private static final String UPDATED_CATEGORY = "BBBBB";

    @Inject
    private DraftRepository draftRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDraftMockMvc;

    private Draft draft;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DraftResource draftResource = new DraftResource();
        ReflectionTestUtils.setField(draftResource, "draftRepository", draftRepository);
        this.restDraftMockMvc = MockMvcBuilders.standaloneSetup(draftResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Draft createEntity(EntityManager em) {
        Draft draft = new Draft()
                .authorID(DEFAULT_AUTHOR_ID)
                .timeCreate(DEFAULT_TIME_CREATE)
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .category(DEFAULT_CATEGORY);
        return draft;
    }

    @Before
    public void initTest() {
        draft = createEntity(em);
    }

    @Test
    @Transactional
    public void createDraft() throws Exception {
        int databaseSizeBeforeCreate = draftRepository.findAll().size();

        // Create the Draft

        restDraftMockMvc.perform(post("/api/drafts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(draft)))
                .andExpect(status().isCreated());

        // Validate the Draft in the database
        List<Draft> drafts = draftRepository.findAll();
        assertThat(drafts).hasSize(databaseSizeBeforeCreate + 1);
        Draft testDraft = drafts.get(drafts.size() - 1);
        assertThat(testDraft.getAuthorID()).isEqualTo(DEFAULT_AUTHOR_ID);
        assertThat(testDraft.getTimeCreate()).isEqualTo(DEFAULT_TIME_CREATE);
        assertThat(testDraft.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDraft.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testDraft.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllDrafts() throws Exception {
        // Initialize the database
        draftRepository.saveAndFlush(draft);

        // Get all the drafts
        restDraftMockMvc.perform(get("/api/drafts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(draft.getId().intValue())))
                .andExpect(jsonPath("$.[*].authorID").value(hasItem(DEFAULT_AUTHOR_ID.intValue())))
                .andExpect(jsonPath("$.[*].timeCreate").value(hasItem(DEFAULT_TIME_CREATE_STR)))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }

    @Test
    @Transactional
    public void getDraft() throws Exception {
        // Initialize the database
        draftRepository.saveAndFlush(draft);

        // Get the draft
        restDraftMockMvc.perform(get("/api/drafts/{id}", draft.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(draft.getId().intValue()))
            .andExpect(jsonPath("$.authorID").value(DEFAULT_AUTHOR_ID.intValue()))
            .andExpect(jsonPath("$.timeCreate").value(DEFAULT_TIME_CREATE_STR))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDraft() throws Exception {
        // Get the draft
        restDraftMockMvc.perform(get("/api/drafts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDraft() throws Exception {
        // Initialize the database
        draftRepository.saveAndFlush(draft);
        int databaseSizeBeforeUpdate = draftRepository.findAll().size();

        // Update the draft
        Draft updatedDraft = draftRepository.findOne(draft.getId());
        updatedDraft
                .authorID(UPDATED_AUTHOR_ID)
                .timeCreate(UPDATED_TIME_CREATE)
                .title(UPDATED_TITLE)
                .content(UPDATED_CONTENT)
                .category(UPDATED_CATEGORY);

        restDraftMockMvc.perform(put("/api/drafts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDraft)))
                .andExpect(status().isOk());

        // Validate the Draft in the database
        List<Draft> drafts = draftRepository.findAll();
        assertThat(drafts).hasSize(databaseSizeBeforeUpdate);
        Draft testDraft = drafts.get(drafts.size() - 1);
        assertThat(testDraft.getAuthorID()).isEqualTo(UPDATED_AUTHOR_ID);
        assertThat(testDraft.getTimeCreate()).isEqualTo(UPDATED_TIME_CREATE);
        assertThat(testDraft.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDraft.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testDraft.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void deleteDraft() throws Exception {
        // Initialize the database
        draftRepository.saveAndFlush(draft);
        int databaseSizeBeforeDelete = draftRepository.findAll().size();

        // Get the draft
        restDraftMockMvc.perform(delete("/api/drafts/{id}", draft.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Draft> drafts = draftRepository.findAll();
        assertThat(drafts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
