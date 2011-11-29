/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.controller;

import converter.model.ConversionException;
import converter.model.ConversionRate;
import converter.model.ConversionRateDTO;
import converter.model.ConversionRatePK;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class ConverterFacade {

    @PersistenceContext(unitName = "ConverterPU")
    private EntityManager em;
    List<Object> objLst;

    public List<String> getConversionList() {
        List<String> srcLst = new ArrayList<String>();
        objLst = em.createNamedQuery("findAllCurrency").getResultList();
        Iterator objLstIterator = objLst.iterator();
        while (objLstIterator.hasNext()) {
            ConversionRateDTO cRate = (ConversionRateDTO) objLstIterator.next();
            String srcCurrency = cRate.getSrcCurrency();
            if(!(srcLst.contains(srcCurrency ))){
                srcLst.add(srcCurrency);
            }
        }
        return srcLst;
    }

    public double convert(String srcCurrency, String dstCurrency, double amount) throws ConversionException{
        ConversionRateDTO conversionRate = em.find(ConversionRate.class, new ConversionRatePK(srcCurrency, dstCurrency));
        if (conversionRate == null) {
            throw new ConversionException("conversion rate does not exist");
        }
        return conversionRate.convert(amount);
    }
}
