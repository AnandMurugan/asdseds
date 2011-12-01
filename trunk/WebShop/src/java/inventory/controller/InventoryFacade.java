/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.controller;

import inventory.model.Inventory;
import inventory.model.InventoryDTO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Anand
 */
@Stateless
@LocalBean
public class InventoryFacade {

    @PersistenceContext(unitName = "WebShopPU")
    private EntityManager em;
    
    public List<String> getGnomesList() {
        List<String> gnomeLst = new ArrayList<String>();
        List<Object> objLst = new ArrayList<Object>();
        objLst = em.createNamedQuery("findAllGnomeType").getResultList();
        Iterator objLstIterator = objLst.iterator();
        while (objLstIterator.hasNext()) {
            InventoryDTO inv = (InventoryDTO) objLstIterator.next();
            String gnome = inv.getGnomeType();
            if (!(gnomeLst.contains(gnome))) {
                gnomeLst.add(gnome);
            }
        }
        return gnomeLst;
    }
    
    public void init(){
        System.out.println("Initializing inventory...");
        Inventory inventory;
        inventory = new Inventory();
        inventory.setGnomeType("Ice Gnomes");
        inventory.setGnomeDesc("region-Frostfell");
        inventory.setNbrOfUnits(15);
        inventory.setPrice(100);
        em.persist(inventory);
        inventory = new Inventory();
        inventory.setGnomeType("Fire Gnomes");
        inventory.setGnomeDesc("region-Bytopia");
        inventory.setNbrOfUnits(10);
        inventory.setPrice(50);
        em.persist(inventory);
        inventory = new Inventory();
        inventory.setGnomeType("Tinker Gnomes");
        inventory.setGnomeDesc("region-Dragonlace");
        inventory.setNbrOfUnits(20);
        inventory.setPrice(70);
        em.persist(inventory);
    }

    public InventoryDTO select(String gnomeType) {
        InventoryDTO inventoryDTO = em.find(Inventory.class, gnomeType);
        if(inventoryDTO==null){
            throw new EntityNotFoundException("No entity found of type -"+gnomeType);
        }
        return inventoryDTO;
    }
    
}
