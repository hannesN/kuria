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
package de.topicmapslab.kuria.annotation.widgets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The file annotation indicates that the field is a absolute path to a file.
 * 
 *  
 * @author Hannes Niederhausen
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface File {

	/** Label used for the widget */
	String label() default "";
	
	/** Indicates if widget is editable */
	boolean readOnly() default false;
	
	/** Indicates if the field is optional and the edit mask may persist without it */
	boolean optional() default false;
	
	/**
	 * The supported file extensions. These extensions must have the form:
	 * <b>"*.extension"</b>. For every entry in the array the file dialog provides 
	 * a selection in the combo box. 
	 * 
	 *  If you want to filter more than one extension at once use:
	 *  <b>"*.ext1;*.ext2"</b> as entry.
	 */
	String[] fileExtensions() default "*.*";
	
	/**
	 * Specifies if the file in the property is used to save or to load a file.
	 * 
	 * If a file is loaded the file must exists, else it can be created.
	 */
	boolean load() default false;
	
	/** the weight is used to create an order of the fields in the input mask. 
	 *  The field with the highest weight will be the first.
	 */
	int weight() default  1;
	
	/**
	 * An optional description used for tooltips 
	 */
	String description() default "";
}
