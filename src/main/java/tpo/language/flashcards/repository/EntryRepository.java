package tpo.language.flashcards.repository;

import org.springframework.stereotype.Repository;
import tpo.language.flashcards.model.Entry;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EntryRepository {
    private final List<Entry> entries = new ArrayList<>();

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public List<Entry> getAllEntries() {
        return entries;
    }
}