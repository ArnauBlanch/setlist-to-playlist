package xyz.arnau.setlisttoplaylist.domain.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ResponseStatus(value = UNAUTHORIZED, reason = "Auth with music platform failed")
public class MusicPlatformAuthException extends RuntimeException {
}
