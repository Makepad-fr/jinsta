/* (C)2021 */
package io.makepad.example;

import io.makepad.jinsta.JInsta;
import io.makepad.socialwalker.commons.models.Configuration;

public class Example {
    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration(true, "jinsta_example_cookies.data");
        JInsta j = new JInsta(config);

        /*
         * This check here is unnecessary just for the example purpose.
         * The login function with username and password will also execute this check
         * if the useCookies in configuration is true
         */
        if (config.isCookiesExists()) {
            j.login();
        } else {
            j.login(System.getenv("INSTAGRAM_USERNAME"), System.getenv("INSTAGRAM_PASSWORD"));
        }
        j.setUserProfile("instagram");

        System.out.println("User fullname is:");
        System.out.println(j.userProfile.getFullname());
    }
}
