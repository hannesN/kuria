/**
 * 
 */
package de.topicmapslab.kuria.annotation.widgets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>Indicates that a model the factory should generate an input mask for this model.</p>
 * <p>All properties, which are not annotated with {@link Hidden}, will be shown in a control based on
 * their annotation or datatype.</p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Editable {

}
