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
 * The date annotation indicates, that the property should be edited by an calender.
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Date {
	/** Label used for the widget */
	String label() default "";
	
	/** Indicates if widget is editable */
	boolean readOnly() default false;
	
	/** The String format, see SimpleDateFormat */
	String format() default "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	/** Indicates if the field is optional and the edit mask may persist without it */
	boolean optional() default false;
	
	/** the weight is used to create an order of the fields in the input mask. 
	 *  The field with the highest weight will be the first.
	 */
	int weight() default  1;
	
	/**
	 * flag to tell the UI to show the date with or without the time of the date 
	 */
	boolean showTime() default false;
	
	/**
	 * An optional description used for tooltips 
	 */
	String description() default "";
}
