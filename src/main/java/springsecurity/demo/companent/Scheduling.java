package springsecurity.demo.companent;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springsecurity.demo.repository.TokenMoneyRepository;

@Component
@EnableScheduling
public class Scheduling {

    @Autowired
    TokenMoneyRepository tokenMoneyRepository;

    @Scheduled(cron = "0 0/40 * * * ?")
    public void scheduling() {
        Integer i = tokenMoneyRepository.update_query();
        if (i > 1)
            tokenMoneyRepository.insert_query();
    }

    @Scheduled(cron = "0 0 1 * * MON")
    public void example() {
        tokenMoneyRepository.deleteOlderOneMonth();
    }
}
