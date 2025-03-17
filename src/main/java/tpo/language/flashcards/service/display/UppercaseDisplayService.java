package tpo.language.flashcards.service.display;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("uppercase")
public class UppercaseDisplayService implements DisplayService{
    @Override
    public String format(String text) {
        return text.toUpperCase();
    }
}
