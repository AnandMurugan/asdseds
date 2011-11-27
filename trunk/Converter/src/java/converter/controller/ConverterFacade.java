/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.controller;

import converter.model.ConversionRate;
import converter.model.ConversionRateDTO;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alex
 */
@Stateless
@LocalBean
public class ConverterFacade{
    @PersistenceContext(unitName = "ConverterPU")
    private EntityManager em;

    public ConversionRateDTO addConversion(String srcCurrency, String dstCurrency, double rate) {
        ConversionRate exchangeRate = new ConversionRate(srcCurrency, dstCurrency, rate);
        em.persist(exchangeRate);
        return exchangeRate;
    }

    public void convert(){
        
    }
    
}
