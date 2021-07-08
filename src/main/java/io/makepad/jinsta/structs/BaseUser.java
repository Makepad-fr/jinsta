package io.makepad.jinsta.structs;

public class BaseUser {
   private  String username;
    private String profileLink;

    public BaseUser(String username,String profileLink){
        this.profileLink = profileLink;
        this.username = username;
    }
}
