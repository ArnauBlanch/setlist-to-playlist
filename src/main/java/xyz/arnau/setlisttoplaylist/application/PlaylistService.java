package xyz.arnau.setlisttoplaylist.application;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.domain.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

import static java.time.format.FormatStyle.LONG;
import static java.util.Locale.US;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final SetlistService setlistService;
    private final PlaylistRepository playlistRepository;
    private final PlaylistImageGenerator playlistImageGenerator;

    private static final String PLAYLIST_NAME = "${artistName} at ${venueName} - ${date}";
    private static final String PLAYLIST_DESCRIPTION =
            "Setlist for ${artistName} concert at ${venueName} (${venueCity}, ${venueCountryCode}) on ${date}.";

    public Playlist createFromSetlist(String setlistId, boolean isPublic, String authorizationHeader) {
        var setlist = setlistService.getSetlist(setlistId);
        //if (setlist.isEmpty() || setlist.get().songs().isEmpty())
            //throw new SetlistNotFoundException();

        CreatePlaylistCommand command = CreatePlaylistCommand.builder()
                .name(fillData(PLAYLIST_NAME, setlist))
                .description(fillData(PLAYLIST_DESCRIPTION, setlist))
                .isPublic(isPublic)
                .songIds(setlist.songs().stream().map(Song::id).filter(Objects::nonNull).collect(toList()))
                .coverImage(playlistImageGenerator.generateImage(setlist))
                .build();
        return playlistRepository.create(command, authorizationHeader);
    }

    public byte[] getCoverImage(String setlistId) {
        var setlist = setlistService.getSetlist(setlistId);
        return playlistImageGenerator.generateImage(setlist);
    }

    private String fillData(String input, Setlist setlist) {
        return StringSubstitutor.replace(input, new HashMap<String, String>() {{
            put("artistName", setlist.artist().name());
            put("venueName", setlist.venue().name());
            put("venueCity", setlist.venue().city());
            put("venueCountry", setlist.venue().country());
            put("venueCountryCode", setlist.venue().countryCode());
            put("date", setlist.date().format(DateTimeFormatter.ofLocalizedDate(LONG).localizedBy(US)));
        }});
    }
}
