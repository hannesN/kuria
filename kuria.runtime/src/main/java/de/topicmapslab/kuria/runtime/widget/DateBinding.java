/**
 * 
 */
package de.topicmapslab.kuria.runtime.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.topicmapslab.kuria.runtime.PropertyBinding;

/**
 * The DateBinding binds an a field with the type {@link Date} to the UI.
 * <p>
 * The date has one additional attribute: format. This format specifies the format of
 * the textual representation of the date. Refer to {@link SimpleDateFormat} for more information.
 * </p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class DateBinding extends PropertyBinding implements IDateBinding {

	private String format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	/**
	 *  {@inheritDoc}
	 */
	public String getFormat() {
	    return format;
    }
	
	/**
	 * Sets the textual representation of the format.
	 * @param format the format of the textual representation
	 */
	public void setFormat(String format) {
	    this.format = format;
    }
}
