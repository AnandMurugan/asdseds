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
import javax.faces.event.ValueChangeEvent;

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
//    private List<String> gnomesList = new ArrayList<String>();

    /** Creates a new instance of InventoryManager */
    public InventoryManager() {
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
//        if(gnomesList.size()==1){
//            gnomeType = gnomesList.get(0).toString();
//            refreshGnomeDetails();
//        }
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
//        if (initialize == false) {
//            inventoryFacade.init();
//            initialize = true;
//        }
//        gnomesList = inventoryFacade.getGnomesList();
        return inventoryFacade.getGnomesList();
    }

    public void select(ValueChangeEvent e) {
        gnomeType = e.getNewValue().toString();
        refreshGnomeDetails();
    }

    public void refreshGnomeDetails() {
        try {
            InventoryDTO inventoryDTO = inventoryFacade.select(gnomeType);
            this.gnomeDesc = inventoryDTO.getGnomeDesc();
            this.nbrOfUnits = inventoryDTO.getNbrOfUnits();
            this.price = inventoryDTO.getPrice();
            selected = true;
        } catch (Exception e1) {
            handleException(e1);
        }
    }

    public String updateItem() {
        inventoryFacade.updateItem(gnomeType, gnomeDesc, nbrOfUnits, price);
        return "modified";
    }

    public void newGnome() {
        newGnome = true;
        this.gnomeDesc = null;
        this.gnomeType = null;
        this.nbrOfUnits = 0;
        this.price = 0;
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
}
