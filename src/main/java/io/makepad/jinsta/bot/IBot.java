package io.makepad.jinsta.bot;

public interface IBot {
  void login(String username, String password);
  int getUserFollowersNb(String username);
  String getUserFullName(String username);
  int getUserNbFollowingNb(String username);
  int getUserPostsNb(String username);
  String getUserBio(String username);
  void getUserFollowers(String username);
}
