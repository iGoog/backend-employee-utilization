package co.picayune.demo.service;

import co.picayune.demo.domain.CapacityUtilization;
import co.picayune.demo.domain.Employee;
import co.picayune.demo.domain.enumeration.AppointmentStatus;
import co.picayune.demo.repository.AppointmentRepository;
import co.picayune.demo.repository.EmployeeRepository;
import co.picayune.demo.repository.ScheduleBlockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CapacityUtilizationServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private ScheduleBlockRepository scheduleBlockRepository;

    @InjectMocks
    private CapacityUtilizationService cus;

    private Employee genEmployee() {
        Employee employee = new Employee();
        employee.setCapacity(1);
        employee.setId(1l);
        employee.setRefKey("pboudreaux");
        employee.setName("Paul");

        return employee;
    }

    //TODO: make sensible name assert expectations, adjust to @ParameterizedTest, @CsvFileSource(resources = "/cus.csv")
    @Test
    public void canTest() {
        Employee employee = genEmployee();
        List<Employee> employees = Arrays.asList(new Employee[] { employee });
        LocalDate start = LocalDate.of(2020, 10, 1);
        LocalDate end = LocalDate.of(2020, 10, 2);
        when(appointmentRepository.findEmployeeUsage(start, end, employee.getId(), AppointmentStatus.SCHEDULED ))
            .thenReturn(600l);

        // 4 and 5.
        List<Object[]> dailyAvailability = new ArrayList<>();
        dailyAvailability.add(new Object[] {150l, 4});
        dailyAvailability.add(new Object[] {150l, 5});

        when(scheduleBlockRepository.findEmployeeAvailabilityByDay(employee.getId()))
            .thenReturn(dailyAvailability);

        long[] dowOccurances = cus.calcDayOfWeekOccurances(start, end);
        for (long o : dowOccurances) System.out.println(o);


        List<CapacityUtilization> result = cus.reportCapacityUtilization(employees, start, end);

        for (CapacityUtilization cu : result) {
            System.out.print(cu.getEmployee());
            System.out.print(" : ");
            System.out.println(cu.getUtilization());
        }



    }

}
