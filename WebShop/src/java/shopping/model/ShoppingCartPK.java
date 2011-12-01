/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopping.model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Anand
 */
@Embeddable
public class ShoppingCartPK implements Serializable{
    private String customerId;
    private String gnomeType;
    
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getGnomeType() {
        return gnomeType;
    }

    public void setGnomeType(String gnomeType) {
        this.gnomeType = gnomeType;
    }
    
    public ShoppingCartPK(){
        
    }
    
    public ShoppingCartPK(String customerId, String gnomeType){
        this.customerId = customerId;
        this.gnomeType = gnomeType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ShoppingCartPK other = (ShoppingCartPK) obj;
        if ((this.customerId == null) ? (other.customerId != null) : !this.customerId.equals(other.customerId)) {
            return false;
        }
        if ((this.gnomeType == null) ? (other.gnomeType != null) : !this.gnomeType.equals(other.gnomeType)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.customerId != null ? this.customerId.hashCode() : 0);
        hash = 29 * hash + (this.gnomeType != null ? this.gnomeType.hashCode() : 0);
        return hash;
    }
    
}
