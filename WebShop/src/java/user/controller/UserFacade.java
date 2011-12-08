/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user.controller;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import user.model.User_details;

/**
 *
 * @author Alex
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class UserFacade {

    @PersistenceContext(unitName = "WebShopPU")
    private EntityManager em;

    public UserFacade() {
    }

    public void registerCustomer(String userName, String password, String fullName) throws RegistrationException {
        User_details user = em.find(User_details.class, userName);

        if (user != null) {
            throw new RegistrationException("userName already exists");
        }
        try {
            user = new User_details(userName, password, fullName, "CUSTOMER", false);
            em.persist(user);
        } catch (Exception e) {
            throw new RegistrationException("registration not possible");
        }
    }

    public void banUser(String username) {
        User_details user = em.find(User_details.class, username);
        if (user != null) {
            user.setLGroup("BANNED_CUSTOMER");
            em.persist(user);
        }
    }

    public List<User_details> getUsrLst() {
        List<User_details> usrLst = new ArrayList<User_details>();
        usrLst = (List) em.createNamedQuery("RetrieveUsers").getResultList();
        return usrLst;
    }
    
    public String getUserRole(String userName) {
        User_details user = em.find(User_details.class, userName);
        if(user != null) {
            return user.getLgroup();
        }
        return "not a user";
    }
}
