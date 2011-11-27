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
import java.util.LinkedHashMap;
import java.util.Map;
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
    private Map<String, Object> srcCurrencyList;
    private static Map<String, Object> dstCurrencyList;
    private ConversionRateDTO conversionRateDTO;

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

    public Map<String, Object> getDstCurrencyList() {
        dstCurrencyList = new LinkedHashMap<String, Object>();
        dstCurrencyList.put("SEK", "sek");
        dstCurrencyList.put("EUR", "eur");
        dstCurrencyList.put("GBP", "gbp");
        return dstCurrencyList;
    }

    public Map<String, Object> getSrcCurrencyList() {
        srcCurrencyList = new LinkedHashMap<String, Object>();
        srcCurrencyList.put("SEK", "sek");
        srcCurrencyList.put("EUR", "eur");
        srcCurrencyList.put("GBP", "gbp");
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
        converterFacade.addConversion(srcCurrency, dstCurrency, rate);
    }

    public void convert() {
        converterFacade.convert();
    }
}
