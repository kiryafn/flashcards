package tpo.language.flashcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tpo.language.flashcards.controller.FlashcardsController;
import tpo.language.flashcards.data.Colors;
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
            System.out.println(Colors.RED + "\n1" + Colors.RESET + ". Add a word");
            System.out.println(Colors.YELLOW + "2" + Colors.RESET + ". Show all words");
            System.out.println(Colors.GREEN + "3" + Colors.RESET + ". Start the test");
            System.out.println(Colors.BLUE + "4" + Colors.RESET + ". Exit");
            System.out.print(Colors.BOLD + "\nChoose an action: " + Colors.RESET);
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
