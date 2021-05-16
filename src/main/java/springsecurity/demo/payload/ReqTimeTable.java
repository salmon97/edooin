package springsecurity.demo.payload;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ReqTimeTable {
    private UUID groupId;
    private Set<Long> weekdaysId;
    private String startHour;
    private String startMin;
    private String endHour;
    private String endMin;
    private boolean active;
}
