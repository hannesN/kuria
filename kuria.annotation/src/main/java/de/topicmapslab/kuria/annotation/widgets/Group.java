/**
 * 
 */
package de.topicmapslab.kuria.annotation.widgets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A group is used to group the values of an complex attribute in the edit mask.
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Group {
	/** Label used for the widget */
	String label() default "";
	
	/** Indicates if widget is editable */
	boolean readOnly() default false;
	
	/** Indicates if the field is optional and the edit mask may persist without it */
	boolean optional() default false;
}
