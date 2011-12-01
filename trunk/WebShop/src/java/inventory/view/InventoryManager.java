/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.view;

import inventory.controller.InventoryFacade;
import inventory.model.InventoryDTO;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

/**
 *
 * @author Anand
 */
@Named(value = "inventoryManager")
@ConversationScoped
public class InventoryManager implements Serializable {

    @EJB
    private InventoryFacade inventoryFacade;
    @Inject
    private Conversation conversation;
    
    private List<String> gnomesList = new ArrayList<String>();
    private InventoryDTO inventoryDTO;
    private String gnomeType;
    private static boolean initialize = false;
    private String gnomeDesc;
    private int nbrOfUnits;
    private int selectedUnits;
    private double price;
    
    public String getGnomeDesc() {
        return gnomeDesc;
    }

    public int getNbrOfUnits() {
        return nbrOfUnits;
    }

    public double getPrice() {
        return price;
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
            inventoryFacade.init();
            initialize = true;
        }

        gnomesList = inventoryFacade.getGnomesList();
        return gnomesList;
    }

    /** Creates a new instance of InventoryManager */
    public InventoryManager() {
    }

    public void select() {
        try{
            startConversation();
            inventoryDTO = inventoryFacade.select(gnomeType);
        }catch(Exception e){
            handleException(e);
        }
        
        gnomeDesc = inventoryDTO.getGnomeDesc();
        nbrOfUnits = inventoryDTO.getNbrOfUnits();
        price = inventoryDTO.getPrice();
    }
    
    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
    }
}
