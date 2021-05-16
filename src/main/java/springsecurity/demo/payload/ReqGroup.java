package springsecurity.demo.payload;

import lombok.Data;
import springsecurity.demo.entity.Subject;
import springsecurity.demo.entity.Teacher;

import javax.validation.constraints.Pattern;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
public class ReqGroup {
    private UUID id;
    private String name;
    private String direction;
    @Pattern(regexp = "[a-zA-Z0-9]{4,8}", message = "group code must be between 4 and 8 characters")
    private String groupCode;
    private double price;
    private List<UUID> subjectId;
    private List<UUID> teacherId;
    private List<Integer> weekdaysId;
    private String startHour;
    private String endHour;
    private boolean active;

//    public static void main(String[] args) {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDate localDate = new LocalDate();
//        TimeZone timeZone1 = TimeZone.getTimeZone("Asia/Tashkent");
//        Calendar calendar = new GregorianCalendar();
//        calendar.add(Calendar.DAY_OF_WEEK,-1);
//        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
//        DayOfWeek day = calendar.getTime().getDayOfWeek();
//        System.out.println(day.getValue());
//    }
}
