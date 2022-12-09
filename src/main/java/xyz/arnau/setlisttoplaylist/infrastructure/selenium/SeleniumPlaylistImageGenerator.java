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

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static com.google.common.io.Resources.getResource;
import static java.nio.file.Files.readString;
import static java.nio.file.Paths.get;
import static java.time.Duration.of;
import static java.time.format.FormatStyle.LONG;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Locale.US;
import static javax.imageio.ImageIO.createImageOutputStream;
import static org.openqa.selenium.support.ui.ExpectedConditions.jsReturnsValue;

@Component
public class SeleniumPlaylistImageGenerator implements PlaylistImageGenerator {
    private final static String COVER_HTML = "playlist_cover.html";
    private final static String COVER_IMAGE_CLASS = "cover";

    @Override
    public byte[] generateImage(Setlist setlist) {
        try {
            var coverHtml = getCoverHtml(setlist);
            var inputFile = loadCover(coverHtml).getScreenshotAs(OutputType.FILE);
            try (var outputStream = new ByteArrayOutputStream()) {
                convertImageToJpg(inputFile, outputStream);
                return outputStream.toByteArray();
            }
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
            put("date", setlist.date().format(DateTimeFormatter.ofLocalizedDate(LONG).localizedBy(US)));
        }});
    }

    private WebDriver getDriver() {
        return new ChromeDriver(
                new ChromeOptions()
                        .setHeadless(true)
                        .setLogLevel(ChromeDriverLogLevel.OFF));
    }

    private static void convertImageToJpg(File inputFile, ByteArrayOutputStream outputStream) throws IOException {
        var writer  = ImageIO.getImageWritersByFormatName("jpg").next();
        writer.setOutput(createImageOutputStream(outputStream));

        var bufferedImage = removeAlphaChannel(ImageIO.read(inputFile));
        writer.write(null,
                new IIOImage(bufferedImage, null, null),
                writer.getDefaultWriteParam());
        writer.dispose();
    }

    private static BufferedImage removeAlphaChannel(BufferedImage img) {
        if (!img.getColorModel().hasAlpha()) {
            return img;
        }

        BufferedImage target = createImage(img.getWidth(), img.getHeight(), false);
        Graphics2D g = target.createGraphics();
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return target;
    }

    private static BufferedImage createImage(int width, int height, boolean hasAlpha) {
        return new BufferedImage(width, height, hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
    }
}
