package io.makepad.jinsta.bot;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Auth  {
    public Auth(){

    }

    private void acceptCookies(){
        Bot.shared.getWait().until(presenceOfElementLocated(By.xpath("(//div[@role='dialog']//button)[1]")));
        WebElement acceptCookies = Bot.shared.getDriver().findElement(By.xpath("(//div[@role='dialog']//button)[1]"));
        acceptCookies.click();
    }


    public void login(String uname, String pwd){
        Bot.shared.getDriver().manage().window().maximize();
        Bot.shared.getDriver().get("https://www.instagram.com/");

        acceptCookies();
        final By userNameXPath = By.xpath("//input[@name='username']");
        Bot.shared.getWait().until(presenceOfElementLocated(userNameXPath));
        Bot.shared.getDriver().findElement(userNameXPath).sendKeys(uname);

        Bot.shared.getDriver().findElement(By.xpath("//input[@name='password']")).sendKeys(pwd);
        final By submitXPath = By.xpath("//button[@type='submit']");
        Bot.shared.getWait().until(ExpectedConditions.elementToBeClickable(submitXPath));

        WebElement submit = Bot.shared.getDriver().findElement(submitXPath);
        JavascriptExecutor executor = (JavascriptExecutor)Bot.shared.getDriver();
        executor.executeScript("arguments[0].click();", submit);

        final By savePath = By.xpath("/html/body/div[1]/section/main/div/div/div/section/div/button");
        Bot.shared.getWait().until(presenceOfElementLocated(savePath));
        Bot.shared.getWait().until(ExpectedConditions.elementToBeClickable(savePath));
        WebElement save = Bot.shared.getDriver().findElement(savePath);
        executor.executeScript("arguments[0].click()",save);

    }

}
