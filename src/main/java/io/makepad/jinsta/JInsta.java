package io.makepad.jinsta;

import io.makepad.jinsta.models.Configuration;
import io.makepad.jinsta.user.profile.UserProfile;
import io.makepad.jinsta.utils.BotHelpers;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.StringTokenizer;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class JInsta implements IBot {
    private final WebDriver driver;
    private final WebDriverWait wait;
    public UserProfile userProfile;
    private final Configuration config;

    public JInsta(Configuration config) throws MalformedURLException {
        FirefoxProfile profile = new FirefoxProfile();
        FirefoxOptions options = new FirefoxOptions();
        //TODO: Use random user agent
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36 OPR/60.0.3255.170";
        options.setProfile(profile);
        options.addPreference("general.useragent.override",userAgent);
        if (config.remoteFirefoxURL == null) {
            this.driver = new FirefoxDriver(options);
        } else {
            this.driver = new RemoteWebDriver(new URL(config.remoteFirefoxURL), options);
        }
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(120));
        this.driver.manage().window().maximize();
        this.driver.get("https://www.instagram.com/");
        this.config = config;
        userProfile = new UserProfile(this.driver, this.wait);

    }

    public void login(String username, String password) throws Exception {
        BotHelpers.acceptCookies(this.wait, this.driver);
        final By userNameXPath = By.xpath("//input[@name='username']");
        this.wait.until(presenceOfElementLocated(userNameXPath));
        this.driver.findElement(userNameXPath).sendKeys(username);

        this.driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        final By submitXPath = By.xpath("//button[@type='submit']");
        this.wait.until(ExpectedConditions.elementToBeClickable(submitXPath));

        WebElement submit = this.driver.findElement(submitXPath);
        JavascriptExecutor executor = (JavascriptExecutor)this.driver;
        executor.executeScript("arguments[0].click();", submit);
        final By savePath = By.xpath("//main[@role='main']//section//button");
        this.wait.until(presenceOfElementLocated(savePath));
        this.wait.until(ExpectedConditions.elementToBeClickable(savePath));
        WebElement save = this.driver.findElement(savePath);
        executor.executeScript("arguments[0].click();", save);
        By by = By.xpath("//div[@role='presentation']");
        if (BotHelpers.isPresent(by, this.wait)) {
            System.out.println("Navigated to the feed");
            this.saveCookies();
            return;
        }
        // TODO: Add custom expception to throw
        throw new Exception();
    }

    public void login() {
        File f = new File(this.config.cookiesPath);
        if (f.exists()) {
            this.loadCookies();
            return;
        }
        // TODO: Add custom exception
    }

    /**
     * Function set the username of the user profile attribute
     * @param username The new username of the user profile
     */
    public void setUserProfile(String username) {
        this.userProfile.setUsername(username);
    }

    /**
     * Save cookies to the given file path
     */
    private void saveCookies() {
        if (this.config.useCookies) {
            File file = new File(this.config.cookiesPath);
            try {
                // Delete old file if exists
                file.delete();
                file.createNewFile();
                FileWriter fileWrite = new FileWriter(file);
                BufferedWriter Bwrite = new BufferedWriter(fileWrite);
                // loop for getting the cookie information
                System.out.println("Number of cookies " + driver.manage().getCookies().size());
                // loop for getting the cookie information
                for (Cookie ck : driver.manage().getCookies()) {
                    Bwrite.write(
                        (ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck
                            .getPath() + ";" + ck.getExpiry() + ";" + ck.isSecure()));
                    Bwrite.newLine();
                }
                Bwrite.close();
                fileWrite.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Function loads the cookies from the given file path
     */
    private void loadCookies() {
        try{
            File file = new File(this.config.cookiesPath);
            FileReader fileReader = new FileReader(file);
            BufferedReader Buffreader = new BufferedReader(fileReader);
            String strline;

            while((strline=Buffreader.readLine())!=null){
                StringTokenizer token = new StringTokenizer(strline,";");
                while(token.hasMoreTokens()){
                    String name = token.nextToken();
                    String value = token.nextToken();
                    String domain = token.nextToken();
                    String path = token.nextToken();
                    Date expiry = null;

                    String val;
                    if(!(val=token.nextToken()).equals("null"))
                    {
                        expiry = (new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")).parse(val);
                    }
                    boolean isSecure = Boolean.parseBoolean(token.nextToken());
                    Cookie ck = new Cookie(name,value,domain,path,expiry,isSecure);
                    driver.manage().addCookie(ck); // This will add the stored cookie to your current session
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Function closes the browser
     */
    public void close() {
        this.driver.close();
    }

}
