/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.view;

import inventory.controller.InventoryFacade;
import inventory.model.InventoryDTO;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author Anand
 */
@Named(value = "inventoryManager")
@SessionScoped
public class InventoryManager implements Serializable {

    @EJB
    private InventoryFacade inventoryFacade;
    private String gnomeType;
//    private static boolean initialize = false;
    private int selectedUnits;
    private String gnomeDesc;
    private int nbrOfUnits;
    private double price;
    private boolean selected = false;
    private boolean newGnome = false;
    List<String> gnomesLst = new ArrayList<String>();

    /** Creates a new instance of InventoryManager */
    public InventoryManager() {
    }

    public String getSubmitLbl() {
        if (newGnome) {
            return "Add gnome";
        } else {
            return "Save";
        }
    }
    
    public String getAdminTitle() {
        if (newGnome) {
            return "Add Inventory";
        } else {
            return "Update Inventory";
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public String getGnomeDesc() {
        return gnomeDesc;
    }

    public void setGnomeDesc(String gnomeDesc) {
        this.gnomeDesc = gnomeDesc;
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

    private void setPrice(double price) {
        this.price = price;
    }

    public String getGnomeType() {
        return gnomeType;
    }

    public void setGnomeType(String gnomeType) {
        this.gnomeType = gnomeType;
    }

    public int getSelectedUnits() {
        return selectedUnits;
    }

    public void setSelectedUnits(int selectedUnits) {
        this.selectedUnits = selectedUnits;
    }

    public List<String> getGnomesList() {
        gnomesLst = inventoryFacade.getGnomesList();
        if (gnomesLst.size() == 1) {
            refreshPage();
        }
        return gnomesLst;
    }

    public void refreshGnomeDetails(AjaxBehaviorEvent e) {
        retrieveGnomeDetails();
    }

    private void retrieveGnomeDetails() {
        try {
            InventoryDTO inventoryDTO = inventoryFacade.select(gnomeType);
            this.gnomeDesc = inventoryDTO.getGnomeDesc();
            this.nbrOfUnits = inventoryDTO.getNbrOfUnits();
            this.price = inventoryDTO.getPrice();
            this.selectedUnits = 0;
            selected = true;
        } catch (Exception e1) {
            handleException(e1);
        }
    }

    public String updateItem() {
        try {
            inventoryFacade.updateItem(gnomeType, gnomeDesc, nbrOfUnits, price);
            newGnome = false;
        } catch (Exception e) {
            handleException(e);
        }
        return "modified";
    }

    public String newGnome() {
        newGnome = true;
        this.gnomeDesc = "";
        this.gnomeType = "";
        this.nbrOfUnits = 0;
        this.price = 0;
        return "addGnome";
    }

    public void oldGnome() {
        newGnome = false;
    }

    public boolean isNewGnome() {
        return newGnome;
    }

    private void handleException(Exception e) {
        e.printStackTrace(System.err);
    }

    public String delete() {
        try {
            if (gnomeType != null) {
                inventoryFacade.deleteItem(gnomeType);
                gnomesLst = inventoryFacade.getGnomesList();
                refreshPage();
            }
        } catch (Exception e) {
            handleException(e);
        }
        return "deleted";
    }

    private void refreshPage() {
        if (gnomesLst.size() > 0) {
            gnomeType = gnomesLst.get(0).toString();
            retrieveGnomeDetails();
        } else {
            this.gnomeDesc = null;
            this.gnomeType = null;
            this.nbrOfUnits = 0;
            this.price = 0;
        }
    }
}
