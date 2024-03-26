package ua.petproject;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class PractProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(PractProjectApplication.class, args);

    }

    @Override
    public void run(String... args) {

        System.out.println("Application started successfully!");

    }
}