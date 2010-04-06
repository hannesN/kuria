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
package de.topicmapslab.kuria.runtime;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Some UI Bindings need a textual representation of a given class, for instance
 * trees, tables and comboboxes.
 * <p>
 * With the {@link TextBinding} it is possible to specify which field should be
 * used to retrieve the text.
 * </p>
 * <p>
 * The fieldname may be <code>null</code>, then toString is used.
 * </p>
 * <p>
 * If you got a class which textual representation should be composed of
 * multiple field, you can write an accessor method for a "virtual" attribute.
 * For instance:
 * 
 * <pre>
 * public class Person {
 *   String firstName;
 *   String lastName;
 * 
 *   getFullName() {
 *     return lastName+", "+firstName;
 *   }
 * }
 * 
 * To use this method, just set the field name to the name of the "virtual" attribute. According to the
 * JavaBean standard a accessor method like <i>getFullName()</i> should access a field named <i>fullName</i> 
 * and thats how the field should be set in the {@link TextBinding}
 * for the example class.
 * 
 * </p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class TextBinding implements ITextBinding {

	private String fieldName;

	/**
	 * The fieldname which getter method should be used.
	 * 
	 * @param fieldName
	 *            the fieldname which may be a virtual fieldname to create the
	 *            name of the getterMethod
	 * 
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 *  {@inheritDoc}
	 */
	public String getText(Object instance) throws IllegalAccessException, NoSuchMethodException,
	        InvocationTargetException {
		if (fieldName == null)
			return instance.toString();
		Object object = PropertyUtils.getProperty(instance, fieldName);
		if (object==null)
			return "<null>";
		
		if (object instanceof String)
			return (String) object;
		

		return object.toString();
	}
}
