package tpo.language.flashcards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tpo.language.flashcards.service.DisplayService;


@Configuration
public class ProfileConfig {

    @Bean
    @Profile("default")
    public DisplayService defaultDisplayService() {
        return text -> text;
    }

    @Bean
    @Profile("uppercase")
    public DisplayService uppercaseDisplayService() {
        return String::toUpperCase;
    }

    @Bean
    @Profile("lowercase")
    public DisplayService lowercaseDisplayService() {
        return String::toLowerCase;
    }
}
