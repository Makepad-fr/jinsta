package io.makepad.jinsta.user.profile;

import java.util.Set;

public interface IUserProfile {
  void setUsername(String username);

  int getNbFollowers();

  String getFullname();

  int getNbFollowings();

  int getNbPosts();

  String[] getFollowers();

  String[] getFollowings();

  Set<String> getPostLinks();

  String getBio();
}
