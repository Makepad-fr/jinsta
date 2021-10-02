/* (C)2021 */
package io.makepad.jinsta.utils;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BotHelpers {

    private static final Logger logger = LogManager.getLogger(BotHelpers.class);

    /**
     * Function scrolls the given element
     *
     * @param element The element to scroll
     */
    public static void scroll(WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollBy(0,arguments[0].scrollHeight)", element);
        try {
            int sleepFor = (int) (Math.random() * 100000);
            System.out.printf("Will sleep for %d\n", sleepFor);
            Thread.sleep(sleepFor);
        } catch (InterruptedException e) {
            System.err.println("Sleep interrupted");
        }
    }

    /**
     * Checks if the given selector is present on the screen
     *
     * @param by The selector
     * @return True if the selector is present on the screen, false if not
     */
    public static boolean isPresent(By by, WebDriverWait wait) {
        try {
            logger.debug("Waiting for the selector");
            wait.until(presenceOfElementLocated(by));
            logger.info("Element appeared.");
            return true;
        } catch (TimeoutException e) {
            logger.catching(e);
            logger.error("");
            return false;
        }
    }

    /** Function accepts the cookie banner */
    public static void acceptCookies(WebDriverWait wait, WebDriver driver) {
        By path = By.xpath("(//div[@role='dialog']//button)[1]");
        logger.debug("Checking the cookies banner is present");
        if (isPresent(path, wait)) {
            WebElement acceptCookies = driver.findElement(path);
            logger.debug("Clicking on the accept cookies button");
            acceptCookies.click();
        }
    }
}
