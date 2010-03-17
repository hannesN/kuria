/**
 * 
 */
package de.topicmapslab.kuria.annotation.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Indicated that instances of this type represent a row in a
 * table. Every element annotated with {@link Column} will be 
 * represented by a column.  
 * </p> 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableElement {

}
