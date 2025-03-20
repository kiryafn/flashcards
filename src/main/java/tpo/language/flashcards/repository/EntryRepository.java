package tpo.language.flashcards.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tpo.language.flashcards.model.Entry;

@Repository
public interface EntryRepository extends CrudRepository<Entry, Long> {

}