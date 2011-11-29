/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.model;

/**
 *
 * @author Alex
 */
public interface ConversionRateDTO {
    public double convert(double amount);
    public String getSrcCurrency();
    public String getDstCurrency();
    public double getRate();
    public void setRate(double rate);
}
