/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.view;

import javax.inject.Named;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import login.controller.LoginFacade;
import login.model.UserAccountDTO;

/**
 *
 * @author Alex
 */
@Named(value = "loginManager")
@ConversationScoped
public class LoginManager implements Serializable {

    @EJB
    private LoginFacade loginFacade;
    @Inject
    private Conversation conversation;
    private Exception loginFailure;
    
    private String userName;
    private String password;
    private UserAccountDTO userAccount;

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
    
    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        loginFailure = e;
    }
    
    /**
     * Returns <code>false</code> if the latest transaction failed, otherwise <code>false</code>.
     */
    public boolean getSuccess() {
        return loginFailure == null;
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
    
    public void login() {
        try {
            startConversation();
            userAccount = loginFacade.login(userName, password);
        } catch (Exception e) {
            handleException(e);
        }
    }
}
