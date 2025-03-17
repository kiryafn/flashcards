package tpo.language.flashcards.service.display;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("default")
public class DefaultDisplayService implements DisplayService{
    @Override
    public String format(String text) {
        return text;
    }
}
