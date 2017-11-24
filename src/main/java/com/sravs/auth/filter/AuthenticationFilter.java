/*
package com.sravs.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private static final List<String> allowedUrls;

    static {
        allowedUrls = Arrays.asList("user/login", "login.html", "register.html");
    }

    public void init(FilterConfig fConfig) throws ServletException {
        System.out.println("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        System.out.println("Requested Resource:: "+uri);

        HttpSession session = req.getSession(false);


        boolean allowed = allowedUrls.stream().anyMatch(uri::endsWith);


        String contextPath = req.getContextPath();
        if((session == null && ! allowed) || (session != null && session.getAttribute("userId") == null && ! allowed)) {

            System.out.println("Unauthorized access request: "+uri);
            res.sendRedirect(contextPath+"/web/login.html");
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }


    }



    public void destroy() {
        //close any resources here
    }
}
*/
