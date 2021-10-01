package io.makepad.jinsta.user.profile;

import org.openqa.selenium.By;

abstract class ProfileSelectors {
  static final By
      CLICKABLE_FOLLOWERS =
          By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[2]/a/span"),
      PRIVATE_PROFILE_FOLLOWERS =
          By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[2]/span/span"),
      FULL_NAME = By.xpath("//h1"),
      CLICKABLE_FOLLOWINGS =
          By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[3]/a/span"),
      PRIVATE_PROFILE_FOLLOWINGS =
          By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[3]/span/span"),
      NUMBER_OF_POSTS =
          By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[1]/span/span"),
      POST_LINKS = By.xpath("//article//a"),
      BIO = By.xpath("/html/body/div[1]/section/main/div/header/section/div[2]/span");
}
