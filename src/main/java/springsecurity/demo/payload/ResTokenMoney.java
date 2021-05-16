package springsecurity.demo.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class ResTokenMoney {
    private UUID id;
    private Date date;

    private Double tokenMoney;

    private String student;
}
