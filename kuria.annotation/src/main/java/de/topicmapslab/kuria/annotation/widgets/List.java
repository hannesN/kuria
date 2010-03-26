/**
 * 
 */
package de.topicmapslab.kuria.annotation.widgets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.topicmapslab.kuria.runtime.widget.ListStyle;

/**
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface List {
	/** Label used for the widget */
	String label() default "";
	
	/** Indicates if widget is editable */
	boolean readOnly() default false;
	
	/** the style how this editor is rendered. 
	 * @see  {@link ListStyle} */
	ListStyle style() default ListStyle.COMPACT;
	
	/** Indicates if a button next to the button is shown. This button opens a 
	 * dialog which let you create a new instance of te type of the annotated field */
	boolean createNew() default false;
	
	/** Indicates if the field is optional and the edit mask may persist without it */
	boolean optional() default false;
}
