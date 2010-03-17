/**
 * 
 */
package de.topicmapslab.kuria.annotation.widgets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotation which indicates hat the property is set via a combo box.
 * The Combobox must be filled with an array of objects of the propertys type.</p>
 * 
 * <p> The text of the entry in the combo box is retrieved via the property annotated with
 * {@link Text}.</p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Combo {
	/** Label used for the widget */
	String label() default "";
	
	/** Indicates if widget is editable */
	boolean readOnly() default false;
	
	/** Inidcates if a button next to the button is shown. This button opens a 
	 * dialog which let you create a new instance of te type of the annotated field */
	boolean createNew() default false;
}
