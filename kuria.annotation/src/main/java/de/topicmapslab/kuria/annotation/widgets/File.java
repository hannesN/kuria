/**
 * 
 */
package de.topicmapslab.kuria.annotation.widgets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The file annotation indicates that the field is a absolute path to a file.
 * 
 *  
 * @author Hannes Niederhausen
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface File {

	/** Label used for the widget */
	String label() default "";
	
	/** Indicates if widget is editable */
	boolean readOnly() default false;
	
	/** Indicates if the field is optional and the edit mask may persist without it */
	boolean optional() default false;
	
	/**
	 * The supported file extensions. These extensions must have the form:
	 * <b>"*.extension"</b>. For every entry in the array the file dialog provides 
	 * a selection in the combo box. 
	 * 
	 *  If you want to filter more than one extension at once use:
	 *  <b>"*.ext1;*.ext2"</b> as entry.
	 */
	String[] fileExtensions() default "*.*";
}
