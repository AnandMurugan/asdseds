/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Anand
 */
@Embeddable
public class ConversionRatePK implements Serializable{
    private String fromCurrency;
    private String toCurrency;
    
    
    public ConversionRatePK(){
    }

    public ConversionRatePK(String fromCurrency, String toCurrency) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }
    
    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }
    
    @Override
    public int hashCode(){
      return (int)  fromCurrency.hashCode() + toCurrency.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConversionRatePK other = (ConversionRatePK) obj;
        if ((this.fromCurrency == null) ? (other.fromCurrency != null) : !this.fromCurrency.equals(other.fromCurrency)) {
            return false;
        }
        if ((this.toCurrency == null) ? (other.toCurrency != null) : !this.toCurrency.equals(other.toCurrency)) {
            return false;
        }
        return true;
    }
    
}
