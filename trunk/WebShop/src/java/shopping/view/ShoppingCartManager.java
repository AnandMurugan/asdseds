/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopping.view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import shopping.controller.ShoppingCartFacade;

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

    /** Creates a new instance of ShoppingCartManager */
    public ShoppingCartManager() {
    }

    public void addGnomes() {
        shoppingCartFacade.addToShoppingCart(username, gnomeType, nbrOfUnits, price);
    }
}
