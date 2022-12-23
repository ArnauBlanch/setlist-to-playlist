package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmSetlist;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@CommonsLog
public class SetlistFmApiService {
    private static final int MAX_ARTIST_SEARCH_RESULTS = 10;
    private static final String ARTIST_SEARCH_SORT = "relevance";
    private final SetlistFmApi setlistFmApi;

    public Optional<SetlistFmSetlist> getSetlist(String setlistId) {
        try {
            var response = setlistFmApi.getSetlist(setlistId).execute();
            if (response.isSuccessful() && response.body() != null) {
                return Optional.of(response.body());
            } else if (response.code() == NOT_FOUND.value()) {
                return Optional.empty();
            } else {
                log.error("Could not get setlist (setlistId=%s)".formatted(setlistId));
                throw new RuntimeException("Setlist.fm API error");
            }
        } catch (IOException e) {
            log.error("Could not get setlist (setlistId=%s)".formatted(setlistId));
            throw new RuntimeException(e);
        }
    }

    public List<SetlistFmArtist> searchArtists(String nameQuery) {
        try {
            var response = setlistFmApi.searchArtists(nameQuery, ARTIST_SEARCH_SORT).execute();
            if (response.isSuccessful() && response.body() != null && response.body().getArtists() != null) {
                return response.body().getArtists().stream()
                        .limit(MAX_ARTIST_SEARCH_RESULTS)
                        .collect(toList());
            } else if (response.code() == NOT_FOUND.value()) {
                return emptyList();
            } else {
                log.error("Could not search artists (nameQuery=%s)".formatted(nameQuery));
                throw new RuntimeException("Setlist.fm API error");
            }
        } catch (IOException e) {
            log.error("Could not search artists (nameQuery=%s)".formatted(nameQuery));
            throw new RuntimeException(e);
        }
    }
}
