/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.view;

import javax.inject.Named;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import login.controller.LoginFacade;
import login.model.UserAccountDTO;

/**
 *
 * @author Alex
 */
@Named(value = "usrManager")
@SessionScoped
public class UserManager implements Serializable {

    @EJB
    private LoginFacade loginFacade;
    private Exception loginFailure;
    private String userName;
    private String password;
    private UserAccountDTO userAccount;

    private void handleException(Exception e) {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        e.printStackTrace(System.err);
        loginFailure = e;
    }

    /**
     * Returns the latest thrown exception.
     */
    public Exception getException() {
        return loginFailure;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return "";
    }

    public String getFullName() {
        return userAccount.getName();
    }

    public void login() {
        try {
            userAccount = loginFacade.login(userName, password);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            httpSession.setAttribute("user", this);
            loginFailure = null;
        } catch (Exception e) {
            handleException(e);
        }
    }

    public String logout() {
        userAccount = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "logout";
    }

    public boolean isLoggedIn() {
        return userAccount != null;
    }

    public int getRole() {
        if (!isLoggedIn()) {
            return 0;
        }
        return userAccount.getRole();
    }

    public boolean success() {
        return loginFailure == null;
    }
}
