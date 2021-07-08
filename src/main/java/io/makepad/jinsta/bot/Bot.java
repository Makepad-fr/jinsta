package io.makepad.jinsta.bot;

import io.makepad.jinsta.structs.UserProfile;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Bot {
    private final WebDriver driver;
    private final WebDriverWait wait;
    protected static final Bot shared = new Bot();
    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public Bot(){
        DesiredCapabilities caps = new DesiredCapabilities();
        FirefoxProfile profile = new FirefoxProfile();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-private");
        options.addPreference("browser.privatebrowsing.autostart", true);
        this.driver = new FirefoxDriver(options);
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(120));
    }

}
