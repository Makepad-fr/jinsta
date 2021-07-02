package io.makepad;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

//comment the above line and uncomment below line to use Chrome
//import org.openqa.selenium.chrome.ChromeDriver;
public class Test {


  public static void main(String[] args) {
    WebDriver driver = new FirefoxDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    try {
      driver.get("https://google.com/ncr");
      driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);
      WebElement firstResult = wait.until(presenceOfElementLocated(By.cssSelector("h3")));
      System.out.println(firstResult.getAttribute("textContent"));
    } finally {
      driver.quit();
    }
  }
}
