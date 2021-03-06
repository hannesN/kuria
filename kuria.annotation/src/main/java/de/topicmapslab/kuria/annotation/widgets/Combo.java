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
package de.topicmapslab.kuria.annotation.widgets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotation which indicates hat the property is set via a combo box.
 * The combo box must be filled with an array of objects of the property's type.</p>
 * 
 * <p> The text of the entry in the combo box is retrieved via the property annotated with
 * {@link Text}.</p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Combo {
	/** Label used for the widget */
	String label() default "";
	
	/** Indicates if widget is editable */
	boolean readOnly() default false;
	
	/** Indicates if a button next to the button is shown. This button opens a 
	 * dialog which let you create a new instance of the type of the annotated field */
	boolean createNew() default false;
	
	/** Indicates if the field is optional and the edit mask may persist without it */
	boolean optional() default false;
	
	/** the weight is used to create an order of the fields in the input mask. 
	 *  The field with the highest weight will be the first.
	 */
	int weight() default  1;
	
	/**
	 * An optional description used for tooltips 
	 */
	String description() default "";
}
