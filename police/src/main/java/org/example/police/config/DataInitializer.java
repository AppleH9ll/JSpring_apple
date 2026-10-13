package org.example.police.config;

import org.example.police.model.RoleEnum;
import org.example.police.model.UserModel;
import org.example.police.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        create("admin", "admin123", Set.of(RoleEnum.ADMIN));
        create("officer", "officer123", Set.of(RoleEnum.OFFICER));
        create("citizen", "citizen123", Set.of(RoleEnum.CITIZEN));
    }

    private void create(String username, String pwd, Set<RoleEnum> roles) {
        if (userRepository.findByUsername(username).isEmpty()) {
            UserModel u = new UserModel();
            u.setUsername(username);
            u.setPassword(encoder.encode(pwd));
            u.setEmail(username + "@police.ru");
            u.setLastName("Тестов");
            u.setFirstName(username);
            u.setRoles(roles);
            u.setActive(true);
            userRepository.save(u);
        }
    }
}