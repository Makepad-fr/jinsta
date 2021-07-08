package io.makepad.jinsta.bot;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract class AbstractBot {
  protected final WebDriver driver;
  protected final WebDriverWait wait;

  AbstractBot() {
    DesiredCapabilities caps = new DesiredCapabilities();
    FirefoxProfile profile = new FirefoxProfile();
    FirefoxOptions options = new FirefoxOptions();
    String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36 OPR/60.0.3255.170";
    options.addPreference("general.useragent.override",userAgent);
    this.driver = new FirefoxDriver(options);
    this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(120));
    this.driver.manage().window().maximize();
    this.driver.get("https://www.instagram.com/");

  }

  /**
   * Returns the URL for the given user's profile
   * @param username The user's username
   * @return The URL of the given user's profile
   */
  String userProfileUrl(String username) {
    return String.format("https://www.instagram.com/%s", username);
  }

  /**
   * Function navigates to the user profile if the current url is different
   * @param username The username of the user that we want to navigate
   */
  void goToUserProfile(String username) {
    if (driver.getCurrentUrl().equals(userProfileUrl(username))) {
      return;
    }
    driver.get(userProfileUrl(username));
  }



  /**
   * Function scrolls the given element
   * @param element The element to scroll
   */
  void scroll(WebElement element) {
    JavascriptExecutor js = (JavascriptExecutor)driver;
    js.executeScript("arguments[0].scrollBy(0,arguments[0].scrollHeight)",element);
    try {
      Thread.sleep((int)(Math.random() * 1000));
    } catch (InterruptedException e) {
      System.err.println("Sleep interrupted");
    }
  }

  /**
   * Checks if the given selector is present on the screen
   * @param by The selector
   * @return True if the selector is present on the screen, false if not
   */
  boolean isPresent(By by) {
    try {
      wait.until(presenceOfElementLocated(by));
      return true;
    } catch (TimeoutException e)  {
      return false;
    }
  }

  /**
   * Returns the href for the followers of the user
   * @param username
   * @return
   */
  String followersHref(String username) {
    return String.format("/%s/followers/", username);
  }

  /**
   * Returns the href for the followings of the user
   * @param username
   * @return
   */
  String followingsHref(String username) {
    return String.format("/%s/following/", username);
  }

  /**
   * Function accepts the cookie banner
   */
  void acceptCookies() {
    By path = By.xpath("(//div[@role='dialog']//button)[1]");
    if (this.isPresent(path)) {
      WebElement acceptCookies = this.driver
          .findElement(path);
      acceptCookies.click();
    }
  }

}
