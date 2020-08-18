package co.picayune.demo.web.rest;

import co.picayune.demo.BackendApp;
import co.picayune.demo.domain.Appointment;
import co.picayune.demo.repository.AppointmentRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.picayune.demo.domain.enumeration.AppointmentStatus;
/**
 * Integration tests for the {@link AppointmentResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AppointmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REF_KEY = "AAAAAAAAAA";
    private static final String UPDATED_REF_KEY = "BBBBBBBBBB";

    private static final AppointmentStatus DEFAULT_STATUS = AppointmentStatus.SCHEDULED;
    private static final AppointmentStatus UPDATED_STATUS = AppointmentStatus.CANCELLED;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_START_MINUTE = 0;
    private static final Integer UPDATED_START_MINUTE = 1;

    private static final Integer DEFAULT_DURATION_IN_MINUTES = 5;
    private static final Integer UPDATED_DURATION_IN_MINUTES = 6;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppointmentMockMvc;

    private Appointment appointment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appointment createEntity(EntityManager em) {
        Appointment appointment = new Appointment()
            .name(DEFAULT_NAME)
            .refKey(DEFAULT_REF_KEY)
            .status(DEFAULT_STATUS)
            .date(DEFAULT_DATE)
            .startMinute(DEFAULT_START_MINUTE)
            .durationInMinutes(DEFAULT_DURATION_IN_MINUTES);
        return appointment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appointment createUpdatedEntity(EntityManager em) {
        Appointment appointment = new Appointment()
            .name(UPDATED_NAME)
            .refKey(UPDATED_REF_KEY)
            .status(UPDATED_STATUS)
            .date(UPDATED_DATE)
            .startMinute(UPDATED_START_MINUTE)
            .durationInMinutes(UPDATED_DURATION_IN_MINUTES);
        return appointment;
    }

    @BeforeEach
    public void initTest() {
        appointment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppointment() throws Exception {
        int databaseSizeBeforeCreate = appointmentRepository.findAll().size();
        // Create the Appointment
        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointment)))
            .andExpect(status().isCreated());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeCreate + 1);
        Appointment testAppointment = appointmentList.get(appointmentList.size() - 1);
        assertThat(testAppointment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppointment.getRefKey()).isEqualTo(DEFAULT_REF_KEY);
        assertThat(testAppointment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppointment.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAppointment.getStartMinute()).isEqualTo(DEFAULT_START_MINUTE);
        assertThat(testAppointment.getDurationInMinutes()).isEqualTo(DEFAULT_DURATION_IN_MINUTES);
    }

    @Test
    @Transactional
    public void createAppointmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appointmentRepository.findAll().size();

        // Create the Appointment with an existing ID
        appointment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointment)))
            .andExpect(status().isBadRequest());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRefKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = appointmentRepository.findAll().size();
        // set the field null
        appointment.setRefKey(null);

        // Create the Appointment, which fails.


        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointment)))
            .andExpect(status().isBadRequest());

        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = appointmentRepository.findAll().size();
        // set the field null
        appointment.setDate(null);

        // Create the Appointment, which fails.


        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointment)))
            .andExpect(status().isBadRequest());

        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartMinuteIsRequired() throws Exception {
        int databaseSizeBeforeTest = appointmentRepository.findAll().size();
        // set the field null
        appointment.setStartMinute(null);

        // Create the Appointment, which fails.


        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointment)))
            .andExpect(status().isBadRequest());

        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationInMinutesIsRequired() throws Exception {
        int databaseSizeBeforeTest = appointmentRepository.findAll().size();
        // set the field null
        appointment.setDurationInMinutes(null);

        // Create the Appointment, which fails.


        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointment)))
            .andExpect(status().isBadRequest());

        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppointments() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList
        restAppointmentMockMvc.perform(get("/api/appointments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].refKey").value(hasItem(DEFAULT_REF_KEY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].startMinute").value(hasItem(DEFAULT_START_MINUTE)))
            .andExpect(jsonPath("$.[*].durationInMinutes").value(hasItem(DEFAULT_DURATION_IN_MINUTES)));
    }
    
    @Test
    @Transactional
    public void getAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get the appointment
        restAppointmentMockMvc.perform(get("/api/appointments/{id}", appointment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appointment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.refKey").value(DEFAULT_REF_KEY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.startMinute").value(DEFAULT_START_MINUTE))
            .andExpect(jsonPath("$.durationInMinutes").value(DEFAULT_DURATION_IN_MINUTES));
    }
    @Test
    @Transactional
    public void getNonExistingAppointment() throws Exception {
        // Get the appointment
        restAppointmentMockMvc.perform(get("/api/appointments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        int databaseSizeBeforeUpdate = appointmentRepository.findAll().size();

        // Update the appointment
        Appointment updatedAppointment = appointmentRepository.findById(appointment.getId()).get();
        // Disconnect from session so that the updates on updatedAppointment are not directly saved in db
        em.detach(updatedAppointment);
        updatedAppointment
            .name(UPDATED_NAME)
            .refKey(UPDATED_REF_KEY)
            .status(UPDATED_STATUS)
            .date(UPDATED_DATE)
            .startMinute(UPDATED_START_MINUTE)
            .durationInMinutes(UPDATED_DURATION_IN_MINUTES);

        restAppointmentMockMvc.perform(put("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppointment)))
            .andExpect(status().isOk());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeUpdate);
        Appointment testAppointment = appointmentList.get(appointmentList.size() - 1);
        assertThat(testAppointment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppointment.getRefKey()).isEqualTo(UPDATED_REF_KEY);
        assertThat(testAppointment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppointment.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAppointment.getStartMinute()).isEqualTo(UPDATED_START_MINUTE);
        assertThat(testAppointment.getDurationInMinutes()).isEqualTo(UPDATED_DURATION_IN_MINUTES);
    }

    @Test
    @Transactional
    public void updateNonExistingAppointment() throws Exception {
        int databaseSizeBeforeUpdate = appointmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppointmentMockMvc.perform(put("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointment)))
            .andExpect(status().isBadRequest());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        int databaseSizeBeforeDelete = appointmentRepository.findAll().size();

        // Delete the appointment
        restAppointmentMockMvc.perform(delete("/api/appointments/{id}", appointment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
