/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter.view;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;

/**
 *
 * @author Anand
 */
@Named(value = "converterManager")
@ConversationScoped
public class ConverterManager implements Serializable {

    /** Creates a new instance of ConverterManager */
    public ConverterManager() {
    }
}
