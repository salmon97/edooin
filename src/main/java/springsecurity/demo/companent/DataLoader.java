package springsecurity.demo.companent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import springsecurity.demo.entity.User;
import springsecurity.demo.repository.RoleRepository;
import springsecurity.demo.repository.UserRepository;

import java.util.Date;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Value("${spring.datasource.initialization-mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            userRepository.save(
                    new User(
                            null,
                            "Salmon",
                            "Muminov",
                            "AA1234567",
                            new Date(),
                            "+998937837520",
                            passwordEncoder.encode("root123"),
                            null,
                            new HashSet<>(roleRepository.findAll())
                    )
            );
        }
    }
}
