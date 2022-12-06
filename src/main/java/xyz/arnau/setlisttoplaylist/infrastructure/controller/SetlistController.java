package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.arnau.setlisttoplaylist.application.SetlistService;
import xyz.arnau.setlisttoplaylist.domain.Setlist;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/setlists")
@RequiredArgsConstructor
public class SetlistController {

    private final SetlistService setlistService;

    @GetMapping("{setlistId}")
    public ResponseEntity<Setlist> getSetlist(@PathVariable String setlistId) {
        Optional<Setlist> setlist = setlistService.getSetlist(setlistId);
        return setlist
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }
}
