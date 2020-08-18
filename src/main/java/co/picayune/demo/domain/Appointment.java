package co.picayune.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import co.picayune.demo.domain.enumeration.AppointmentStatus;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    /**
     * a unique key to make it easy to reference an appointment
     */
    @NotNull
    @ApiModelProperty(value = "a unique key to make it easy to reference an appointment", required = true)
    @Column(name = "ref_key", nullable = false, unique = true)
    private String refKey;

    /**
     * Is the apppointment scheduled / cancelled?
     */
    @ApiModelProperty(value = "Is the apppointment scheduled / cancelled?")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppointmentStatus status;

    /**
     * What day is the appointment?
     */
    @NotNull
    @ApiModelProperty(value = "What day is the appointment?", required = true)
    @Column(name = "date", nullable = false)
    private LocalDate date;

    /**
     * minute in day appointment starts.
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1435)
    @ApiModelProperty(value = "minute in day appointment starts.", required = true)
    @Column(name = "start_minute", nullable = false)
    private Integer startMinute;

    /**
     * length of appointment, use 5 minute granularity
     */
    @NotNull
    @Min(value = 5)
    @Max(value = 1440)
    @ApiModelProperty(value = "length of appointment, use 5 minute granularity", required = true)
    @Column(name = "duration_in_minutes", nullable = false)
    private Integer durationInMinutes;

    @ManyToOne
    @JsonIgnoreProperties(value = "appointments", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Appointment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefKey() {
        return refKey;
    }

    public Appointment refKey(String refKey) {
        this.refKey = refKey;
        return this;
    }

    public void setRefKey(String refKey) {
        this.refKey = refKey;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Appointment status(AppointmentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public Appointment date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public Appointment startMinute(Integer startMinute) {
        this.startMinute = startMinute;
        return this;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public Appointment durationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        return this;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Appointment employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appointment)) {
            return false;
        }
        return id != null && id.equals(((Appointment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Appointment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", refKey='" + getRefKey() + "'" +
            ", status='" + getStatus() + "'" +
            ", date='" + getDate() + "'" +
            ", startMinute=" + getStartMinute() +
            ", durationInMinutes=" + getDurationInMinutes() +
            "}";
    }
}
