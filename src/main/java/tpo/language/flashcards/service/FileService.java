package tpo.language.flashcards.service;

import org.springframework.stereotype.Service;
import tpo.language.flashcards.model.Entry;
import tpo.language.flashcards.repository.EntryRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {
    private final EntryRepository repository;
    private final String filename;


    public FileService(EntryRepository repository, String filename) {
        this.repository = repository;
        this.filename = filename;
    }

    public void loadEntries() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    repository.addEntry(new Entry(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка загрузки файла: " + e.getMessage());
        }
    }
}