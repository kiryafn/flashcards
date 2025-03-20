package tpo.language.flashcards.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tpo.language.flashcards.model.Entry;

import java.util.List;

@Repository
public interface EntryRepository extends CrudRepository<Entry, Long> {

    List<Entry> findAllByOrderByEnglishAsc();
    List<Entry> findAllByOrderByEnglishDesc();

    List<Entry> findAllByOrderByPolishAsc();
    List<Entry> findAllByOrderByPolishDesc();

    List<Entry> findAllByOrderByGermanAsc();
    List<Entry> findAllByOrderByGermanDesc();
}