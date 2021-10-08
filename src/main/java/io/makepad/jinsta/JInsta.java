/* (C)2021 */
package io.makepad.jinsta;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import io.makepad.jinsta.user.profile.UserProfile;
import io.makepad.socialwalker.commons.helpers.BotHelpers;
import io.makepad.socialwalker.commons.models.AbstractBot;
import io.makepad.socialwalker.commons.models.Configuration;
import io.makepad.socialwalker.commons.models.exceptions.CookieFileNotFoundException;
import io.makepad.socialwalker.commons.models.exceptions.TwoFactorAuthenticationRequiredException;
import java.net.MalformedURLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JInsta extends AbstractBot {

    public UserProfile userProfile;
    private static final Logger logger = LogManager.getLogger(JInsta.class);

    public JInsta(Configuration config) throws MalformedURLException {
        super(config, "https://instagram.com");
        userProfile = new UserProfile(this.driver, this.wait);
    }

    /**
     * Function handles the login action with username and password
     *
     * @param username The username for the current user
     * @param password The password for the current user
     * @throws TwoFactorAuthenticationRequiredException If the login ends with a two factor
     *     authentication input
     */
    public void login(String username, String password) throws Exception {
        BotHelpers.acceptCookies(super.wait, super.driver);
        super.wait.until(presenceOfElementLocated(LoginSelectors.USERNAME_INPUT));

        super.driver.findElement(LoginSelectors.USERNAME_INPUT).sendKeys(username);
        logger.info("Username field completed");
        super.driver.findElement(LoginSelectors.PASSWORD_INPUT).sendKeys(password);
        logger.info("Password field completed");
        super.wait.until(ExpectedConditions.elementToBeClickable(LoginSelectors.SUBMIT_BUTTON));
        logger.info("Submit button appeared");
        WebElement submit = super.driver.findElement(LoginSelectors.SUBMIT_BUTTON);
        JavascriptExecutor executor = (JavascriptExecutor) this.driver;
        executor.executeScript("arguments[0].click();", submit);
        logger.info("Clicked on submit button");
        super.wait.until(presenceOfElementLocated(LoginSelectors.SAVE_BUTTON));
        super.wait.until(ExpectedConditions.elementToBeClickable(LoginSelectors.SAVE_BUTTON));
        WebElement save = super.driver.findElement(LoginSelectors.SAVE_BUTTON);
        executor.executeScript("arguments[0].click();", save);
        logger.info("Clicked on save informations button");
        if (BotHelpers.isPresent(LoginSelectors.FEED, super.wait)) {
            logger.info("Navigated to the feed");
            super.saveCookies();
            return;
        }
        throw new TwoFactorAuthenticationRequiredException("Login failed. Can not reach the feed");
    }

    /**
     * Function handles the login action with the cookies
     *
     * @throws CookieFileNotFoundException If cookie file is not found
     */
    public void login() throws CookieFileNotFoundException {
        if (super.config.isCookiesExists()) {
            logger.info("Cookie file exists");
            super.loadCookies();
            logger.info("Cookies are loaded");
            return;
        }
        throw new CookieFileNotFoundException(
                String.format("cookie file %s does not exists", super.config.cookiesPath));
    }

    /**
     * Function set the username of the user profile attribute
     *
     * @param username The new username of the user profile
     */
    public void setUserProfile(String username) {
        this.userProfile.setUsername(username);
    }
}
