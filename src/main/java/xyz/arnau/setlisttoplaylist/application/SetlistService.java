package xyz.arnau.setlisttoplaylist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.domain.Setlist;
import xyz.arnau.setlisttoplaylist.domain.SetlistNotFoundException;
import xyz.arnau.setlisttoplaylist.domain.SetlistRepository;

@Service
@RequiredArgsConstructor
public class SetlistService {

    private final SetlistRepository setlistRepository;

    public Setlist getSetlist(String id) {
        return setlistRepository.getSetlist(id)
                .filter(setlist -> setlist.songs() != null && !setlist.songs().isEmpty())
                .orElseThrow(SetlistNotFoundException::new);
    }
}
