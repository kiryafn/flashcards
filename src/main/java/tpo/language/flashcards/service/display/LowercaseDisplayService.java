package tpo.language.flashcards.service.display;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("lowercase")
public class LowercaseDisplayService implements DisplayService {
    @Override
    public String format(String text) {
        return text.toLowerCase();
    }
}
