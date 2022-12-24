package xyz.arnau.setlisttoplaylist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.domain.entities.BasicSetlist;
import xyz.arnau.setlisttoplaylist.domain.entities.PagedList;
import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;
import xyz.arnau.setlisttoplaylist.domain.exceptions.ArtistSetlistsNotFoundException;
import xyz.arnau.setlisttoplaylist.domain.exceptions.SetlistNotFoundException;
import xyz.arnau.setlisttoplaylist.domain.ports.SetlistRepository;

@Service
@RequiredArgsConstructor
public class SetlistService {

    private final SetlistRepository setlistRepository;

    public Setlist getById(String setlistId) {
        return setlistRepository.getSetlist(setlistId)
                .filter(setlist -> setlist.songs() != null && !setlist.songs().isEmpty())
                .orElseThrow(SetlistNotFoundException::new);
    }

    public PagedList<BasicSetlist> getByArtistId(String artistId, int page) {
        return setlistRepository.getArtistSetlists(artistId, page)
                .orElseThrow(ArtistSetlistsNotFoundException::new);
    }
}
