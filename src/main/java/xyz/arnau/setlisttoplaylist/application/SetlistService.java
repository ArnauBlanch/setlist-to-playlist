package xyz.arnau.setlisttoplaylist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.domain.Setlist;
import xyz.arnau.setlisttoplaylist.domain.SetlistRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SetlistService {

    private final SetlistRepository setlistRepository;

    public Optional<Setlist> getSetlist(String id) {
        return setlistRepository.getSetlist(id);
    }
}
