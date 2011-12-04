/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopping.controller;

import inventory.model.Inventory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import shopping.model.ShoppingCartItem;

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
        if (!(isItemExist(gnomeType))) {
            ShoppingCartItem shoppingCart = new ShoppingCartItem();
            shoppingCart.setCustomerId(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
            shoppingCart.setGnomeType(gnomeType);
            shoppingCart.setNbrOfUnits(nbrOfUnits);
            shoppingCart.setPrice(price);
            shoppingCart.setEditable(true);
            em.persist(shoppingCart);
        }else{
            System.out.println("Item exists");
        }

    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        String customerId = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        List<ShoppingCartItem> itemsLst = new ArrayList<ShoppingCartItem>();
        itemsLst = (List) em.createNamedQuery("RetrieveShoppingCartItemsByCustomer").setParameter("customerId", customerId).getResultList();
        return itemsLst;
    }

    private boolean isItemExist(String gnomeType) {
        String username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        try {
            ShoppingCartItem item = (ShoppingCartItem) em.createNamedQuery("RetrieveShoppingCartItem").setParameter("customerId", username).setParameter("gnomeType", gnomeType).getSingleResult();
            if (item == null) {
                return false;
            }
        }catch (NoResultException e) {
            e.printStackTrace();
            return false;
        }catch(NonUniqueResultException e1){
            e1.printStackTrace();
            return true;
        }
        return true;
    }

    public void checkout() {
        //update inventory and remove shopping cart items in a single transaction
        int units;
        List<ShoppingCartItem> itemsLst = new ArrayList<ShoppingCartItem>();
        itemsLst = getShoppingCartItems();
        for(ShoppingCartItem item:itemsLst){
            Inventory inv = em.find(Inventory.class, item.getGnomeType());
            if(inv == null){
                throw new EntityNotFoundException("The selected gnome type doesn't exist in inventory");
            }
            units = inv.getNbrOfUnits();
            units = units - item.getNbrOfUnits();
            if(units > 0){
                inv.setNbrOfUnits(units);
            }else{
                em.remove(inv);
            }
            em.remove(item);
        }
    }
}
