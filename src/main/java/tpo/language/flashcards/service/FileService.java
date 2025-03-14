package tpo.language.flashcards.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tpo.language.flashcards.model.Entry;
import tpo.language.flashcards.repository.EntryRepository;

import java.io.*;


@Service
public class FileService {
    private final EntryRepository repository;
    private final String filename;


    public FileService(EntryRepository repository, @Value("${pl.edu.pja.tpo02.filename}") String filename) {
        this.repository = repository;
        this.filename = filename;
    }

    public void loadEntries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    repository.addEntry(new Entry(parts[0], parts[1], parts[2]));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
        }
    }

    public void saveEntry(Entry entry) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(filename), true))){
            String words = entry.toString();
            writer.println(words);
        }catch (FileNotFoundException e){
            System.err.println("File not found: " + filename);
        }
    }
}