/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopping.controller;

import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import shopping.model.ShoppingCart;

/**
 *
 * @author Anand
 */
@Stateful
@LocalBean
public class ShoppingCartFacade {
    @PersistenceContext(unitName = "WebShopPU")
    EntityManager em;


    public void addToShoppingCart(String gnomeType, int nbrOfUnits, double price) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setGnomeType(gnomeType);
        shoppingCart.setNbrOfUnits(nbrOfUnits);
        shoppingCart.setPrice(price);
        shoppingCart.setCustomerId("userid");
        em.persist(shoppingCart);
    }
    
}
