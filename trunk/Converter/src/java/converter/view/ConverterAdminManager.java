/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.view;

import converter.controller.ConverterAdminFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Alex
 */
@Named(value = "converterAdminManager")
@SessionScoped
public class ConverterAdminManager implements Serializable{

    @EJB
    private ConverterAdminFacade converterAdminFacade;
    
    private String srcCurrency;
    private String dstCurrency;
    private double rate;
    
    /*
     * setters/getters for attributs
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setDstCurrency(String dstCurrency) {
        this.dstCurrency = dstCurrency;
    }

    public void setSrcCurrency(String srcCurrency) {
        this.srcCurrency = srcCurrency;
    }
    
    public double getRate() {
        return rate;
    }

    public String getDstCurrency() {
        return dstCurrency;
    }

    public String getSrcCurrency() {
        return srcCurrency;
    }
    
    /*
     * methods of class
     */
     public void addConversion() {
        converterAdminFacade.addConversion(srcCurrency, dstCurrency, rate);
        converterAdminFacade.addConversion(dstCurrency, srcCurrency, 1/rate);
        converterAdminFacade.addConversion(srcCurrency, srcCurrency, 1);
        converterAdminFacade.addConversion(dstCurrency, dstCurrency, 1);
    }
}
