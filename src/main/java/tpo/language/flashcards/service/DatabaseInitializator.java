package tpo.language.flashcards.service;

import org.springframework.stereotype.Component;
import tpo.language.flashcards.model.Entry;

@Component
public class DatabaseInitializator {

    private FileService fileService;
    private EntryService entryService;

    public DatabaseInitializator(FileService fileService, EntryService entryService) {
        this.fileService = fileService;
        this.entryService = entryService;
    }

    public void init() {
        if (entryService.countEntries() == 0){
            fileService.loadEntries();
        }
    }
}
