package tpo.language.flashcards.controller;

import org.springframework.stereotype.Controller;
import tpo.language.flashcards.model.Entry;
import tpo.language.flashcards.repository.EntryRepository;
import tpo.language.flashcards.service.DisplayService;
import tpo.language.flashcards.service.FileService;

import java.io.FileReader;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Controller
public class FlashcardsController {
    private final FileService fileService;
    private final EntryRepository repository;
    private final DisplayService displayService;
    private final Scanner scanner = new Scanner(System.in);

    public FlashcardsController(FileService fileService, EntryRepository repository, DisplayService displayService) {
        this.fileService = fileService;
        this.repository = repository;
        this.displayService = displayService;
    }

    public void addWord() {
        System.out.print("Enter the word (Polish, English, German separated by commas): ");
        String[] parts = scanner.nextLine().split(",");

        for (int i = 0; i < parts.length; i++) {
            parts[i] = capitalizeWord(parts[i].trim().toLowerCase());
        }

        if (parts.length == 3) {
            Entry newEntry = new Entry(parts[0], parts[1], parts[2]);

            for (Entry entry : repository.getAllEntries()) {
                if (entry.equals(newEntry)) {
                    System.out.println("The word is already in the dictionary.");
                    return;
                }
            }
            fileService.saveEntry(newEntry);
            repository.addEntry(newEntry);
            System.out.println("Word added!");
        } else {
            System.out.println("Incorrect input format.");
        }
    }

    private String capitalizeWord(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public void displayWords() {
        List<Entry> entries = repository.getAllEntries();
        if (entries.isEmpty()) {
            System.out.println("The dictionary is empty.");
            return;
        }

        String format = "%-20s | %-20s | %-20s%n";

        System.out.println();
        System.out.printf(format, "Polish", "English", "German");
        System.out.println("----------------------------------------------------------");

        for (Entry entry : entries) {
            System.out.printf(format, displayService.format(entry.getPolish()), displayService.format(entry.getEnglish()), displayService.format(entry.getGerman()));
        }
    }

    public void startTest() {
        List<Entry> entries = repository.getAllEntries();
        if (entries.isEmpty()) {
            System.out.println("There are no words for the test.");
            return;
        }
        Random random = new Random();
        Entry word = entries.get(random.nextInt(entries.size()));

        int randomLanguage = random.nextInt(3);

        switch (randomLanguage) {
            case 0 -> { //Polish
                System.out.println("Translate the word: " + word.getPolish());
                System.out.print("English: ");
                String userEnglish = scanner.nextLine().trim().toLowerCase();
                System.out.print("German: ");
                String userGerman = scanner.nextLine().trim().toLowerCase();

                if (userEnglish.equals(word.getEnglish().toLowerCase()) &&
                        userGerman.equals(word.getGerman().toLowerCase())) {
                    System.out.println("Correct!");
                } else {
                    System.out.println("Wrong! Correct answer: English = " + word.getEnglish() + ", German = " + word.getGerman());
                }
            }
            case 1 -> { //English
                System.out.println("\nTranslate the word: " + word.getEnglish());
                System.out.print("Polish: ");
                String userPolish = scanner.nextLine().trim().toLowerCase();
                System.out.print("German: ");
                String userGerman = scanner.nextLine().trim().toLowerCase();

                if (userPolish.equals(word.getPolish().toLowerCase()) &&
                        userGerman.equals(word.getGerman().toLowerCase())) {
                    System.out.println("Correct!");
                } else {
                    System.out.println("Wrong! Correct answer: Polish = " + word.getPolish() + ", German = " + word.getGerman());
                }
            }
            case 2 -> { //German
                System.out.println("Translate the word: " + word.getGerman());
                System.out.print("Polish: ");
                String userPolish = scanner.nextLine().trim().toLowerCase();
                System.out.print("English: ");
                String userEnglish = scanner.nextLine().trim().toLowerCase();

                if (userPolish.equals(word.getPolish().toLowerCase()) &&
                        userEnglish.equals(word.getEnglish().toLowerCase())) {
                    System.out.println("Correct!");
                } else {
                    System.out.println("Wrong! Correct answer: Polish = " + word.getPolish() + ", English = " + word.getEnglish());
                }
            }
            default -> throw new IllegalStateException();
        }
    }
}