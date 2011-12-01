/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.model;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import login.model.userRoles.Role;
import login.model.userRoles.RoleGuest;
import login.model.userRoles.RoleUser;
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
        UserManager userManager = (UserManager) req.getSession().getAttribute("user");
        String pageRequested = req.getRequestURI().toString();

        System.out.println(pageRequested);
        Role r;
        if (userManager != null) {
            r = userManager.getUserRole();
        } else {
            r = new RoleGuest();
        }

        if (r.isAllowed(pageRequested)) {
            String redirectPath = r.redirectPage(pageRequested);
            if (redirectPath != null) {
                System.out.println("redirect");
                res.sendRedirect(redirectPath);
                return;
            }
            chain.doFilter(request, response);
            return;
        } else {
            System.out.println("not allowed");
            res.sendRedirect(r.getHomePage());
            return;
        }
    }

    @Override
    public void destroy() {
    }
}
