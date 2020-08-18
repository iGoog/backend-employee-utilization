package co.picayune.demo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.picayune.demo.web.rest.TestUtil;

public class AppointmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appointment.class);
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        Appointment appointment2 = new Appointment();
        appointment2.setId(appointment1.getId());
        assertThat(appointment1).isEqualTo(appointment2);
        appointment2.setId(2L);
        assertThat(appointment1).isNotEqualTo(appointment2);
        appointment1.setId(null);
        assertThat(appointment1).isNotEqualTo(appointment2);
    }
}
