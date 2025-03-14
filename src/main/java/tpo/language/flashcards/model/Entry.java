package tpo.language.flashcards.model;


public class Entry {
    private String polish;
    private String english;
    private String german;

    public Entry(String polish, String english, String german) {
        this.polish = polish;
        this.english = english;
        this.german = german;
    }

    public String getPolish() { return polish; }
    public String getEnglish() { return english; }
    public String getGerman() { return german; }

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