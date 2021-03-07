package springsecurity.demo.projection;

import org.springframework.data.rest.core.config.Projection;
import springsecurity.demo.entity.Subject;

import java.util.UUID;

@Projection(name = "CustomSubject", types = Subject.class)
public interface CustomSubject {
    UUID getId();

    String getName();
}
