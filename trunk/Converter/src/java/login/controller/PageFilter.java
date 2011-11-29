/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import login.view.UserManager;

/**
 *
 * @author Alex
 */
public class PageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        UserManager userManager = (UserManager) req.getSession().getAttribute("userManager");
        String pageRequested = req.getRequestURI().toString();

        //role 1 - admin
        if (pageRequested.contains("/admin.xhtml")) {
            if (userManager == null || !userManager.isLoggedIn() || !(userManager.getRole() == 1)) {
                res.sendRedirect(req.getContextPath() + "/faces/converter.xhtml");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
