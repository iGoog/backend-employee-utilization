package co.picayune.demo.repository;

import co.picayune.demo.domain.Appointment;

import co.picayune.demo.domain.Employee;
import co.picayune.demo.domain.enumeration.AppointmentStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Spring Data  repository for the Appointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value =
        "SELECT SUM(a.duration_in_minutes) as usage " +
        "FROM appointment a " +
        "WHERE a.date BETWEEN :start AND :end " +
            "AND a.employee_id = :employeeId " +
            "AND a.status = :status ", nativeQuery = true)
    Long findEmployeeUsage(@Param("start") LocalDate start, @Param("end") LocalDate end,
                           @Param("employeeId") Long employeeId, @Param("status") AppointmentStatus status);
}
