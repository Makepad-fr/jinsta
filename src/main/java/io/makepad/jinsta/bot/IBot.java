package io.makepad.jinsta.bot;

public interface IBot {
  void login(String username, String password) throws Exception;
  int getUserFollowersNb(String username);
  String getUserFullName(String username);
  int getUserNbFollowingNb(String username);
  int getUserPostsNb(String username);
  String getUserBio(String username);
  String[] getUserFollowers(String username);
  void saveCookies(String filePath);
  void loadCookies(String filePath);
}
