package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmSong;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyAlbum;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyImage;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyTrack;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongMapperTest {
    private static final String SPOTIFY_ID = "spotify123";
    private static final String SONG_NAME = "Reptilia";
    private static final String ARTIST = "The Strokes";

    @Mock
    private SpotifyApiService spotifyApiService;

    @InjectMocks
    private SetlistFmSongMapper songMapper;

    @Test
    public void whenSongIsFound_ReturnsSongWithId() {
        when(spotifyApiService.searchTrack(ARTIST, SONG_NAME))
                .thenReturn(Optional.of(SpotifyTrack.builder()
                        .id(SPOTIFY_ID)
                        .name(SONG_NAME)
                        .album(SpotifyAlbum.builder()
                                .name("Room On Fire")
                                .images(singletonList(new SpotifyImage("http://image.com/cover.jpg")))
                                .build())
                        .durationMs(3 * 60 * 1000)
                        .previewUrl("http://preview.com/song.mp3")
                        .build()));

        var song = songMapper.map(
                SetlistFmSong.builder().name(SONG_NAME).build(),
                SetlistFmArtist.builder().name(ARTIST).build());

        assertThat(song.id()).isEqualTo(SPOTIFY_ID);
        assertThat(song.name()).isEqualTo(SONG_NAME);
        assertThat(song.originalArtist()).isEqualTo(null);
        assertThat(song.previewUrl()).isEqualTo("http://preview.com/song.mp3");
        assertThat(song.albumName()).isEqualTo("Room On Fire");
        assertThat(song.albumCoverUrl()).isEqualTo("http://image.com/cover.jpg");
        assertThat(song.durationSeconds()).isEqualTo(3 * 60);
    }

    @Test
    public void whenSongIsCover_ReturnsOriginalSong() {
        String originalArtist = "OriginalArtist";
        when(spotifyApiService.searchTrack(originalArtist, SONG_NAME))
                .thenReturn(Optional.of(SpotifyTrack.builder()
                                .id(SPOTIFY_ID)
                                .name(SONG_NAME)
                                .album(SpotifyAlbum.builder()
                                        .name("Album")
                                        .images(singletonList(new SpotifyImage("img")))
                                        .build())
                                .durationMs(0)
                                .build()));

        var song = songMapper.map(
                SetlistFmSong.builder().name(SONG_NAME)
                        .cover(SetlistFmArtist.builder().name(originalArtist).build()).build(),
                SetlistFmArtist.builder().name(ARTIST).build());

        assertThat(song.id()).isEqualTo(SPOTIFY_ID);
        assertThat(song.name()).isEqualTo(SONG_NAME);
        assertThat(song.originalArtist()).isEqualTo(originalArtist);
    }

    @Test
    public void whenSongIsNotFound_ReturnsSongWithoutId() {
        when(spotifyApiService.searchTrack(ARTIST, SONG_NAME)).thenReturn(Optional.empty());

        var song = songMapper.map(
                SetlistFmSong.builder().name(SONG_NAME).build(),
                SetlistFmArtist.builder().name(ARTIST).build());

        assertThat(song.id()).isNull();
        assertThat(song.name()).isEqualTo(SONG_NAME);
        assertThat(song.originalArtist()).isEqualTo(null);
    }
}