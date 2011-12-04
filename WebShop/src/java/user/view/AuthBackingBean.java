/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user.view;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex
 */
@ManagedBean
@RequestScoped
@Named(value = "authBackingBean")
public class AuthBackingBean {
   
  public String logout() {
    String result="/index?faces-redirect=true";
     
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
    context.getExternalContext().invalidateSession();;
    return result;
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