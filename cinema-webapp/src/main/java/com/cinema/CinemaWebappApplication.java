package com.cinema;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cinema.model.User;
import com.cinema.repository.UserRepository;
import com.cinema.service.UserService;

import java.security.SecureRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class CinemaWebappApplication implements CommandLineRunner{
	
    @Autowired
    private UserRepository userRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(CinemaWebappApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CinemaWebappApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@admin.com")) {
            String adminPassword = generateRandomPassword(16);
            User admin = new User();
            admin.setEmail("admin@admin.com");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            admin.setPassword(encoder.encode(adminPassword));
            admin.setRole("ADMIN");
            userRepository.save(admin);
            logger.info("CREDENZIALI ADMIN:");
            logger.info("{}", admin.getEmail());
            logger.info("{}", adminPassword);
        }
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!$%&/()=?'@#";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for(int i=0;i<length;i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

}
