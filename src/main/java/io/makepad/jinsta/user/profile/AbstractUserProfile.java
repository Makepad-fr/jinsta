/* (C)2021 */
package io.makepad.jinsta.user.profile;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import io.makepad.socialwalker.commons.helpers.BotHelpers;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract class AbstractUserProfile {

    /**
     * Returns the href for the followings of the user
     *
     * @param username
     * @return
     */
    String followingsHref(String username) {
        return String.format("/%s/following/", username);
    }

    /**
     * Returns the href for the followers of the user
     *
     * @param username
     * @return
     */
    String followersHref(String username) {
        return String.format("/%s/followers/", username);
    }

    /**
     * Returns the URL for the given user's profile
     *
     * @param username The user's username
     * @return The URL of the given user's profile
     */
    String userProfileUrl(String username) {
        return String.format("https://www.instagram.com/%s", username);
    }

    /** Function scrolls the popup that contains the contact list */
    void scrollPopup(WebDriver driver) {
        BotHelpers.scroll(driver.findElement(By.className("isgrP")), driver);
    }

    /**
     * Function navigates to the user profile if the current url is different
     *
     * @param username The username of the user that we want to navigate
     */
    void goProfilePage(String username, WebDriver driver, WebDriverWait wait) {
        if (driver.getCurrentUrl().equals(userProfileUrl(username))) {
            return;
        }
        driver.get(userProfileUrl(username));
        BotHelpers.acceptCookies(wait, driver);
    }

    /**
     * Function returns the array of the contact's username with given selector and max number
     *
     * @param contactLinkSelector The selector of the contact button
     * @param max The max number of the contacts
     * @return The array of the contact's username or null if the contact information is not
     *     available
     */
    String[] getContactUserNames(
            String contactLinkSelector, int max, WebDriver driver, WebDriverWait wait) {
        String[] result = new String[max];
        String buttonSelector = String.format("//a[@href='%s']", contactLinkSelector);
        By followerPath = By.xpath(buttonSelector);
        WebElement element = driver.findElement(followerPath);
        wait.until(presenceOfElementLocated(followerPath));
        wait.until(elementToBeClickable(followerPath));

        element.click();
        System.out.println("Here");
        // FIXME: This empty while loop is ugly, we need to fix this somehow
        while (!driver.getCurrentUrl()
                .equals(String.format("https://www.instagram.com%s", contactLinkSelector))) {}
        System.out.println("Navigated");
        By followerListPath = By.xpath("//div[@role='dialog']");
        By followerRowPath = By.xpath("//div[@role='dialog']//ul//li");
        By followerUserNamePath = By.xpath("//div[@role='dialog']//ul//li//span//a");

        wait.until(presenceOfElementLocated(followerListPath));
        WebElement e = driver.findElement(followerListPath);
        while (driver.findElements(followerRowPath).size() < max) {
            this.scrollPopup(driver);
        }
        List<WebElement> followersList = driver.findElements(followerUserNamePath);
        WebElement followerElement;
        for (int i = 0; i < followersList.size(); i++) {
            followerElement = followersList.get(i);
            result[i] = followerElement.getAttribute("title");
        }
        return result;
    }
}
