package io.makepad.jinsta.models;

public class BaseUser {
   private  String username;
    private String profileLink;

    public BaseUser(String username,String profileLink){
        this.profileLink = profileLink;
        this.username = username;
    }
}
