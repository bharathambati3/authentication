package com.sravs.auth.util;

import javax.enterprise.context.Dependent;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

@Dependent
public class AuthUtil {

    private static final String JSESSIONID = "JSESSIONID";

    public AuthUtil() {
    }

    public String constructFullPath(HttpServletRequest request, String relPath) {
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        return serverName + ":" + serverPort + contextPath + relPath;
    }

    public void invalidateSession(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(JSESSIONID)){
                    System.out.println("JSESSIONID="+cookie.getValue());
                }
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

    }

    public String getJSessionId(HttpServletRequest request) {
        Optional<Cookie> optional = Arrays.stream(request.getCookies()).filter(s -> s.getName().equals(JSESSIONID)).findFirst();

        if (optional.isPresent()) {
            Cookie cookie = optional.get();
            return cookie.getValue();
        }
        return null;
    }
}
