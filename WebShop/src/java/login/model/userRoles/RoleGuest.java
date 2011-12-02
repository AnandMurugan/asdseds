/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.model.userRoles;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Alex
 */
public class RoleGuest implements Role{
    private String homepage = "/WebShop/";
    private Set<String> allowedPages = new HashSet<String>();
    
    public RoleGuest() {
        allowedPages.add(homepage);
        allowedPages.add("/WebShop/faces/login.xhtml");
        allowedPages.add("/WebShop/faces/register.xhtml");
    }
    
    @Override
    public boolean isAllowed(String page) {
         if (allowedPages.contains(page)) {
            return true;
        }
        return false;
    }

    @Override
    public String redirectPage(String page) {
        return null;
    }

    @Override
    public String getHomePage() {
        return homepage;
    }
}
