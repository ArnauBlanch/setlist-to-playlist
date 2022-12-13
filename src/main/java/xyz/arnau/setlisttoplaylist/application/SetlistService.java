package xyz.arnau.setlisttoplaylist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;
import xyz.arnau.setlisttoplaylist.domain.exceptions.SetlistNotFoundException;
import xyz.arnau.setlisttoplaylist.domain.ports.SetlistRepository;

@Service
@RequiredArgsConstructor
public class SetlistService {

    private final SetlistRepository setlistRepository;

    public Setlist getSetlist(String setlistId) {
        return setlistRepository.getSetlist(setlistId)
                .filter(setlist -> setlist.songs() != null && !setlist.songs().isEmpty())
                .orElseThrow(SetlistNotFoundException::new);
    }
}
