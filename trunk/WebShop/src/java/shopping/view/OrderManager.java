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
import shopping.controller.OrderFacade;
import shopping.model.OrderItem;

/**
 *
 * @author Anand
 */
@Named(value = "orderManager")
@SessionScoped
public class OrderManager implements Serializable {
    @EJB
    private OrderFacade orderFacade;
    private String gnomeType;
    private int nbrOfUnits;
    private double price;
    private List<OrderItem> orderLst = new ArrayList<OrderItem>();

    public List<OrderItem> getOrderLst() {
        return orderLst;
    }

    public boolean isOrderNotEmpty() {
        refreshPage();
        if (orderLst.size() > 0) {
            return true;
        }
        return false;
    }
    
    public void setOrderLst(List<OrderItem> orderLst) {
        this.orderLst = orderLst;
    }

    public String getGnomeType() {
        return gnomeType;
    }

    public void setGnomeType(String gnomeType) {
        this.gnomeType = gnomeType;
    }

    public int getNbrOfUnits() {
        return nbrOfUnits;
    }

    public void setNbrOfUnits(int nbrOfUnits) {
        this.nbrOfUnits = nbrOfUnits;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public double getTotalPrice() {
        double totalPrice = 0;
        if (orderLst.size() > 0) {
            for (OrderItem item : orderLst) {
                totalPrice = totalPrice + item.getPrice();
            }
        }
        return totalPrice;
    }
    /** Creates a new instance of OrderManager */
    public OrderManager() {
    }

    private void refreshPage() {
        setOrderLst(orderFacade.getOrderItems());
    }
}
