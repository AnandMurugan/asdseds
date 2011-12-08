/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopping.controller;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import shopping.model.OrderItem;

/**
 *
 * @author Anand
 */
@Stateful
@LocalBean
public class OrderFacade {

    @PersistenceContext(unitName = "WebShopPU")
    EntityManager em;
    
    public List<OrderItem> getOrderItems() {
        String customerId = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        List<OrderItem> itemsLst = new ArrayList<OrderItem>();
        itemsLst = (List) em.createNamedQuery("RetrieveOrderItemsByCustomer").setParameter("customerId", customerId).getResultList();
        return itemsLst;
    }

}
