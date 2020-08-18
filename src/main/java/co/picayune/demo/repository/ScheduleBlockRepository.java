package co.picayune.demo.repository;

import co.picayune.demo.domain.ScheduleBlock;

import co.picayune.demo.domain.enumeration.ScheduleBlockActivity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ScheduleBlock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleBlockRepository extends JpaRepository<ScheduleBlock, Long> {

    @Query(value =
        "SELECT SUM(  IF(sb.type='AVAILABLE', sb.duration_in_minutes, (-1 * sb.duration_in_minutes))) as usage, sb.day_of_week " +
            "FROM schedule_block sb " +
            "WHERE sb.employee_id = :employeeId " +
            "GROUP BY sb.day_of_week " +
            "ORDER BY sb.day_of_week ASC ", nativeQuery = true)
    public List<Object[]> findEmployeeAvailabilityByDay(
        @Param("employeeId") Long employeeId);

}
