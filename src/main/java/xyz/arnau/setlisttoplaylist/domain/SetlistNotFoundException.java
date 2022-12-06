package xyz.arnau.setlisttoplaylist.domain;

public class SetlistNotFoundException extends RuntimeException {
    public SetlistNotFoundException(String setlistId) {
        super("Setlist %s was not found".formatted(setlistId));
    }
}
