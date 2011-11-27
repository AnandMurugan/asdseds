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
    
    String getSrcCurrency();
    String getDstCurrency();
    double getRate();
    
}
