package tpo.language.flashcards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import tpo.language.flashcards.exception.EntryAlreadyExistsException;
import tpo.language.flashcards.model.Entry;
import tpo.language.flashcards.repository.EntryRepository;

import java.io.*;


@Service
@PropertySource("classpath:values.yaml")
@ConfigurationProperties(prefix = "pl.edu.pja.tpo02")
public class FileService {
    private final EntryService entryService;
    private final String filename;

    @Autowired
    public FileService(EntryService entryService, @Value("${filename}") String filename) {
        this.entryService = entryService;
        this.filename = filename;
    }

    public void loadEntries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    entryService.insert(new Entry(parts[0], parts[1], parts[2]));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
        } catch (EntryAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }
}