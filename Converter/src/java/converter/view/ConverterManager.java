/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.view;

import converter.controller.ConverterFacade;
import converter.model.ConversionRateDTO;
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
@Named(value = "converterManager")
@ConversationScoped
public class ConverterManager implements Serializable {

    @EJB
    private ConverterFacade converterFacade;
    @Inject
    private Conversation conversation;
    private String srcCurrency;
    private String dstCurrency;
    private double rate;
    private double amount;
    private double value;
    private List<String> srcCurrencyList = new ArrayList<String>();
    private List<String> dstCurrencyList = new ArrayList<String>();
    private ConversionRateDTO conversionRateDTO;
    private Exception conversionFailure;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<String> getDstCurrencyList() {
        dstCurrencyList = converterFacade.getDestCurrencyLst();
        return dstCurrencyList;
    }

    public List<String> getSrcCurrencyList() {
        srcCurrencyList = converterFacade.getSourceCurrencyLst();
        return srcCurrencyList;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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

    /** Creates a new instance of ConverterManager */
    public ConverterManager() {
    }

    public void addConversion() {
        try {
            conversionFailure = null;
            startConversation();
            converterFacade.addConversion(srcCurrency, dstCurrency, rate);
        } catch (Exception e) {
            handleException(e);
        }

    }

    public void convert() {
        try {
            conversionFailure = null;
            startConversation();
            value = converterFacade.convert(srcCurrency, dstCurrency, amount);
        } catch (Exception e) {
            handleException(e);
        }
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
        conversionFailure = e;
    }

    public boolean getSuccess() {
        return conversionFailure == null;
    }
}
