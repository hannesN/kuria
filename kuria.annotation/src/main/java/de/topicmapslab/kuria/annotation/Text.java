/**
 * 
 */
package de.topicmapslab.kuria.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p> If you use a class in a tree, list or combobox an textual representaion for the
 * user is needed. 
 * his annotation indicates that the annotated field or method
 * represents this text.
 * </p>
 * 
 *  <p> If used on a field, the attributes value is the text and
 *  will retireved by an accessor method. The datatype needs to be
 *  {@link String}.</p>
 *  
 *  <p> If used on a method, the method needs to return a {@link String} which
 *  is used for the node text.</p> 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Text {

}
