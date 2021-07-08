package io.makepad.jinsta.bot;

import io.makepad.jinsta.structs.BaseUser;
import io.makepad.jinsta.structs.UserProfile;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserDetails {
    private String uname;
    public UserDetails(String uname){
        super();
        this.uname = uname;
        Bot.shared.getDriver().get("https://www.instagram.com/"+uname);
    }

    public int getNbFollowing(){
        WebElement element;
        By path;
        try{
            path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[3]/a/span");
            Bot.shared.getWait().until(presenceOfElementLocated(path));
        }catch (TimeoutException e){
            path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[3]/span/span");
            Bot.shared.getWait().until(presenceOfElementLocated(path));
        }
        element = Bot.shared.getDriver().findElement(path);
        int following = Integer.parseInt(element.getText().replaceAll("\\s+",""));
        System.out.println("Following = "+following);
        return following;
    }

    public int getNbFollowers(){
        WebElement element;
        By path;
        try{
            path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[2]/a/span");
            Bot.shared.getWait().until(presenceOfElementLocated(path));
        }catch (TimeoutException e){
            path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[2]/span/span");
            Bot.shared.getWait().until(presenceOfElementLocated(path));
        }
        element =Bot.shared.getDriver().findElement(path);
        int followers = Integer.parseInt(element.getAttribute("title").replaceAll("(\\s+|,)",""));
        System.out.println("Followers = "+followers);
        return followers;
    }

    public String getFullName(){
        String fullName;
        WebElement element;
        By path;
        try{
            path = By.xpath("/html/body/div[1]/section/main/div/header/section/div[2]/h1");
            Bot.shared.getWait().until(presenceOfElementLocated(path));
            element= Bot.shared.getDriver().findElement(path);
            fullName = (element.getText());
            System.out.println("Fullname = " + fullName);
        }catch (TimeoutException e){
            fullName = null;
            System.out.println("No full name found");
        }
        return fullName;
    }

    public int getNbPosts(){
        WebElement element;
        By path;
        path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[1]/span/span");
        Bot.shared.getWait().until(presenceOfElementLocated(path));

        element= Bot.shared.getDriver().findElement(path);
        int postNum = Integer.parseInt(element.getText().replaceAll("\\s+",""));
        System.out.println("Posts = " +postNum);
        return postNum;
    }

    public String getBio(){
        WebElement element;
        By path;

        String bio;
        try {
            path = By.xpath("/html/body/div[1]/section/main/div/header/section/div[2]/span");
            Bot.shared.getWait().until(presenceOfElementLocated(path));
            Bot.shared.getDriver().findElement(path);
            element= Bot.shared.getDriver().findElement(path);
            bio = (element.getText());
            System.out.println("Bio = " + bio);
        } catch (TimeoutException e) {
            bio = null;
            System.out.println("No bio found");
        }
        return bio;
    }

    public String getUserURL(){
        return "https://www.instagram.com/"+this.uname;
    }

    public UserProfile extractUserData(){
        return new UserProfile(getNbFollowers(),getNbFollowing(),getFullName(),getBio(),this.uname,getUserURL(),getNbPosts(),isVisible());
    }

    public boolean isVisible(){
        WebElement element;
        By path;
        try{
            path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[2]/a/span");
            Bot.shared.getWait().until(presenceOfElementLocated(path));
            return true;
        }catch (TimeoutException e){
            return false;
        }
    }

    public void scroll() {
        JavascriptExecutor js = (JavascriptExecutor) Bot.shared.getDriver();
        WebElement scroll = Bot.shared.getDriver().findElement(By.className("isgrP"));
        js.executeScript("arguments[0].scrollBy(0,arguments[0].scrollHeight)",scroll);
    }

    public BaseUser[] getFollowerArray(){
        if(isVisible()) {
            By followerPath = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[2]/a/span");
            WebElement element = Bot.shared.getDriver().findElement(followerPath);
            Bot.shared.getWait().until(presenceOfElementLocated(followerPath));
            Bot.shared.getWait().until(elementToBeClickable(followerPath));
            element.click();
            By followerListPath = By.xpath("/html/body/div[5]/div/div/div[2]");
            By followerRowPath = By.xpath("/html/body/div[5]/div/div/div[2]//li");

            Bot.shared.getWait().until(presenceOfElementLocated(followerListPath));
            WebElement e = Bot.shared.getDriver().findElement(followerListPath);
            int nbFollowers = getNbFollowers();
            while (Bot.shared.getDriver().findElements(followerListPath).size() <= nbFollowers) {
                scroll();
            }
            System.out.println("Scrolled until the end");
        }
        return null;
    }



}
