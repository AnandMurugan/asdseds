/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Alex
 */
@Named(value = "loginManager")
@SessionScoped
public class LoginManager implements Serializable {

    /** Creates a new instance of LoginManager */
    public LoginManager() {
    }
}
