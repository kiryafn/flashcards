package tpo.language.flashcards.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tpo.language.flashcards.exception.EntryNotFoundException;
import tpo.language.flashcards.model.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EntryRepository {
    private final EntityManager entityManager;

    EntryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void addEntry(Entry entry){
        entityManager.persist(entry);
    }

    public Optional<Entry> findById(Long id){
        return Optional.ofNullable(entityManager.find(Entry.class, id));
    }

    @Transactional
    public void deleteById(Long id){
        findById(id).ifPresent(entityManager::remove);
    }

    @Transactional
    public Entry update(Entry Entry) throws EntryNotFoundException {
        Entry dbEntry = findById(Entry.getId()).orElseThrow(() -> new EntryNotFoundException("Entry not found"));
        dbEntry.setEnglish(Entry.getEnglish());
        dbEntry.setPolish(Entry.getPolish());
        dbEntry.setGerman(Entry.getGerman());
        return dbEntry;
    }

    public List<Entry> findAll() {
        List<Entry> entries = entityManager.createQuery("SELECT e FROM Entry e", Entry.class).getResultList();
        if (entries.isEmpty() || entries == null) return new ArrayList<>();
        return entries;
    }

    public List<Entry> findAllOrdered(String fieldName, boolean ascending){
        List<String> allowedFields = List.of("polish", "english", "german");

        if (!allowedFields.contains(fieldName)) {
            return findAll();
        }

        String direction = ascending ? "ASC" : "DESC";
        String query = String.format("SELECT e FROM Entry e ORDER BY e.%s %s", fieldName, direction);

        return entityManager.createQuery(query, Entry.class).getResultList();
    }
}