/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author Anand
 */
@Entity
public class ConversionRate implements ConversionRateDTO, Serializable {
    private static final long serialVersionUID = 1L;
    ConversionRatePK primaryKey;

    public void setPrimaryKey(ConversionRatePK primaryKey) {
        this.primaryKey = primaryKey;
    }
    private double rate;

    public ConversionRate(String srcCurrency, String dstCurrency, double rate) {
        ConversionRatePK newConversion = new ConversionRatePK(srcCurrency, dstCurrency);
        this.primaryKey = newConversion;
        this.rate = rate;
    }

    @Override
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
    public ConversionRate(){
        
    }
    
    @EmbeddedId
    public ConversionRatePK getPrimaryKey(){
        return primaryKey;
    }

    @Override
    public String getSrcCurrency() {
        return primaryKey.getFromCurrency();
    }

    @Override
    public String getDstCurrency() {
        return primaryKey.getToCurrency();
    }
}