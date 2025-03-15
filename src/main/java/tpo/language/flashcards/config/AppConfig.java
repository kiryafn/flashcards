package tpo.language.flashcards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public Scanner scannerSystemIn() {
        return new Scanner(System.in);
    }
}
