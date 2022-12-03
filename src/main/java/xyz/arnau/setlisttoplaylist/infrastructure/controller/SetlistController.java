package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.arnau.setlisttoplaylist.application.SetlistService;
import xyz.arnau.setlisttoplaylist.domain.Setlist;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/setlist")
@RequiredArgsConstructor
public class SetlistController {

    private final SetlistService setlistService;

    @GetMapping("{id}")
    public ResponseEntity<Setlist> getSetlist(@PathVariable String id) {
        return setlistService.getSetlist(id)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }
}
