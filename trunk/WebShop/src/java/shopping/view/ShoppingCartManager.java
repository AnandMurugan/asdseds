/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopping.view;

import inventory.controller.InventoryFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    @EJB
    private InventoryFacade inventoryFacade;
    private String gnomeType;
    private int nbrOfUnits;
    private double price;
//    private double totalPrice = 0;
    private List<ShoppingCartItem> shoppingCartItems = new ArrayList<ShoppingCartItem>();

    public double getTotalPrice() {
        double totalPrice = 0;
        if (shoppingCartItems.size() > 0) {
            for (ShoppingCartItem item : shoppingCartItems) {
                totalPrice = totalPrice + item.getPrice();
            }
        }
        return totalPrice;
    }

    public void calculateTotal() {
    }

    public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
//        calculateTotal();
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public boolean isCartNotEmpty() {
        refreshPage();
        if (shoppingCartItems.size() > 0) {
            return true;
        }
        return false;
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

    public int getNbrOfUnits() {
        return nbrOfUnits;
    }
    
    public void deleteAction(ShoppingCartItem item) {
        shoppingCartFacade.removeItemFromCart(item);
        refreshPage();
    }

    /** Creates a new instance of ShoppingCartManager */
    public ShoppingCartManager() {
    }

    public void addGnomes() {
        if (validateQuantity()) {
            shoppingCartFacade.addToShoppingCart(gnomeType, nbrOfUnits, price);
        }
    }

    public String checkout() {
        if (validateCheckOut()) {
            shoppingCartFacade.checkout();
            return "checkout";
        }
        return null;

    }

    private void refreshPage() {
        setShoppingCartItems(shoppingCartFacade.getShoppingCartItems());
    }

    private boolean validateQuantity() {
        int totalUnits = 0;
        FacesContext context;
        context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        ShoppingCartItem item = shoppingCartFacade.retrieveItem(gnomeType);
        if (item != null) {
            totalUnits = nbrOfUnits + item.getNbrOfUnits();
        }
        if (nbrOfUnits < 1) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Number should be positive.");
            message.setDetail("Number should be positive.");
            context.addMessage("gnomesForm:selectedUnits",message);
            return false;
        } else if (!(inventoryFacade.isQuantityValid(gnomeType, totalUnits))) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Selected quantity is not available. Please check your shopping cart for existing items.");
            message.setDetail("Selected quantity is not available. Please check your shopping cart for existing items.");
            context.addMessage("gnomesForm:selectedUnits",message);
            return false;
        }
        return true;
    }

    private boolean validateCheckOut() {
        FacesContext context;
        context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        if (shoppingCartItems.size() > 0) {
            for (ShoppingCartItem item : shoppingCartItems) {
                boolean debug = validateItemAvailability(item);
                System.out.println("Item's availability - "+debug+" for gnome type"+item.getGnomeType());
                if (!(debug)) {
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    message.setSummary("Selected quantity of "+item.getGnomeType()+" is not available.");
                    message.setDetail("Selected quantity of "+item.getGnomeType()+" is not available.");
                    context.addMessage("viewcartForm:submit", message);
                    return false;
                }
            }
        } else {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("No items in the Shopping cart.");
            message.setDetail("No items in the Shopping cart.");
            context.addMessage("viewcartForm:submit", message);
            return false;
        }

        return true;
    }

    private boolean validateItemAvailability(ShoppingCartItem item) {
        if (inventoryFacade.isQuantityValid(item.getGnomeType(), item.getNbrOfUnits())) {
            return true;
        }
        return false;
    }
}
