package co.picayune.demo.web.rest;

import co.picayune.demo.BackendApp;
import co.picayune.demo.domain.ScheduleBlock;
import co.picayune.demo.repository.ScheduleBlockRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.picayune.demo.domain.enumeration.ScheduleBlockActivity;
/**
 * Integration tests for the {@link ScheduleBlockResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScheduleBlockResourceIT {

    private static final ScheduleBlockActivity DEFAULT_TYPE = ScheduleBlockActivity.AVAILABLE;
    private static final ScheduleBlockActivity UPDATED_TYPE = ScheduleBlockActivity.BREAK;

    private static final Integer DEFAULT_DAY_OF_WEEK = 1;
    private static final Integer UPDATED_DAY_OF_WEEK = 2;

    private static final Integer DEFAULT_START_MINUTE = 0;
    private static final Integer UPDATED_START_MINUTE = 1;

    private static final Integer DEFAULT_DURATION_IN_MINUTES = 5;
    private static final Integer UPDATED_DURATION_IN_MINUTES = 6;

    @Autowired
    private ScheduleBlockRepository scheduleBlockRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheduleBlockMockMvc;

    private ScheduleBlock scheduleBlock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleBlock createEntity(EntityManager em) {
        ScheduleBlock scheduleBlock = new ScheduleBlock()
            .type(DEFAULT_TYPE)
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .startMinute(DEFAULT_START_MINUTE)
            .durationInMinutes(DEFAULT_DURATION_IN_MINUTES);
        return scheduleBlock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleBlock createUpdatedEntity(EntityManager em) {
        ScheduleBlock scheduleBlock = new ScheduleBlock()
            .type(UPDATED_TYPE)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .startMinute(UPDATED_START_MINUTE)
            .durationInMinutes(UPDATED_DURATION_IN_MINUTES);
        return scheduleBlock;
    }

    @BeforeEach
    public void initTest() {
        scheduleBlock = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheduleBlock() throws Exception {
        int databaseSizeBeforeCreate = scheduleBlockRepository.findAll().size();
        // Create the ScheduleBlock
        restScheduleBlockMockMvc.perform(post("/api/schedule-blocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleBlock)))
            .andExpect(status().isCreated());

        // Validate the ScheduleBlock in the database
        List<ScheduleBlock> scheduleBlockList = scheduleBlockRepository.findAll();
        assertThat(scheduleBlockList).hasSize(databaseSizeBeforeCreate + 1);
        ScheduleBlock testScheduleBlock = scheduleBlockList.get(scheduleBlockList.size() - 1);
        assertThat(testScheduleBlock.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testScheduleBlock.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testScheduleBlock.getStartMinute()).isEqualTo(DEFAULT_START_MINUTE);
        assertThat(testScheduleBlock.getDurationInMinutes()).isEqualTo(DEFAULT_DURATION_IN_MINUTES);
    }

    @Test
    @Transactional
    public void createScheduleBlockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleBlockRepository.findAll().size();

        // Create the ScheduleBlock with an existing ID
        scheduleBlock.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleBlockMockMvc.perform(post("/api/schedule-blocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleBlock)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleBlock in the database
        List<ScheduleBlock> scheduleBlockList = scheduleBlockRepository.findAll();
        assertThat(scheduleBlockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleBlockRepository.findAll().size();
        // set the field null
        scheduleBlock.setType(null);

        // Create the ScheduleBlock, which fails.


        restScheduleBlockMockMvc.perform(post("/api/schedule-blocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleBlock)))
            .andExpect(status().isBadRequest());

        List<ScheduleBlock> scheduleBlockList = scheduleBlockRepository.findAll();
        assertThat(scheduleBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDayOfWeekIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleBlockRepository.findAll().size();
        // set the field null
        scheduleBlock.setDayOfWeek(null);

        // Create the ScheduleBlock, which fails.


        restScheduleBlockMockMvc.perform(post("/api/schedule-blocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleBlock)))
            .andExpect(status().isBadRequest());

        List<ScheduleBlock> scheduleBlockList = scheduleBlockRepository.findAll();
        assertThat(scheduleBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartMinuteIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleBlockRepository.findAll().size();
        // set the field null
        scheduleBlock.setStartMinute(null);

        // Create the ScheduleBlock, which fails.


        restScheduleBlockMockMvc.perform(post("/api/schedule-blocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleBlock)))
            .andExpect(status().isBadRequest());

        List<ScheduleBlock> scheduleBlockList = scheduleBlockRepository.findAll();
        assertThat(scheduleBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationInMinutesIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleBlockRepository.findAll().size();
        // set the field null
        scheduleBlock.setDurationInMinutes(null);

        // Create the ScheduleBlock, which fails.


        restScheduleBlockMockMvc.perform(post("/api/schedule-blocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleBlock)))
            .andExpect(status().isBadRequest());

        List<ScheduleBlock> scheduleBlockList = scheduleBlockRepository.findAll();
        assertThat(scheduleBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScheduleBlocks() throws Exception {
        // Initialize the database
        scheduleBlockRepository.saveAndFlush(scheduleBlock);

        // Get all the scheduleBlockList
        restScheduleBlockMockMvc.perform(get("/api/schedule-blocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleBlock.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].startMinute").value(hasItem(DEFAULT_START_MINUTE)))
            .andExpect(jsonPath("$.[*].durationInMinutes").value(hasItem(DEFAULT_DURATION_IN_MINUTES)));
    }
    
    @Test
    @Transactional
    public void getScheduleBlock() throws Exception {
        // Initialize the database
        scheduleBlockRepository.saveAndFlush(scheduleBlock);

        // Get the scheduleBlock
        restScheduleBlockMockMvc.perform(get("/api/schedule-blocks/{id}", scheduleBlock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scheduleBlock.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK))
            .andExpect(jsonPath("$.startMinute").value(DEFAULT_START_MINUTE))
            .andExpect(jsonPath("$.durationInMinutes").value(DEFAULT_DURATION_IN_MINUTES));
    }
    @Test
    @Transactional
    public void getNonExistingScheduleBlock() throws Exception {
        // Get the scheduleBlock
        restScheduleBlockMockMvc.perform(get("/api/schedule-blocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheduleBlock() throws Exception {
        // Initialize the database
        scheduleBlockRepository.saveAndFlush(scheduleBlock);

        int databaseSizeBeforeUpdate = scheduleBlockRepository.findAll().size();

        // Update the scheduleBlock
        ScheduleBlock updatedScheduleBlock = scheduleBlockRepository.findById(scheduleBlock.getId()).get();
        // Disconnect from session so that the updates on updatedScheduleBlock are not directly saved in db
        em.detach(updatedScheduleBlock);
        updatedScheduleBlock
            .type(UPDATED_TYPE)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .startMinute(UPDATED_START_MINUTE)
            .durationInMinutes(UPDATED_DURATION_IN_MINUTES);

        restScheduleBlockMockMvc.perform(put("/api/schedule-blocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedScheduleBlock)))
            .andExpect(status().isOk());

        // Validate the ScheduleBlock in the database
        List<ScheduleBlock> scheduleBlockList = scheduleBlockRepository.findAll();
        assertThat(scheduleBlockList).hasSize(databaseSizeBeforeUpdate);
        ScheduleBlock testScheduleBlock = scheduleBlockList.get(scheduleBlockList.size() - 1);
        assertThat(testScheduleBlock.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testScheduleBlock.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testScheduleBlock.getStartMinute()).isEqualTo(UPDATED_START_MINUTE);
        assertThat(testScheduleBlock.getDurationInMinutes()).isEqualTo(UPDATED_DURATION_IN_MINUTES);
    }

    @Test
    @Transactional
    public void updateNonExistingScheduleBlock() throws Exception {
        int databaseSizeBeforeUpdate = scheduleBlockRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleBlockMockMvc.perform(put("/api/schedule-blocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleBlock)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleBlock in the database
        List<ScheduleBlock> scheduleBlockList = scheduleBlockRepository.findAll();
        assertThat(scheduleBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScheduleBlock() throws Exception {
        // Initialize the database
        scheduleBlockRepository.saveAndFlush(scheduleBlock);

        int databaseSizeBeforeDelete = scheduleBlockRepository.findAll().size();

        // Delete the scheduleBlock
        restScheduleBlockMockMvc.perform(delete("/api/schedule-blocks/{id}", scheduleBlock.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScheduleBlock> scheduleBlockList = scheduleBlockRepository.findAll();
        assertThat(scheduleBlockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
