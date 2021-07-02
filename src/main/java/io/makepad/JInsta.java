package io.makepad;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class JInsta {
    private final WebDriver driver;
    private final WebDriverWait wait;
    public JInsta(){
        this.driver = new FirefoxDriver();
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(120));
    }

    private void acceptCookies(){
        wait.until(presenceOfElementLocated(By.xpath("(//div[@role='dialog']//button)[1]")));
        WebElement acceptCookies = driver.findElement(By.xpath("(//div[@role='dialog']//button)[1]"));
        acceptCookies.click();
    }


    public void login(String uname,String pwd){
        this.driver.manage().window().maximize();
        this.driver.get("https://www.instagram.com/");

        acceptCookies();
        final By userNameXPath = By.xpath("//input[@name='username']");
        wait.until(presenceOfElementLocated(userNameXPath));
        driver.findElement(userNameXPath).sendKeys(uname);

        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(pwd);
        final By submitXPath = By.xpath("//button[@type='submit']");
        wait.until(ExpectedConditions.elementToBeClickable(submitXPath));

        WebElement submit = driver.findElement(submitXPath);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", submit);

    }



}
