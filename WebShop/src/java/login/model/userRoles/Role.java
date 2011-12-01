/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.model.userRoles;

/**
 *
 * @author Alex
 */
public interface Role {
    public boolean isAllowed(String page);
    public String redirectPage(String page);
    public String getHomePage();
}
