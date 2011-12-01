/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.view;

import converter.controller.ConverterFacade;
import converter.model.ConversionException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Anand
 */
@Named(value = "converterManager")
@SessionScoped
public class ConverterManager implements Serializable {

    @EJB
    private ConverterFacade converterFacade;
    
    private String srcCurrency;
    private String dstCurrency;
    private double amount;
    private double value;
    
    private Exception conversionFailure;

    /*
     * getters/setters for the attributes
     */
    
    public void setValue(double value) {
        this.value = value;
    }
    public double getValue() {
        return value;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
    
    /*
     * succes and exception handling
     */
    

    private void handleException(Exception e) {
        e.printStackTrace(System.err);
        conversionFailure = e;
        value = 0;
    }

    public boolean getSuccess() {
        return conversionFailure == null;
        
    }

    public Exception getException() {
        return conversionFailure;
    }
    
    /*
     * methods from ConverterManager
     */
    
    public List<String> getCurrencyList() {
        return converterFacade.getCurrencyList();
    }
    
    public void convert() {
        try {
            System.out.println(srcCurrency + " " + dstCurrency + " " + amount);
            conversionFailure = null;
            value = converterFacade.convert(srcCurrency, dstCurrency, amount);
        } catch (ConversionException e) {
            handleException(e);
        }
    }
    
    public void validateAmount(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Double v = (Double) value;
        if (v < 0) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("number should be positive");
            message.setDetail("number should be positive");
            throw new ValidatorException(message);
        }
    }
}
