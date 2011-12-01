/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.view;

import inventory.controller.InventoryFacade;
import inventory.model.InventoryDTO;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Anand
 */
@Named(value = "inventoryManager")
@SessionScoped
public class InventoryManager implements Serializable {

    @EJB
    private InventoryFacade inventoryFacade;
    
    private InventoryDTO inventoryDTO;
    private String gnomeType;
    private static boolean initialize = false;
    private int selectedUnits;
    
    public String getGnomeDesc() {
        if(inventoryDTO==null){
            return null;
        }
        return inventoryDTO.getGnomeDesc();
    }

    public int getNbrOfUnits() {
        if(inventoryDTO==null){
            return 0;
        }
        return inventoryDTO.getNbrOfUnits();
    }

    public double getPrice() {
        if(inventoryDTO==null){
            return 0;
        }
        return inventoryDTO.getPrice();
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
        if (initialize == false) {
         //   inventoryFacade.init();
            initialize = true;
        }

        return inventoryFacade.getGnomesList();
    }

    /** Creates a new instance of InventoryManager */
    public InventoryManager() {
    }

    public void select() {
        try{
            inventoryDTO = inventoryFacade.select(gnomeType);
        }catch(Exception e){
            handleException(e);
        }
    }
    
    private void handleException(Exception e) {
        e.printStackTrace(System.err);
    }
}
