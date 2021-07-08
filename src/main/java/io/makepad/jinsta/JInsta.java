package io.makepad.jinsta;


import io.makepad.jinsta.bot.Bot;

public class JInsta {
  public Bot bot;
  public JInsta(String username, String password) throws Exception {
    this.bot = new Bot();
    this.bot.login(username, password);
  }
  public JInsta(String filePath) {
    this.bot = new Bot();
    this.bot.loadCookies(filePath);
  }
}
