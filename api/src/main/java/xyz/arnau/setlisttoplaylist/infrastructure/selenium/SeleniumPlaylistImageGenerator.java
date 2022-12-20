package xyz.arnau.setlisttoplaylist.infrastructure.selenium;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;
import xyz.arnau.setlisttoplaylist.domain.ports.PlaylistImageGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static com.google.common.io.Resources.getResource;
import static java.nio.file.Files.readString;
import static java.nio.file.Paths.get;
import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.time.format.FormatStyle.LONG;
import static java.util.Locale.US;
import static xyz.arnau.setlisttoplaylist.infrastructure.selenium.JpegImageConverter.convertImageToJpg;

@Component
@CommonsLog
@RequiredArgsConstructor
public class SeleniumPlaylistImageGenerator implements PlaylistImageGenerator {
    private final static String COVER_HTML_FILE = "playlist_cover.html";
    private final static String COVER_IMAGE_CLASS = "cover";

    private final SeleniumScreenshotService screenshotService;

    @Override
    public byte[] generateImage(Setlist setlist) {
        try {
            var html = generateCoverHtml(setlist);
            var imageFile = screenshotService.takeScreenshot(html, COVER_IMAGE_CLASS);
            return convertImageToJpg(imageFile);
        } catch (Exception e) {
            log.error("Could not generate playlist image", e);
            throw new RuntimeException(e);
        }
    }

    private String generateCoverHtml(Setlist setlist) throws URISyntaxException, IOException {
        String html = "data:text/html;charset=utf-8," +
                readString(get(getResource(COVER_HTML_FILE).toURI()).toFile().toPath());
        return StringSubstitutor.replace(html, new HashMap<>() {{
            put("artistName", setlist.artist().name());
            put("artistImage", setlist.artist().imageUrl());
            put("venueName", setlist.venue().name());
            put("date", setlist.date().format(ofLocalizedDate(LONG).localizedBy(US)));
        }});
    }
}
