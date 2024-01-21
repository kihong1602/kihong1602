package com.blanc.recrute.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieManager {

  private CookieManager() {
    throw new IllegalStateException("Utility class");
  }

  public static Cookie getCookie(HttpServletRequest request, String cookieName) {
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if (findCookieByName(cookie, cookieName)) {
          return cookie;
        }
      }
    }
    return null;
  }

  public static Cookie createCookie(String name, String value, int age) {
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(age);
    return cookie;
  }

  public static String getSessionValue(HttpServletRequest request, Cookie cookie) {
    return (String) request.getSession()
                           .getAttribute(cookie.getValue());
  }

  private static boolean findCookieByName(Cookie cookie, String cookieName) {
    return cookie.getName()
                 .equals(cookieName);
  }
}