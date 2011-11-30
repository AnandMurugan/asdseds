/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

/**
 *
 * @author Anand
 */
@Entity
@NamedQuery(name = "findAllCurrency",
    query = "SELECT OBJECT(cRate) FROM ConversionRate cRate")
public class ConversionRate implements ConversionRateDTO, Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    ConversionRatePK primaryKey;
    private double rate;

    public ConversionRate() {
    }

    public ConversionRate(String srcCurrency, String dstCurrency, double rate) {
        ConversionRatePK newConversion = new ConversionRatePK(srcCurrency, dstCurrency);
        this.primaryKey = newConversion;
        this.rate = rate;
    }

//    public ConversionRatePK getPrimaryKey() {
//        return primaryKey;
//    }
    public void setPrimaryKey(ConversionRatePK primaryKey) {
        this.primaryKey = primaryKey;
    }

//    @Override
//    public double getRate() {
//        return rate;
//    }
//
//    public void setRate(double rate) {
//        this.rate = rate;
//    }
//
//    @Override
//    public String getSrcCurrency() {
//        return primaryKey.getFromCurrency();
//    }
//
//    @Override
//    public String getDstCurrency() {
//        return primaryKey.getToCurrency();
//    }
    @Override
    public double convert(double amount) {
        return rate * amount;
    }

    @Override
    public String getDstCurrency() {
        return primaryKey.getToCurrency();
    }

    @Override
    public String getSrcCurrency() {
        return primaryKey.getFromCurrency();
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }
}