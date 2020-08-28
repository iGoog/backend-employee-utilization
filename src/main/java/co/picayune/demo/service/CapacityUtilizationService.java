package co.picayune.demo.service;

import co.picayune.demo.domain.CapacityUtilization;
import co.picayune.demo.domain.Employee;
import co.picayune.demo.domain.enumeration.AppointmentStatus;
import co.picayune.demo.repository.AppointmentRepository;
import co.picayune.demo.repository.EmployeeRepository;
import co.picayune.demo.repository.ScheduleBlockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class CapacityUtilizationService {

    private final AppointmentRepository appointmentRepository;
    private final ScheduleBlockRepository scheduleBlockRepository;

    public CapacityUtilizationService(AppointmentRepository appointmentRepository,
                                      ScheduleBlockRepository scheduleBlockRepository) {
        this.appointmentRepository = appointmentRepository;
        this.scheduleBlockRepository = scheduleBlockRepository;
    }



    public List<CapacityUtilization> reportCapacityUtilization(List<Employee> employees, LocalDate start, LocalDate end) {
        List<CapacityUtilization> utilizationList = new ArrayList<>(employees.size()+1);
        long totalAvailability = 0;
        long totalUsage = 0;
        for (Employee employee : employees) {
            CapacityUtilization employeeUtilization = new CapacityUtilization();
            utilizationList.add(employeeUtilization);
            employeeUtilization.setEmployee(employee);
            long usage = appointmentRepository.findEmployeeUsage(start,end,employee.getId(), AppointmentStatus.SCHEDULED);
            employeeUtilization.setUsage(usage);
            List<Object[]> dailyAvailability = scheduleBlockRepository.findEmployeeAvailabilityByDay(employee.getId());
            long[] dayOfWeekOccurances = calcDayOfWeekOccurances(start, end);
            long availability = 0;
            for (Object[] dayAvailability : dailyAvailability) { // [avail, dayOfWeek]
                int dayOfWeek = ((Integer)dayAvailability[1] ) -1;
                long avail = ((Long)dayAvailability[0] );
                availability += dayOfWeekOccurances[ dayOfWeek ] * avail * employee.getCapacity();
            }
            employeeUtilization.setAvailability(availability);
            employeeUtilization.setUtilization(calculateUtilization(usage, availability));
            totalAvailability += availability;
            totalUsage += usage;
        }
        CapacityUtilization totalUtilization = new CapacityUtilization();
        utilizationList.add(totalUtilization);
        totalUtilization.setAggregate(true);
        totalUtilization.setUsage(totalUsage);
        totalUtilization.setAvailability(totalAvailability);
        totalUtilization.setUtilization(calculateUtilization(totalUsage, totalAvailability));

        return utilizationList;
    }

    float calculateUtilization(long usage, long availability) {
        if (availability == 0) return -1.0f;
        return  (float) (((double) usage) / ((double) availability ));
    }

    long[] calcDayOfWeekOccurances(LocalDate start, LocalDate end) {
        long[] dayOfWeekOccurances = new long[7];
        int startDay = start.getDayOfWeek().getValue()-1; // Monday is 1, so 1-1 = 0;
        long days = ChronoUnit.DAYS.between(start, end);
        for (int i=0; i<7; i++) {
            dayOfWeekOccurances[(i+startDay) % 7] = (days / 7); // full weeks
            if  ( (days % 7) >= i ) dayOfWeekOccurances[(i+startDay) % 7] += 1;
        }
        return dayOfWeekOccurances;
    }



}
