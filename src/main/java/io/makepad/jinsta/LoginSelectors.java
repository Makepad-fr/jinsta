package io.makepad.jinsta;

import org.openqa.selenium.By;

public abstract class LoginSelectors {
  static final By USERNAME_INPUT = By.xpath("//input[@name='username']"),
      PASSWORD_INPUT = By.xpath("//input[@name='password']"),
      SUBMIT_BUTTON = By.xpath("//button[@type='submit']"),
      SAVE_BUTTON = By.xpath("//main[@role='main']//section//button"),
      FEED = By.xpath("//div[@role='presentation']");
}
