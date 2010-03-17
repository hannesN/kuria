/**
 * 
 */
package de.topicmapslab.kuria.annotation.widgets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Indicates that a text field should be used to create a
 * an input mask.</p>    
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TextField {
	/** Label used for the widget */
	String label() default "";
	
	/** Indicates if widget is editable */
	boolean readOnly() default false;
	
	/** Is the widget a password field*/
	boolean password() default false;
	
	/** The regular expression for a validator */
	String regexp() default "\\.*";
	
	/** the number of rows for the text field. More than one would indicate the need for a multiline textfield*/ 
	int rows() default 1;
	
	/** inidicates that the text field should be higher than the given rows count if there is enough place below */
	boolean grabVerticalSpace() default false;
	
}
