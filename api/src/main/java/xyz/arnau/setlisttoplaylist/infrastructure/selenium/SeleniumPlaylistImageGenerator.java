package xyz.arnau.setlisttoplaylist.infrastructure.selenium;

import com.google.common.io.Resources;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;
import xyz.arnau.setlisttoplaylist.domain.ports.PlaylistImageGenerator;

import java.io.IOException;
import java.util.HashMap;

import static java.nio.charset.Charset.defaultCharset;
import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.time.format.FormatStyle.LONG;
import static java.util.Locale.US;
import static xyz.arnau.setlisttoplaylist.config.CacheConfig.COVER_IMAGE;
import static xyz.arnau.setlisttoplaylist.infrastructure.selenium.JpegImageConverter.convertImageToJpg;

@Component
@CommonsLog
@RequiredArgsConstructor
public class SeleniumPlaylistImageGenerator implements PlaylistImageGenerator {
    private final static String COVER_HTML_FILE = "playlist_cover.html";
    private final static String COVER_IMAGE_CLASS = "cover";

    private final SeleniumScreenshotService screenshotService;

    private static final String htmlTemplate = getCoverHtmlTemplate();

    @Cacheable(value = COVER_IMAGE, key = "#setlist.id", sync = true)
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

    private String generateCoverHtml(Setlist setlist) {
        return StringSubstitutor.replace(htmlTemplate, new HashMap<>() {{
            put("artistName", setlist.artist().name());
            put("artistImage", setlist.artist().imageUrl());
            put("venueName", setlist.venue().name());
            put("date", setlist.date().format(ofLocalizedDate(LONG).localizedBy(US)));
        }});
    }

    private static String getCoverHtmlTemplate() {
        try {
            return "data:text/html;charset=utf-8," +
                    Resources.toString(Resources.getResource(COVER_HTML_FILE), defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
