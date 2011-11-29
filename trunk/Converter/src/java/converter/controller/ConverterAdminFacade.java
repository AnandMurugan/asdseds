/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.controller;

import converter.model.ConversionRate;
import converter.model.ConversionRateDTO;
import converter.model.ConversionRatePK;
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
public class ConverterAdminFacade {

    @PersistenceContext(unitName = "ConverterPU")
    private EntityManager em;
    
    public void addConversion(String srcCurrency, String dstCurrency, double rate) {
        ConversionRateDTO conversionRate = em.find(ConversionRate.class, new ConversionRatePK(srcCurrency, dstCurrency));
        if(conversionRate != null) {
            if(conversionRate.getRate() == rate) {
                return;
            }
            conversionRate.setRate(rate);
            em.persist(conversionRate);
        } else {
            ConversionRate exchangeRate = new ConversionRate(srcCurrency, dstCurrency, rate);
            em.persist(exchangeRate);
        }
    }
}
