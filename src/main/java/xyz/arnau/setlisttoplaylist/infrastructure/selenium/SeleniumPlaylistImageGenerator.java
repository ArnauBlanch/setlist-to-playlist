package xyz.arnau.setlisttoplaylist.infrastructure.selenium;

import org.apache.commons.text.StringSubstitutor;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.domain.PlaylistImageGenerator;
import xyz.arnau.setlisttoplaylist.domain.Setlist;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static com.google.common.io.Resources.getResource;
import static java.nio.file.Files.readString;
import static java.nio.file.Paths.get;
import static java.time.Duration.of;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.openqa.selenium.support.ui.ExpectedConditions.jsReturnsValue;

@Component
public class SeleniumPlaylistImageGenerator implements PlaylistImageGenerator {
    private final static String COVER_HTML = "playlist_cover.html";
    private final static String COVER_IMAGE_CLASS = "cover";

    @Override
    public byte[] generateImage(Setlist setlist) {
        try {
            var coverHtml = getCoverHtml(setlist);
            return loadCover(coverHtml).getScreenshotAs(OutputType.BYTES);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private WebElement loadCover(String html) throws URISyntaxException, IOException, InterruptedException {
        var driver = getDriver();
        driver.get(html);
        WebDriverWait webDriverWait = new WebDriverWait(driver, of(2, SECONDS));
        webDriverWait.until(jsReturnsValue("return document.fonts.status === 'loaded';"));
        return driver.findElement(By.className(COVER_IMAGE_CLASS));
    }

    private String getCoverHtml(Setlist setlist) throws URISyntaxException, IOException {
        String html = "data:text/html;charset=utf-8," +
                readString(get(getResource(COVER_HTML).toURI()).toFile().toPath());
        return StringSubstitutor.replace(html, new HashMap<>() {{
            put("artistName", setlist.artist().name());
            put("artistImage", setlist.artist().image());
            put("venueName", setlist.venue().name());
            put("date", setlist.date());
        }});
    }

    private WebDriver getDriver() {
        return new ChromeDriver(
                new ChromeOptions()
                        .setHeadless(true)
                        .setLogLevel(ChromeDriverLogLevel.OFF));
    }
}
