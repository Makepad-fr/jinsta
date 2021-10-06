/* (C)2021 */
package io.makepad.jinsta.example;

import io.makepad.jinsta.JInsta;
import io.makepad.socialwalker.commons.models.Configuration;

public class Example {
    public static void main(String[] args) throws Exception {
        Configuration c = new Configuration(true, "jinsta_test_cookies.data");
        JInsta j = new JInsta(c);

        // EXAMPLE PURPOSE ONLY: username, password login function will also check if cookie exists
        // and login with cookies if needed.
        if (c.isCookiesExists()) {
            // If cookie already exists, it will login with cookies
            j.login();
        } else {
            // Login to instagram account with username and password
            j.login(System.getenv("INSTAGRAM_USERNAME"), System.getenv("INSTAGRAM_PASSWORD"));
        }

        j.setUserProfile("instagram");

        System.out.printf("User full name %s\n", j.userProfile.getFullname());
        System.out.printf("User number of followers %d\n", j.userProfile.getNbFollowers());
        System.out.printf("User number of followings %s\n", j.userProfile.getNbFollowings());
        System.out.printf("User bio %s\n", j.userProfile.getBio());
        for (String followingUsername : j.userProfile.getFollowings()) {
            System.out.printf("Following username %s\n", followingUsername);
        }
        // TODO: Check followers of another account
    }
}
