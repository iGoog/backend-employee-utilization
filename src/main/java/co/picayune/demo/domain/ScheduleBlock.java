package co.picayune.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import co.picayune.demo.domain.enumeration.ScheduleBlockActivity;

/**
 * A ScheduleBlock.
 */
@Entity
@Table(name = "schedule_block")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ScheduleBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ScheduleBlockActivity type;

    @NotNull
    @Min(value = 1)
    @Max(value = 7)
    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    /**
     * minute in day block starts.
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1435)
    @ApiModelProperty(value = "minute in day block starts.", required = true)
    @Column(name = "start_minute", nullable = false)
    private Integer startMinute;

    /**
     * minute length of block, use 5 minute granularity
     */
    @NotNull
    @Min(value = 5)
    @Max(value = 1440)
    @ApiModelProperty(value = "minute length of block, use 5 minute granularity", required = true)
    @Column(name = "duration_in_minutes", nullable = false)
    private Integer durationInMinutes;

    @ManyToOne
    @JsonIgnoreProperties(value = "scheduleBlocks", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScheduleBlockActivity getType() {
        return type;
    }

    public ScheduleBlock type(ScheduleBlockActivity type) {
        this.type = type;
        return this;
    }

    public void setType(ScheduleBlockActivity type) {
        this.type = type;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public ScheduleBlock dayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public ScheduleBlock startMinute(Integer startMinute) {
        this.startMinute = startMinute;
        return this;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public ScheduleBlock durationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        return this;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Employee getEmployee() {
        return employee;
    }

    public ScheduleBlock employee(Employee employee) {
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
        if (!(o instanceof ScheduleBlock)) {
            return false;
        }
        return id != null && id.equals(((ScheduleBlock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleBlock{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", dayOfWeek=" + getDayOfWeek() +
            ", startMinute=" + getStartMinute() +
            ", durationInMinutes=" + getDurationInMinutes() +
            "}";
    }
}
