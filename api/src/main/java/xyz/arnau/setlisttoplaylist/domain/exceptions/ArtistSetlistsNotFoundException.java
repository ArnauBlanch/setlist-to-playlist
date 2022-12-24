package xyz.arnau.setlisttoplaylist.domain.exceptions;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND, reason = "Artist setlists not found")
@ResponseBody
public class ArtistSetlistsNotFoundException extends RuntimeException {
}
