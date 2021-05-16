package springsecurity.demo.projection;

import org.springframework.data.rest.core.config.Projection;
import springsecurity.demo.entity.WeekDays;

import java.util.UUID;

@Projection(name = "CustomWeekDays", types = WeekDays.class)
public interface CustomWeekDays {
    Integer getId();

    String getName();
}
