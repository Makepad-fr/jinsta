package io.makepad.jinsta;

public interface IBot {
  void login(String username, String password) throws Exception;

  void close();
}
