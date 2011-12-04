/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 *
 * @author Anand
 */
@Entity
@NamedQuery(
        name="findAllGnomeType",
        query="SELECT OBJECT(inv) FROM Inventory inv"
)
public class Inventory implements InventoryDTO, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String gnomeType;
    private String gnomeDesc;
    private int nbrOfUnits;
    private double price;

    public Inventory() {
        
    }
    
    public Inventory(String gnomeType, String gnomeDesc, int nbrOfUnits, double price) {
        this.gnomeDesc = gnomeDesc;
        this.gnomeType = gnomeType;
        this.price = price;
        this.nbrOfUnits = nbrOfUnits;
    }
    
    @Override
    public String getGnomeType() {
        return gnomeType;
    }

    public void setGnomeType(String id) {
        this.gnomeType = id;
    }

    @Override
    public String getGnomeDesc() {
        return gnomeDesc;
    }

    public void setGnomeDesc(String gnomeDesc) {
        this.gnomeDesc = gnomeDesc;
    }

    @Override
    public int getNbrOfUnits() {
        return nbrOfUnits;
    }

    public void setNbrOfUnits(int nbrOfUnits) {
        this.nbrOfUnits = nbrOfUnits;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gnomeType != null ? gnomeType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.gnomeType == null && other.gnomeType != null) || (this.gnomeType != null && !this.gnomeType.equals(other.gnomeType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventory.model.Inventory[ id=" + gnomeType + " ]";
    }

}
