package com.example.L3Application.bootstrap;

import com.example.L3Application.entity.User;
import com.example.L3Application.enums.Roles;
import com.example.L3Application.repo.UserRepo;
import com.example.L3Application.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) {
        seed("coordinator@12.test","Front" ,"Desk",Roles.FRONT_DESK_COORDINATOR);
        seed("dr.ajay@threshold.test", "Ajsy", "Rao", Roles.CLINICIAN);
    }
    private void seed(String email,String first,String last,Roles role){
        if (userRepository.existsByEmail(email))return;
        userRepository.save(User.builder().firstName(first).lastName(last).email(email)
                                  .password(passwordEncoder.encode("password"))
                                 .role(role).build());
    }
}
