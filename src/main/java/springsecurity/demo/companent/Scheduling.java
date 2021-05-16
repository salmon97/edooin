package springsecurity.demo.companent;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springsecurity.demo.repository.TokenMoneyRepository;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

@Component
@EnableScheduling
public class Scheduling {

    @Autowired
    TokenMoneyRepository tokenMoneyRepository;

    @Scheduled(cron = "0 0/40 * * * ?")
    public void scheduling() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Tashkent"));
        Integer i = tokenMoneyRepository.update_query(Time.valueOf(localDateTime.toString().substring(11,19)));
        if (i > 1)
            tokenMoneyRepository.insert_query(Time.valueOf(localDateTime.toString().substring(11,19)));
    }

    @Scheduled(cron = "0 0 1 * * MON")
    public void example() {
        tokenMoneyRepository.deleteOlderOneMonth();
    }
}
