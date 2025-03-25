package tpo.language.flashcards.service;

import org.springframework.stereotype.Component;
import tpo.language.flashcards.model.Entry;

@Component
public class DatabaseInitializator {

    private EntryService entryService;

    public DatabaseInitializator(EntryService entryService) {
        this.entryService = entryService;
    }

    public void init() {
        if (entryService.countEntries() == 0){

        }
    }
}
