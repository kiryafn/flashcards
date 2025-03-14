package tpo.language.flashcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tpo.language.flashcards.controller.FlashcardsController;
import tpo.language.flashcards.service.FileService;
import java.util.Scanner;

@SpringBootApplication
public class FlashcardsApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FlashcardsApplication.class, args);
        FlashcardsController controller = context.getBean(FlashcardsController.class);
        FileService fileService = context.getBean(FileService.class);

        fileService.loadEntries();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add a word");
            System.out.println("2. Show all words");
            System.out.println("3. Start the test");
            System.out.println("4. Exit");
            System.out.print("\nChoose an action: ");
            String choice = scanner.next();
            scanner.nextLine();

            switch (choice) {
                case "1" -> controller.addWord();
                case "2" -> controller.displayWords();
                case "3" -> controller.startTest();
                case "4" -> System.exit(0);
                default -> System.out.println("\nWrong choice!");
            }
        }
    }

}
