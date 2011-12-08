/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user.view;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.controller.UserFacade;

/**
 *
 * @author Alex
 */
@ManagedBean
@RequestScoped
@Named(value = "authBackingBean")
public class UserHelper {

    @EJB
    private UserFacade userFacade;

    public String logout() {
        String result = "/index?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        context.getExternalContext().invalidateSession();
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
        return result;
    }

    public boolean loggedIn() {
        if (FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() == null) {
            return false;
        }
        return true;
    }

    public boolean renderPage(String page) {
        if (FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() == null) {
            return true;
        } else {
            String userName = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
            String role = userFacade.getUserRole(userName);
            if (role.equals("ADMIN") && page.equals("shopMain")) {
                return false;
            }
            if (role.equals("CUSTOMER") && page.equals("adminMain")) {
                return false;
            }
            if (role.equals("BANNED_CUSTOMER") && (page.equals("adminMain") || page.equals("shopMain"))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isBanned() {
        String userName = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        String role = userFacade.getUserRole(userName);
        if(role.equals("BANNED_CUSTOMER")) {
            return true;
        }
        return false;
    }
//    public void logout(){
//      System.out.println("Logout");
//      FacesContext ctx = FacesContext.getCurrentInstance();
//      ExternalContext ectx = ctx.getExternalContext();
//      HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
//      HttpSession session = (HttpSession)ectx.getSession(false);
//      session.invalidate();
//      
//    }
}