/*******************************************************************************
 * Copyright 2010, Topic Maps Lab
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/**
 * 
 */
package de.topicmapslab.kuria.annotation.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>A field annotated with {@link Column} indicates that the values of the
 * field are rendered in the table. The value is accessed using an accessor
 * and next to the value is the image. The title is used in the header of the
 * table. 
 * </p>
 * 
 * @author Hannes Niederhausen
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	
	/** The column title which is used in the table header */
	String title() default "";
	
	/** the path to an image for the cell */
	String image() default "";
	
	/** The column number. Use this to set an order for the columns. If not used the order of fields is used. */
	int col() default -1;
	
	/** the name of the method which provides a path to an image */
	String imageMethod() default "";
	
	/** the name of the method which generates the text for the column */
	String textMethod() default "";
}
