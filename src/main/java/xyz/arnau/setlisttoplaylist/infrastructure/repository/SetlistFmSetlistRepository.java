package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import xyz.arnau.setlisttoplaylist.domain.ArtistInfo;
import xyz.arnau.setlisttoplaylist.domain.Setlist;
import xyz.arnau.setlisttoplaylist.domain.SetlistRepository;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmApi;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistInfo;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SetlistFmSetlistRepository implements SetlistRepository {

    private final SetlistFmApi setlistFmApi;

    @Override
    public Optional<Setlist> getSetlist(String id) {
        try {
            Response<SetlistInfo> setlistInfoResponse = setlistFmApi.getSetlist(id).execute();
            SetlistInfo setlist = setlistInfoResponse.body();

            if (setlist != null) {
                return Optional.of(new Setlist(new ArtistInfo(setlist.getArtist().getName())));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
