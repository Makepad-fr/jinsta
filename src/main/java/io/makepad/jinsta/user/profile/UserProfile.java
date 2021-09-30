package io.makepad.jinsta.user.profile;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import io.makepad.jinsta.utils.BotHelpers;
import java.util.LinkedHashSet;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserProfile extends AbstractUserProfile  implements IUserProfile {
  private final WebDriver driver;
  private final WebDriverWait wait;
  private String username;

  public UserProfile(WebDriver driver, WebDriverWait wait, String username) {
    this.driver = driver;
    this.wait = wait;
    this.username = username;
  }

  public UserProfile(WebDriver driver, WebDriverWait wait) {
    this.driver = driver;
    this.wait = wait;
  }

  /**
   * Function updates the username of the current UserProfile object
   * @param username The new username to change
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns the number of followers of the given user
   * @return The number of the followers for the current user profile
   */
  public int getNbFollowers() {
    super.goProfilePage(this.username, this.driver, this.wait);
    WebElement element;
    // First check if the user followers is a link
    By path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[2]/a/span");
    if (!BotHelpers.isPresent(path, this.wait)) {
      // If not check if it's just a text
      path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[2]/span/span");
      if (!BotHelpers.isPresent(path, this.wait)) {
        // If it is neither text nor a link, there's a problem
        // TODO: Throw a custom exception
        return -1;
      }
    }
    element = this.driver.findElement(path);
    // Replace comma and space with empty text
    return Integer.parseInt(element.getAttribute("title").replaceAll("(\\s+|,)",""));
  }

  /**
   * Function returns the given user's full name if present
   * @return Full name of the current user if present, null if not
   */
  public String getFullname() {
    super.goProfilePage(username, this.driver, this.wait);
    String fullName = null;
    WebElement element;
    By path = By.xpath("/html/body/div[1]/section/main/div/header/section/div[2]/h1");
    if (BotHelpers.isPresent(path, this.wait)) {
      this.wait.until(presenceOfElementLocated(path));
      element = this.driver.findElement(path);
      fullName = (element.getText());
    }
    return fullName;
  }

  /**
   * Returns the number of followings for the current user
   * @return The number of the followings of the current user
   */
  public int getNbFollowings() {
    super.goProfilePage(this.username, this.driver, this.wait);
    // Check if the user followings number is a link
    By path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[3]/a/span");
    if (!BotHelpers.isPresent(path, this.wait)) {
      // If not check if it is just a text
      path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[3]/span/span");
      if (!BotHelpers.isPresent(path, this.wait)) {
        // If it is not throws an exception
        // TODO: Create custom exception
        return -1;
      }
    }
    // Get the element
    WebElement element = this.driver.findElement(path);
    // Replace spaces and commas with nothing
    return Integer.parseInt(element.getText().replaceAll("(\\s+|,)",""));
  }


  /**
   * Function returns the number of posts of the current user
   * @return The number of the current user
   */
  public int getNbPosts() {
    super.goProfilePage(username, this.driver, this.wait);
    WebElement element;
    By path;
    path = By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[1]/span/span");
    if (BotHelpers.isPresent(path, this.wait)) {
      element = this.driver.findElement(path);
      return Integer.parseInt(element.getText().replaceAll("\\s+", ""));
    }
    // TODO: Throws a custom exception
    return -1;
  }

  /**
   * Function check if the user's contact list are visible
   * @return True if the user's contact list is visible
   */
  private boolean isContactsVisible() {
    super.goProfilePage(username, this.driver, this.wait);
    return BotHelpers.isPresent(By.xpath(String.format("//a[@href='%s']", super.followersHref(username))), this.wait);
  }

  /**
   * function returns the array of the usernames of the current user
   * @return The array of usernames of current user's followers
   */
  public String[] getFollowers() {
    int nbFollowers = this.getNbFollowers();
    if(this.isContactsVisible()) {
      return super.getContactUserNames(super.followersHref(username), nbFollowers, this.driver, this.wait);
    }
    return null;
  }

  /**
   * Function returns the array of the current user's followings
   * @return The array of the following's usernames of the current user
   */
  public String[] getFollowings() {
    int nbFollowings = this.getNbFollowings();
    if (this.isContactsVisible()) {
      return super.getContactUserNames(super.followingsHref(username), nbFollowings, this.driver, this.wait);
    }
    return null;
  }

  /**
   * Function returns links of the user's posts
   * @return A Set contains the links of the current user's posts
   */
  public Set<String> getPostLinks() {
    int nbPosts = this.getNbPosts();
    By path = By.xpath("//article//a");
    Set<String> result = new LinkedHashSet<String>(0);
    if (nbPosts > 0 && BotHelpers.isPresent(path, this.wait)) {
      result = new LinkedHashSet<String>(nbPosts);
    }
    // TODO: Complete function definition
    return result;
  }

  /**
   * Function returns the given user's bio
   * @return The bio of the user if present, null if not
   */
  public String getBio() {
    super.goProfilePage(this.username, this.driver, this.wait);
    By path = By.xpath("/html/body/div[1]/section/main/div/header/section/div[2]/span");
    WebElement element;
    String bio = null;
    if (BotHelpers.isPresent(path, this.wait)) {
      this.wait.until(presenceOfElementLocated(path));
      this.driver.findElement(path);
      element= this.driver.findElement(path);
      bio = (element.getText());
    }
    return bio;
  }


}
