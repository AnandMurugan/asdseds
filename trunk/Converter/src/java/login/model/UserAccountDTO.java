/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.model;

/**
 *
 * @author Alex
 */
public interface UserAccountDTO {
    public boolean checkPassword(String password);
    public String getName();
}
