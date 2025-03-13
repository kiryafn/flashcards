package tpo.language.flashcards.controller;

import org.springframework.stereotype.Controller;
import tpo.language.flashcards.model.Entry;
import tpo.language.flashcards.repository.EntryRepository;
import tpo.language.flashcards.service.DisplayService;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Controller
public class FlashcardsController {
    private final EntryRepository repository;
    private final DisplayService displayService;
    private final Scanner scanner = new Scanner(System.in);

    public FlashcardsController(EntryRepository repository, DisplayService displayService) {
        this.repository = repository;
        this.displayService = displayService;
    }

    public void addWord() {
        System.out.print("Введите слово (польский, английский, немецкий через запятую): ");
        String[] parts = scanner.nextLine().split(",");
        if (parts.length == 3) {
            repository.addEntry(new Entry(parts[0].trim(), parts[1].trim(), parts[2].trim()));
            System.out.println("Слово добавлено!");
        } else {
            System.out.println("Неверный формат ввода.");
        }
    }

    public void displayWords() {
        List<Entry> entries = repository.getAllEntries();
        if (entries.isEmpty()) {
            System.out.println("Словарь пуст.");
            return;
        }
        entries.forEach(entry ->
                System.out.println(displayService.format(entry.toString()))
        );
    }

    public void startTest() {
        List<Entry> entries = repository.getAllEntries();
        if (entries.isEmpty()) {
            System.out.println("Нет слов для теста.");
            return;
        }
        Random random = new Random();
        Entry word = entries.get(random.nextInt(entries.size()));

        System.out.println("Переведите слово: " + word.getPolish());
        System.out.print("Английский: ");
        String userEnglish = scanner.nextLine().trim().toLowerCase();
        System.out.print("Немецкий: ");
        String userGerman = scanner.nextLine().trim().toLowerCase();

        if (userEnglish.equals(word.getEnglish().toLowerCase()) &&
                userGerman.equals(word.getGerman().toLowerCase())) {
            System.out.println("Правильно!");
        } else {
            System.out.println("Ошибка! Верный ответ: " + word.getEnglish() + ", " + word.getGerman());
        }
    }
}