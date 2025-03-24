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
    public Entry update(Entry entry) throws EntryNotFoundException {
        Entry dbEntry = findById(entry.getId())
                .orElseThrow(() -> new EntryNotFoundException("Entry not found"));

        boolean isModified = !dbEntry.getEnglish().equals(entry.getEnglish())
                || !dbEntry.getPolish().equals(entry.getPolish())
                || !dbEntry.getGerman().equals(entry.getGerman());

        if (!isModified) {
            return dbEntry;
        }

        dbEntry.setEnglish(entry.getEnglish());
        dbEntry.setPolish(entry.getPolish());
        dbEntry.setGerman(entry.getGerman());

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

    public List<Entry> findAllByPart(String phrase) {
        String query = "SELECT e FROM Entry e WHERE " +
                "LOWER(e.polish) LIKE LOWER(:phrase) OR " +
                "LOWER(e.english) LIKE LOWER(:phrase) OR " +
                "LOWER(e.german) LIKE LOWER(:phrase)";

        return entityManager.createQuery(query, Entry.class)
                .setParameter("phrase", "%" + phrase + "%")
                .getResultList();
    }
}