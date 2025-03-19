package tpo.language.flashcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tpo.language.flashcards.controller.FlashcardsController;

@SpringBootApplication
public class FlashcardsApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FlashcardsApplication.class, args);
        FlashcardsController controller = context.getBean(FlashcardsController.class);
        controller.start();


    }

}
