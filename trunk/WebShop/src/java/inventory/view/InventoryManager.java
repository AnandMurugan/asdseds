/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.view;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;

/**
 *
 * @author Anand
 */
@Named(value = "inventoryManager")
@ConversationScoped
public class InventoryManager implements Serializable {

    /** Creates a new instance of InventoryManager */
    public InventoryManager() {
    }
}
