package io.makepad;

import io.makepad.jinsta.JInsta;
import java.io.File;

public class Test {

  public static void main(String[] args) throws Exception {
    final String COOKIE_FILE_PATH = "cookie.data";
    File f = new File(COOKIE_FILE_PATH);
    JInsta j;
    if(f.exists() && !f.isDirectory()) {
      // Cookie file exists load from cookies
      j = new JInsta(COOKIE_FILE_PATH);
    } else {
      // Cookie file does not exists
      j = new JInsta(System.getenv("INSTAGRAM_USERNAME"), System.getenv("INSTAGRAM_PASSWORD"));
      j.bot.saveCookies(COOKIE_FILE_PATH);
    }
    j.bot.getUserFollowers("yurtdisindaokumak");
  }
}