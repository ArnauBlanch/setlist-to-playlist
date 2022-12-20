package xyz.arnau.setlisttoplaylist.infrastructure.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static java.time.Duration.of;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.OutputType.FILE;
import static org.openqa.selenium.support.ui.ExpectedConditions.jsReturnsValue;

@Service
public class SeleniumScreenshotService {
    public static final int WAIT_TIME_UNTIL_FONTS_LOADED_IN_SECONDS = 2;
    @Value("${selenium.remoteWebDriverUrl}")
    private String remoteWebDriverUrl;

    public File takeScreenshot(String html, String classSelector) throws IOException {
        var webDriver = getWebDriver();
        webDriver.get(html);
        waitUntilFontsAreLoaded(webDriver);
        var coverElement=  webDriver.findElement(className(classSelector));
        return coverElement.getScreenshotAs(FILE);
    }
    private WebDriver getWebDriver() throws MalformedURLException {
        return new RemoteWebDriver(
                new URL(remoteWebDriverUrl),
                new ChromeOptions()
                        .setHeadless(true)
                        .addArguments("--no-sandbox")
                        .setLogLevel(ChromeDriverLogLevel.OFF));
    }

    private static void waitUntilFontsAreLoaded(WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, of(WAIT_TIME_UNTIL_FONTS_LOADED_IN_SECONDS, SECONDS));
        webDriverWait.until(jsReturnsValue("return document.fonts.status === 'loaded';"));
    }
}
