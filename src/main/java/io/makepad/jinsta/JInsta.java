package io.makepad.jinsta;


import io.makepad.jinsta.bot.Bot;

public class JInsta {
  public Bot bot;
  public JInsta(String username, String password) {
    this.bot = new Bot();
    this.bot.login(username, password);
  }
}
