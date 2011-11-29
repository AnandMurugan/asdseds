/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.view;

import converter.controller.ConverterAdminFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author Alex
 */
@Named(value = "converterAdminManager")
@ConversationScoped
public class ConverterAdminManager implements Serializable{

    @EJB
    private ConverterAdminFacade converterAdminFacade;
    @Inject
    private Conversation conversation;
    private String srcCurrency;
    private String dstCurrency;
    private double rate;
    
    public void setRate(double rate) {
        this.rate = rate;
    }
    
    public double getRate() {
        return rate;
    }
    
    public String getDstCurrency() {
        return dstCurrency;
    }

    public void setDstCurrency(String dstCurrency) {
        this.dstCurrency = dstCurrency;
    }

    public String getSrcCurrency() {
        return srcCurrency;
    }

    public void setSrcCurrency(String srcCurrency) {
        this.srcCurrency = srcCurrency;
    }
    
     public void addConversion() {
        converterAdminFacade.addConversion(srcCurrency, dstCurrency, rate);
        converterAdminFacade.addConversion(dstCurrency, srcCurrency, 1/rate);
        converterAdminFacade.addConversion(srcCurrency, srcCurrency, 1);
        converterAdminFacade.addConversion(dstCurrency, dstCurrency, 1);
    }
}
