/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.controller;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.auth.login.LoginException;
import login.model.UserAccount;
import login.model.UserAccountDTO;

/**
 *
 * @author Alex
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class LoginFacade {

    @PersistenceContext(unitName = "WebShopPU")
    private EntityManager em;

    public UserAccountDTO login(String userName, String password) throws LoginException {
        UserAccountDTO user = em.find(UserAccount.class, userName);
        if (user == null || !user.checkPassword(password)) {
            throw new LoginException("userName and/or password are invalid");
        }
        if(user != null && user.isBanned()) {
            throw new LoginException("user is banned");
        }
        return user;
    }
}
