/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.controller;

import converter.model.ConversionRate;
import converter.model.ConversionRateDTO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    public ConversionRateDTO addConversion(String srcCurrency, String dstCurrency, double rate) {
        ConversionRate exchangeRate = new ConversionRate(srcCurrency, dstCurrency, rate);
        em.persist(exchangeRate);
        return exchangeRate;
    }

    public List<String> getSourceCurrencyLst() {
        List<String> srcLst = new ArrayList<String>();
        objLst = em.createNamedQuery("findAllCurrency").getResultList();
        Iterator objLstIterator = objLst.iterator();
        while (objLstIterator.hasNext()) {
            ConversionRate cRate = (ConversionRate) objLstIterator.next();
            if(!(srcLst.contains(cRate.getPrimaryKey().getFromCurrency()))){
                srcLst.add(cRate.getPrimaryKey().getFromCurrency());
            }
        }
        return srcLst;
    }

    public List<String> getDestCurrencyLst() {
        List<String> dstLst = new ArrayList<String>();
        Iterator objLstIterator = objLst.iterator();
        while (objLstIterator.hasNext()) {
            ConversionRate cRate = (ConversionRate) objLstIterator.next();
            if(!(dstLst.contains(cRate.getPrimaryKey().getToCurrency()))){
                dstLst.add(cRate.getPrimaryKey().getToCurrency());
            }
        }
        return dstLst;
    }

    public double convert(String srcCurrency, String dstCurrency, double amount) {
        List<String> tmpLst = new ArrayList<String>();
        double value = 0;
        ConversionRate temp, cRate;
        temp = new ConversionRate(srcCurrency, dstCurrency, 0);
        Query queryObj = em.createNamedQuery("findRate");
        queryObj.setParameter(1, temp.getPrimaryKey());
        tmpLst = queryObj.getResultList();
        Iterator it = tmpLst.iterator();
        if (it.hasNext()) {
            cRate = (ConversionRate) it.next();
            if (cRate == null) {
                throw new EntityNotFoundException("No Conversion rate found from:" + srcCurrency + " to:" + dstCurrency);
            }
            value = (cRate.getRate()) * amount;
        }
        if(value == 0){
            throw new EntityNotFoundException("Conversion rate is not found for the above conversion");
        }
        return value;
    }
}
