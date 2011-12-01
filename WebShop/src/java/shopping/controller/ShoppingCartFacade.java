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
import shopping.model.ShoppingCartPK;

/**
 *
 * @author Anand
 */
@Stateful
@LocalBean
public class ShoppingCartFacade {
    @PersistenceContext(unitName = "WebShopPU")
    EntityManager em;


    public void addToShoppingCart(String username, String gnomeType, int nbrOfUnits, double price) {
        ShoppingCartPK shoppingCartPK = new ShoppingCartPK(username, gnomeType);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setPrimaryKey(shoppingCartPK);
        shoppingCart.setNbrOfUnits(nbrOfUnits);
        shoppingCart.setPrice(price);
        em.persist(shoppingCart);
    }
    
}
