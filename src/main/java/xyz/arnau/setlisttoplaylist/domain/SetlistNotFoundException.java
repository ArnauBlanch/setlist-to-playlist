package xyz.arnau.setlisttoplaylist.domain;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND, reason = "Setlist not found")
public class SetlistNotFoundException extends RuntimeException {
}
