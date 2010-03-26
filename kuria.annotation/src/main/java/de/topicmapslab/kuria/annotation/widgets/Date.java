/**
 * 
 */
package de.topicmapslab.kuria.annotation.widgets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The date annottion indicates, that the property should be editited by an calender.
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Date {
	/** Label used for the widget */
	String label() default "";
	
	/** Indicates if widget is editable */
	boolean readOnly() default false;
	
	/** The String format, see SimpleDateFormat */
	String format() default "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	/** Indicates if the field is optional and the edit mask may persist without it */
	boolean optional() default false;
}
