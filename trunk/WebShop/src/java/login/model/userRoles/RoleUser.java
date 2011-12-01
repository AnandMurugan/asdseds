/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.model.userRoles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Alex
 */
public class RoleUser implements Role{

    private String homePage = "/WebShop/faces/gnomes.xhtml";
    private Set<String> allowedPages = new HashSet<String>();
    private Map<String, String> redirectPages = new HashMap<String, String>();

    public RoleUser() {
        allowedPages.add("/WebShop/");
        allowedPages.add("/WebShop/faces/login.xhtml");
        allowedPages.add(homePage);

        redirectPages.put("/WebShop/", homePage);
        redirectPages.put("/WebShop/faces/login.xhtml", homePage);              
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
        if (redirectPages.containsKey(page)) {
            return redirectPages.get(page);
        }
        return null;
    }

    @Override
    public String getHomePage() {
        return homePage;
    }
}
