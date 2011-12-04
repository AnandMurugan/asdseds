/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopping.view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import shopping.controller.ShoppingCartFacade;
import shopping.model.ShoppingCartItem;

/**
 *
 * @author Anand
 */
@Named(value = "shoppingCartManager")
@SessionScoped
public class ShoppingCartManager implements Serializable {
    @EJB
    private ShoppingCartFacade shoppingCartFacade;
    private String username;
    private String gnomeType;
    private int nbrOfUnits;
    private double price;
    private List<ShoppingCartItem> shoppingCartItems = new ArrayList<ShoppingCartItem>();

    public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }
    
    public List<ShoppingCartItem> getShoppingCartItems(){
        setShoppingCartItems(shoppingCartFacade.getShoppingCartItems());
        return shoppingCartItems;
    }

    public void setGnomeType(String gnomeType) {
        this.gnomeType = gnomeType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNbrOfUnits(int selectedUnits) {
        this.nbrOfUnits = selectedUnits;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String editAction(ShoppingCartItem item) {
	item.setEditable(true);
	return null;
    }
    
    /** Creates a new instance of ShoppingCartManager */
    public ShoppingCartManager() {
    }

    public void addGnomes() {
        shoppingCartFacade.addToShoppingCart(gnomeType, nbrOfUnits, price);
    }
    
    public String saveAction(){
        for(ShoppingCartItem item:shoppingCartItems){
            item.setEditable(false);
        }
        return null;
    }
    
    public String checkout(){
        shoppingCartFacade.checkout();
        return "checkout";
    }
}
