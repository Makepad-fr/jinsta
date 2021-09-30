package io.makepad.jinsta.utils;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BotHelpers {

  /**
   * Function scrolls the given element
   * @param element The element to scroll
   */
  public static void scroll(WebElement element, WebDriver driver) {
    JavascriptExecutor js = (JavascriptExecutor)driver;
    js.executeScript("arguments[0].scrollBy(0,arguments[0].scrollHeight)",element);
    try {
      int sleepFor = (int)(Math.random() * 100000);
      System.out.printf("Will sleep for %d\n", sleepFor);
      Thread.sleep(sleepFor);
    } catch (InterruptedException e) {
      System.err.println("Sleep interrupted");
    }
  }

  /**
   * Checks if the given selector is present on the screen
   * @param by The selector
   * @return True if the selector is present on the screen, false if not
   */
  public static boolean isPresent(By by, WebDriverWait wait) {
    try {
      wait.until(presenceOfElementLocated(by));
      return true;
    } catch (TimeoutException e)  {
      return false;
    }
  }





  /**
   * Function accepts the cookie banner
   */
  public static void acceptCookies(WebDriverWait wait, WebDriver driver) {
    By path = By.xpath("(//div[@role='dialog']//button)[1]");
    if (isPresent(path, wait)) {
      WebElement acceptCookies = driver
          .findElement(path);
      acceptCookies.click();
    }
  }
}
