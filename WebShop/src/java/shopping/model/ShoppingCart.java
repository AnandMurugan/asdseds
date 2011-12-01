/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopping.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author Anand
 */
@Entity
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private ShoppingCartPK primaryKey;
    private int nbrOfUnits;
    private double price;

    public ShoppingCartPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ShoppingCartPK primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getCustomerId() {
        return primaryKey.getCustomerId();
    }

    public String getGnomeType() {
        return primaryKey.getGnomeType();
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
    
}