package springsecurity.demo.projection;

import org.springframework.data.rest.core.config.Projection;
import springsecurity.demo.entity.Region;

@Projection(name = "CustomRegion", types = Region.class)
public interface CustomRegion {
    Integer getId();

    String getName();
}
