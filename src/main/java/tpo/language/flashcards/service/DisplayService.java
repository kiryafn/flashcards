package tpo.language.flashcards.service;

import org.springframework.stereotype.Service;

@FunctionalInterface
public interface DisplayService {
    String format(String text);
}