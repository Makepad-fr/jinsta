/* (C)2021 */
package io.makepad;

import io.makepad.jinsta.JInsta;
import io.makepad.jinsta.models.Configuration;

public class Test {

    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration(true, "cookies.data");
        JInsta j = new JInsta(config);

        if (config.isCookiesExists()) {
            j.login();
        } else {
            j.login(System.getenv("INSTAGRAM_USERNAME"), System.getenv("INSTAGRAM_PASSWORD"));
        }

        j.setUserProfile("yurtdisindaokumak");

        System.out.printf("The user full name %s", j.userProfile.getFullname());
    }
}
