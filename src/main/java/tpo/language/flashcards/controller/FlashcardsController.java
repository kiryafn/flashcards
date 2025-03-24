package tpo.language.flashcards.controller;

import org.springframework.stereotype.Controller;
import tpo.language.flashcards.data.Colors;
import tpo.language.flashcards.exception.EntryAlreadyExistsException;
import tpo.language.flashcards.exception.EntryNotFoundException;
import tpo.language.flashcards.model.Entry;
import tpo.language.flashcards.service.EntryService;
import tpo.language.flashcards.service.display.DisplayService;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Controller
public class FlashcardsController {
    private final EntryService entryService;
    private final DisplayService displayService;
    private final Scanner scanner;

    public FlashcardsController(EntryService entryService, DisplayService displayService, Scanner scanner) {
        this.entryService = entryService;
        this.displayService = displayService;
        this.scanner = scanner;
    }

    public void addWord() {
        System.out.print("\nEnter the word (Polish, English, German separated by commas): ");
        String[] parts = scanner.nextLine().split(",");

        if (parts.length != 3) {
            System.err.println("Incorrect input format. Please input 3 words separated by commas.\n");
            return;
        }

        Entry entry = new Entry(formatWord(parts[0]), formatWord(parts[1]), formatWord(parts[2]));

        try {
            entryService.insert(entry);
        } catch (EntryAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("\nWord added!");
    }

    public void deleteWord() {
        System.out.println("\nEnter the ID of the word to delete:");
        
        try {
            entryService.delete(Long.parseLong(scanner.nextLine()));
        } catch (EntryNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void editWord() {
        System.out.println("\nEnter the ID of the word to edit: ");

        try {
            Entry selectedEntry = entryService.findById(Long.parseLong(scanner.nextLine()));
            System.out.println("Current polish translate: " + selectedEntry.getPolish() + ", enter a new value or just press enter to keep the old value: ");
            String word = scanner.nextLine();
            if (!word.isBlank()) selectedEntry.setPolish(word);

            System.out.println("Current english translate: " + selectedEntry.getEnglish() + ", enter a new value or just press enter to keep the old value: ");
            word = scanner.nextLine();
            if (!word.isBlank()) selectedEntry.setEnglish(word);

            System.out.println("Current german translate: " + selectedEntry.getGerman() + ", enter a new value or just press enter to keep the old value: ");
            word = scanner.nextLine();
            if (!word.isBlank()) selectedEntry.setGerman(word);

            entryService.update(selectedEntry);

        } catch (EntryNotFoundException e) {
            System.err.println(e.getMessage());
            return;
        } catch (EntryAlreadyExistsException e) {
            throw new RuntimeException(e);
        }

    }

    public void displayWords() {
        System.out.println("\nSelect the sorting language:\n");
        System.out.println(Colors.RED + "1" + Colors.RESET + ". Polish");
        System.out.println(Colors.YELLOW + "2" + Colors.RESET + ". English");
        System.out.println(Colors.GREEN + "3" + Colors.RESET + ". German");
        System.out.print(Colors.BOLD + "\nChoose an action (or press any other key for no sorting):" + Colors.RESET);

        String languageChoice = scanner.nextLine();

        System.out.println("\nSelect the sorting order:");
        System.out.println(Colors.BLACK + "1" + Colors.RESET + ". Ascending");
        System.out.println(Colors.WHITE + "2" + Colors.RESET + ". Descending");
        System.out.print(Colors.BOLD + "\nChoose an action: " + Colors.RESET);

        String orderChoice = scanner.nextLine();

        List<Entry> entries;
        
        switch (languageChoice){
            case "1" -> entries = entryService.findAllByOrderByPolish(orderChoice.equals("1"));
            case "2" -> entries = entryService.findAllByOrderByEnglish(orderChoice.equals("1"));
            case "3" -> entries = entryService.findAllByOrderByGerman(orderChoice.equals("1"));
            default -> {
                System.out.println("\nInvalid language or sorting option. Displaying unsorted list.");
                entries = entryService.findAll();
            }
        }
        
        
        if (entries.isEmpty()) {
            System.out.println("\nThe dictionary is empty.");
            return;
        }

        System.out.print("\nWould you like to filter the results by a phrase? (yes/no): ");
        String filterChoice = scanner.nextLine().trim().toLowerCase();

        if (filterChoice.equals("yes")) {

            System.out.print("Enter the search phrase: ");
            String searchPhrase = scanner.nextLine().trim();

            entries = entryService.searchByPart(searchPhrase);
        }

        if (entries.isEmpty()) {
            System.out.println("\nNo results found for the given search phrase.");
            return;
        }

        String format = "%-5s| %-20s | %-20s | %-20s%n";

        System.out.println();
        System.out.printf(format, "№", "Polish", "English", "German");
        System.out.println(Colors.GREEN + "----------------------------------------------------------" + Colors.RESET);

        for (Entry entry : entries) {
            System.out.printf(format, entry.getId(), displayService.format(entry.getPolish()), displayService.format(entry.getEnglish()), displayService.format(entry.getGerman()));
        }
    }

    public void startTest() {
        List<Entry> entries = entryService.findAll();
        if (entries.isEmpty()) {
            System.out.println("\nThere are no words for the test.");
            return;
        }
        Random random = new Random();
        Entry word = entries.get(random.nextInt(entries.size()));

        int randomLanguage = random.nextInt(3);

        switch (randomLanguage) {
            case 0 -> { //Polish
                System.out.println("\nTranslate the word from Polish: " + Colors.BOLD + word.getPolish() + Colors.RESET);
                System.out.print("English: ");
                String userEnglish = scanner.nextLine().trim().toLowerCase();
                System.out.print("German: ");
                String userGerman = scanner.nextLine().trim().toLowerCase();

                if (userEnglish.equals(word.getEnglish().toLowerCase()) &&
                        userGerman.equals(word.getGerman().toLowerCase())) {
                    System.out.println(Colors.BOLD + (Colors.GREEN+"\nCorrect!"+Colors.RESET) + Colors.RESET);
                } else {
                    System.out.println("\nWrong! Correct answer: English = " + Colors.BOLD + word.getEnglish() + Colors.RESET + ", German = " + Colors.BOLD + word.getGerman() + Colors.RESET);
                }
            }
            case 1 -> { //English
                System.out.println("\nTranslate the word from English: " + Colors.BOLD + word.getEnglish() + Colors.RESET);
                System.out.print("Polish: ");
                String userPolish = scanner.nextLine().trim().toLowerCase();
                System.out.print("German: ");
                String userGerman = scanner.nextLine().trim().toLowerCase();

                if (userPolish.equals(word.getPolish().toLowerCase()) &&
                        userGerman.equals(word.getGerman().toLowerCase())) {
                    System.out.println(Colors.BOLD + (Colors.GREEN+"\nCorrect!"+Colors.RESET) + Colors.RESET);
                } else {
                    System.out.println("\nWrong! Correct answer: Polish = " + Colors.BOLD + word.getPolish() + Colors.RESET + ", German = " + Colors.BOLD + word.getGerman() + Colors.RESET);
                }
            }
            case 2 -> { //German
                System.out.println("\nTranslate the word from German: " + Colors.BOLD + word.getGerman() + Colors.RESET);
                System.out.print("Polish: ");
                String userPolish = scanner.nextLine().trim().toLowerCase();
                System.out.print("English: ");
                String userEnglish = scanner.nextLine().trim().toLowerCase();

                if (userPolish.equals(word.getPolish().toLowerCase()) &&
                        userEnglish.equals(word.getEnglish().toLowerCase())) {
                    System.out.println(Colors.BOLD + (Colors.GREEN+"\nCorrect!"+Colors.RESET) + Colors.RESET);
                } else {
                    System.out.println("\nWrong! Correct answer: Polish = " + Colors.BOLD + word.getPolish() + Colors.RESET + ", English = " + Colors.BOLD + word.getEnglish() + Colors.RESET);
                }
            }
            default -> throw new IllegalStateException();
        }
    }

    private String formatWord(String word) {
        if (word == null || word.isEmpty()) return word;
        word = word.trim();
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public void start() {
        while (true) {
            System.out.println(Colors.RED + "\n1" + Colors.RESET + ". Add a word");
            System.out.println(Colors.YELLOW + "2" + Colors.RESET + ". Delete a word");
            System.out.println(Colors.GREEN + "3" + Colors.RESET + ". Edit a word");
            System.out.println(Colors.CYAN + "4" + Colors.RESET + ". Show all words");
            System.out.println(Colors.BLUE + "5" + Colors.RESET + ". Start the test");
            System.out.println(Colors.PURPLE + "6" + Colors.RESET + ". Exit");
            System.out.print(Colors.BOLD + "\nChoose an action: " + Colors.RESET);
            String choice = scanner.next();
            scanner.nextLine();

            switch (choice) {
                case "1" -> addWord();
                case "2" -> deleteWord();
                case "3" -> editWord();
                case "4" -> displayWords();
                case "5" -> startTest();
                case "6" -> System.exit(0);
                default -> System.err.println("\nWrong choice!");
            }
        }
    }
}