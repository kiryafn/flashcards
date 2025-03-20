package tpo.language.flashcards.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String polish;
    private String english;
    private String german;

    public Entry(String polish, String english, String german) {
        this.polish = polish;
        this.english = english;
        this.german = german;
    }

    public Entry() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolish() {
        return polish;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getGerman() {
        return german;
    }

    public void setGerman(String german) {
        this.german = german;
    }

    @Override
    public String toString() {
        return polish + "," + english + "," + german;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Entry entry = (Entry) obj;
        return (polish.equals(entry.polish)) &&
               (english.equals(entry.english)) &&
               (german.equals(entry.german));

    }
}