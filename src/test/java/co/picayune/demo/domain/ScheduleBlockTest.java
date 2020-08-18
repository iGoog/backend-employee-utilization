package co.picayune.demo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.picayune.demo.web.rest.TestUtil;

public class ScheduleBlockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleBlock.class);
        ScheduleBlock scheduleBlock1 = new ScheduleBlock();
        scheduleBlock1.setId(1L);
        ScheduleBlock scheduleBlock2 = new ScheduleBlock();
        scheduleBlock2.setId(scheduleBlock1.getId());
        assertThat(scheduleBlock1).isEqualTo(scheduleBlock2);
        scheduleBlock2.setId(2L);
        assertThat(scheduleBlock1).isNotEqualTo(scheduleBlock2);
        scheduleBlock1.setId(null);
        assertThat(scheduleBlock1).isNotEqualTo(scheduleBlock2);
    }
}
