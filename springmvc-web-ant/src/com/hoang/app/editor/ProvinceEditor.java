package com.hoang.app.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hoang.app.boundary.ProvinceFC;

/**
 * 
 * @author Hoang Truong
 *
 */

public class ProvinceEditor extends PropertyEditorSupport {
    private Log logger = LogFactory.getLog( this.getClass() );
    private ProvinceFC provinceFC;
    
    public ProvinceEditor(ProvinceFC provinceFC) {
        this.provinceFC = provinceFC;
    }

    /**
     * Sets the value of this property from the provided text string. If the
     * string is not convertable into a value, the value will remain as null.
     *
     * @param value The text string to be set as this property's value.
     */
    @Override
    public void setAsText(String value) {
        try {
            int id = Integer.valueOf(value);
            setValue( provinceFC.read(new Long(id) ));
        }
        catch(NumberFormatException e) {
            logger.error("ProvinceEditor setAsText", e);
            setValue( null );
        }
    }
}
