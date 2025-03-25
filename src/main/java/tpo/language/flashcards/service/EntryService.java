package tpo.language.flashcards.service;

import org.springframework.stereotype.Service;
import tpo.language.flashcards.exception.EntryAlreadyExistsException;
import tpo.language.flashcards.exception.EntryNotFoundException;
import tpo.language.flashcards.model.Entry;
import tpo.language.flashcards.repository.EntryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntryService {
    private final EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public void insert(Entry entry) throws EntryAlreadyExistsException {
        validateEntry(entry);
        entryRepository.save(entry);
    }

    public List<Entry> findAll() {
        List<Entry> entries = new ArrayList<>();
        entryRepository.findAll().forEach(entries::add);
        return entries;
    }

    public List<Entry> findAllByOrderByPolish(boolean ascending) {
       if (ascending) return entryRepository.findAllByOrderByPolishAsc();
       else return entryRepository.findAllByOrderByPolishDesc();
    }

    public List<Entry> findAllByOrderByEnglish(boolean ascending) {
        if (ascending) return entryRepository.findAllByOrderByEnglishAsc();
        else return entryRepository.findAllByOrderByEnglishDesc();
    }

    public List<Entry> findAllByOrderByGerman(boolean ascending) {
        if (ascending) return entryRepository.findAllByOrderByGermanAsc();
        else return entryRepository.findAllByOrderByGermanDesc();
    }

    public Entry findById(Long id) throws EntryNotFoundException {
        return entryRepository.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("Entry not found with id: " + id));
    }

    public void delete(Long id) throws EntryNotFoundException {
        Entry entry = findById(id);
        if (entry != null) entryRepository.deleteById(id);
    }

    public Entry update(Entry entry) throws EntryNotFoundException, EntryAlreadyExistsException {
        //validateEntry(entry);
        return entryRepository.save(entry);
    }

    public List<Entry> searchByPart(String part) {
        return entryRepository.findAllByPolishContainingIgnoreCaseOrEnglishContainingIgnoreCaseOrGermanContainingIgnoreCase(part, part, part);
    }

    public boolean isDuplicate(Entry entry) {
        return findAll().stream()
                .anyMatch(existingEntry ->
                                existingEntry.getPolish().equals(entry.getPolish()) &&
                                existingEntry.getEnglish().equals(entry.getEnglish()) &&
                                existingEntry.getGerman().equals(entry.getGerman()));
    }

    public void validateEntry(Entry entry) throws EntryAlreadyExistsException, IllegalArgumentException {
        if (isDuplicate(entry)) {
            throw new EntryAlreadyExistsException("This word is already in the dictionary!");
        }

        if (entry.getPolish() == null || entry.getPolish().isBlank()) {
            throw new IllegalArgumentException("Polish field cannot be null or empty");
        }
        if (entry.getEnglish() == null || entry.getEnglish().isBlank()) {
            throw new IllegalArgumentException("English field cannot be null or empty");
        }
        if (entry.getGerman() == null || entry.getGerman().isBlank()) {
            throw new IllegalArgumentException("German field cannot be null or empty");
        }
    }

    public double countEntries() {
        return entryRepository.count();
    }
}