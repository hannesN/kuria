/**
 * 
 */
package de.topicmapslab.kuria.annotation.widgets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify the UI element for the
 * annotated element is a check box.
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Check  {
	/** Label used for the widget */
	String label() default "";
	
	/** Indicates if widget is editable */
	boolean readOnly() default false;
}
