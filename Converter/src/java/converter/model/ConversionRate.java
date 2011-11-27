/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Anand
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findAllCurrency",
                query = "SELECT OBJECT(cRate) FROM ConversionRate cRate")
//    @NamedQuery(name="findRate", 
//                query = "SELECT OBJECT(cRate) FROM ConversionRate cRate WHERE cRate.getPrimaryKey().getFromCurrency() = ?1 AND cRate.getPrimaryKey().getToCurrency() = ?2")
})
public class ConversionRate implements ConversionRateDTO, Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    ConversionRatePK primaryKey;
    private double rate;

    public void setPrimaryKey(ConversionRatePK primaryKey) {
        this.primaryKey = primaryKey;
    }

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

    public ConversionRate() {
    }

    public ConversionRatePK getPrimaryKey() {
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