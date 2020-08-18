package co.picayune.demo.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * the number of appointments an employee can handle at once
     */
    @NotNull
    @ApiModelProperty(value = "the number of appointments an employee can handle at once", required = true)
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "ref_key", nullable = false, unique = true)
    private String refKey;

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ScheduleBlock> scheduleBlocks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Employee capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public Employee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefKey() {
        return refKey;
    }

    public Employee refKey(String refKey) {
        this.refKey = refKey;
        return this;
    }

    public void setRefKey(String refKey) {
        this.refKey = refKey;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public Employee appointments(Set<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public Employee addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setEmployee(this);
        return this;
    }

    public Employee removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.setEmployee(null);
        return this;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<ScheduleBlock> getScheduleBlocks() {
        return scheduleBlocks;
    }

    public Employee scheduleBlocks(Set<ScheduleBlock> scheduleBlocks) {
        this.scheduleBlocks = scheduleBlocks;
        return this;
    }

    public Employee addScheduleBlock(ScheduleBlock scheduleBlock) {
        this.scheduleBlocks.add(scheduleBlock);
        scheduleBlock.setEmployee(this);
        return this;
    }

    public Employee removeScheduleBlock(ScheduleBlock scheduleBlock) {
        this.scheduleBlocks.remove(scheduleBlock);
        scheduleBlock.setEmployee(null);
        return this;
    }

    public void setScheduleBlocks(Set<ScheduleBlock> scheduleBlocks) {
        this.scheduleBlocks = scheduleBlocks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", capacity=" + getCapacity() +
            ", name='" + getName() + "'" +
            ", refKey='" + getRefKey() + "'" +
            "}";
    }
}
