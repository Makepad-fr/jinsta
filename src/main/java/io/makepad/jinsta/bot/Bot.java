package io.makepad.jinsta.bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Bot extends AbstractBot implements IBot {

    public Bot(){
      super();
    }

    public void login(String username, String password) {
        super.acceptCookies();
        final By userNameXPath = By.xpath("//input[@name='username']");
        super.wait.until(presenceOfElementLocated(userNameXPath));
        super.driver.findElement(userNameXPath).sendKeys(username);

        super.driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        final By submitXPath = By.xpath("//button[@type='submit']");
        super.wait.until(ExpectedConditions.elementToBeClickable(submitXPath));

        WebElement submit = super.driver.findElement(submitXPath);
        JavascriptExecutor executor = (JavascriptExecutor)super.driver;
        executor.executeScript("arguments[0].click();", submit);
        final By savePath = By.xpath("//main[@role='main']//section//button");
        super.wait.until(presenceOfElementLocated(savePath));
        super.wait.until(ExpectedConditions.elementToBeClickable(savePath));
        WebElement save = super.driver.findElement(savePath);
        executor.executeScript("arguments[0].click();", save);
    }

    /**
     * Returns the number of followers of the given user
     * @param username
     * @return
     */
    public int getUserFollowersNb(String username) {
        super.goToUserProfile(username);
        WebElement element;
        // First check if the user followers is a link
        By path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[2]/a/span");
        if (!super.isPresent(path)) {
            // If not check if it's just a text
            path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[2]/span/span");
            if (!super.isPresent(path)) {
                // If it is neither text nor a link, there's a problem
                // TODO: Throw a custom exception
                return -1;
            }
        }
        element = super.driver.findElement(path);
        // Replace comma and space with empty text
        int followers = Integer.parseInt(element.getAttribute("title").replaceAll("(\\s+|,)",""));
        return followers;
    }

    /**
     * Function returns the given user's full name if present
     * @param username The user's username
     * @return Full name of the user if present, null if not
     */
    public String getUserFullName(String username) {
        super.goToUserProfile(username);
        String fullName = null;
        WebElement element;
        By path = By.xpath("/html/body/div[1]/section/main/div/header/section/div[2]/h1");
        if (super.isPresent(path)) {
            super.wait.until(presenceOfElementLocated(path));
            element= super.driver.findElement(path);
            fullName = (element.getText());
        }
        return fullName;
    }

    /**
     * Returns the number of followings for the given user
     * @param username The username of the user
     * @return The number of the followings of the given user
     */
    public int getUserNbFollowingNb(String username) {
        super.goToUserProfile(username);
        // Check if the user followings number is a link
        By path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[3]/a/span");
        if (!super.isPresent(path)) {
            // If not check if it is just a text
            path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[3]/span/span");
            if (!super.isPresent(path)) {
                // If it is not throws an exception
                // TODO: Create custom exception
                return -1;
            }
        }
        // Get the element
        WebElement element = super.driver.findElement(path);
        // Replace spaces and commas with nothing
        int following = Integer.parseInt(element.getText().replaceAll("\\s+",""));
        return following;
    }

    /**
     * Function returns the users post number
     * @param username The username of the user
     * @return The number of the posts that user posted
     */
    public int getUserPostsNb(String username) {
        super.goToUserProfile(username);
        WebElement element;
        By path;
        path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[1]/span/span");
        if (super.isPresent(path)) {
            element = super.driver.findElement(path);
            int postNum = Integer.parseInt(element.getText().replaceAll("\\s+", ""));
            return postNum;
        }
        // TODO: Throws a custom exception
        return -1;
    }

    /**
     * Function returns the given user's bio
     * @param username The username of the user
     * @return The bio of the user if present, null if not
     */
    public String getUserBio(String username) {
        super.goToUserProfile(username);
        By path = By.xpath("/html/body/div[1]/section/main/div/header/section/div[2]/span");
        WebElement element;
        String bio = null;
        if (super.isPresent(path)) {
            super.wait.until(presenceOfElementLocated(path));
            super.driver.findElement(path);
            element= super.driver.findElement(path);
            bio = (element.getText());
        }
        return bio;
    }

    /**
     * Function check if the user's contact list are visible
     * @param username The username of the user
     * @return True if the user's contact list is visible
     */
    private boolean isUserContactsVisible(String username) {
        super.goToUserProfile(username);
        return super.isPresent(By.xpath(String.format("//a[@href='%s']", super.followersHref(username))));
    }

    /**
     * Function scrolls the popup that contains the contact list
    */
    private void scrollPopup() {
        super.scroll(driver.findElement(By.className("isgrP")));
    }

    /**
     * Save cookies to the given file path
     * @param filePath The file path to save the cookies
     */
    public void saveCookies(String filePath) {
        File file = new File(filePath);
        try
        {
            // Delete old file if exists
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);
            // loop for getting the cookie information
            System.out.println("Number of cookies " + driver.manage().getCookies().size());
            // loop for getting the cookie information
            for(Cookie ck : driver.manage().getCookies())
            {
                Bwrite.write((ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()));
                Bwrite.newLine();
            }
            Bwrite.close();
            fileWrite.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Function loads the cookies from the given file path
     * @param filePath The path of the cookie file to load
     */
    public void loadCookies(String filePath) {
        try{

            File file = new File(filePath);
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

    public void getUserFollowers(String username) {
        if(this.isUserContactsVisible(username)) {
            int nbFollowers = this.getUserFollowersNb(username);
            System.out.println("Number of followers " + nbFollowers);
            By followerPath = By.xpath(String.format("//a[@href='%s']", super.followersHref(username)));
            WebElement element = super.driver.findElement(followerPath);
            super.wait.until(presenceOfElementLocated(followerPath));
            super.wait.until(elementToBeClickable(followerPath));

            element.click();
            System.out.println("Here");
            while (!super.driver.getCurrentUrl().equals(String.format("https://www.instagram.com%s", super.followersHref(username)))) {
            }
            System.out.println("Navigated");
             By followerListPath = By.xpath("//div[@role='dialog']");
             By followerRowPath = By.xpath("//div[@role='dialog']//ul//li");

            super.wait.until(presenceOfElementLocated(followerListPath));
            WebElement e = super.driver.findElement(followerListPath);
            while (super.driver.findElements(followerListPath).size() <= nbFollowers) {
                this.scrollPopup();
            }
            System.out.println("Scrolled until the end");
        }
        // TODO: Complete function return
    }

}
