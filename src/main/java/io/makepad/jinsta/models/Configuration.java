package io.makepad.jinsta.models;

import java.io.File;

public class Configuration {
  public final boolean useCookies;
  public final String cookiesPath;
  public final String remoteFirefoxURL;

  public Configuration(boolean useCookies, String cookiesPath, String remoteFirefoxURL) {
    this.useCookies = useCookies;
    this.cookiesPath = cookiesPath;
    this.remoteFirefoxURL = remoteFirefoxURL;
  }

  public Configuration(boolean useCookies, String cookiesPath) {
    this.useCookies = useCookies;
    this.cookiesPath = cookiesPath;
    this.remoteFirefoxURL = null;
  }

  /**
   * Function checks if the cookies at given path exists or not
   *
   * @return true if cookies exists on the given path, false if not
   */
  public boolean isCookiesExists() {
    return new File(this.cookiesPath).exists();
  }
}
