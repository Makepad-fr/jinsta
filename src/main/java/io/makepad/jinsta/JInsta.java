package io.makepad.jinsta;


import io.makepad.jinsta.bot.Auth;
import io.makepad.jinsta.bot.UserDetails;

public class JInsta {
    public UserDetails profile;
    private final Auth auth = new Auth();
    public JInsta(String uname,String pwd){
        this.auth.login(uname,pwd);
    }

    public void userProfile(String uname){
        this.profile = new UserDetails(uname);
    }
}
