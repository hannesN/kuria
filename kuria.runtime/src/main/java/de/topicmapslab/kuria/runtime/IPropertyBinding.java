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
package de.topicmapslab.kuria.runtime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public interface IPropertyBinding {

	/**
	 * Returns the value of the label attribute.
	 * 
	 * Labels are used for labeling all kind of input control, like textfields,
	 * checkboxes and so on. Only with the labels the user knows what field she
	 * actually edits.
	 * 
	 * If no label is set, a label is creates based on the field name.
	 * 
	 * @return the label or the field name with the first letter upcased
	 */
	public abstract String getLabel();

	/**
	 * Returns if readOnly is true.
	 * 
	 * ReadOnly indicates, that a control is used to show the current value of
	 * an field, without providing the modification of the field.
	 * 
	 * @return <code>true</code> if readOnly is true, <code>false</code> else
	 */
	public abstract boolean isReadOnly();

	/**
	 * Returns if this propterty is optional
	 * @return <code>true</code> if the property is optional
	 */
	public abstract boolean isOptional();

	/**
	 * Returns the type of this field. This may be either a {@link Class} or an
	 * {@link ParameterizedType} based on the type of attribute in the bound
	 * class.
	 * 
	 * @return the type of the field
	 */
	public abstract Type getType();

	/**
	 * Returns the value of the field in the given instance.
	 * 
	 * @param instance the instance which field value should be returned
	 * @return the value of the field in the given instance
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public abstract Object getValue(Object instance)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;

	/**
	 * Returns the fieldname of this binding.
	 * 
	 * @return the current value of fieldname
	 */
	public String getFieldName();
	
	/**
	 * Sets the value of the field in the given instance.
	 * 
	 * @param instance the instance which field value shoulb be changed
	 * @param value the new value
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 */
	public void setValue(Object instance, Object value) throws IllegalAccessException, InvocationTargetException,
	        NoSuchMethodException, IllegalArgumentException;
	
	/**
	 * Checks if the type of this field is an array.
	 * 
	 * <p>This is a helper method to get information of the type.</p>
	 * @return <code>true</code> if the type of this field is an array, <code>false</code> else
	 */
	public abstract boolean isArray();

	/**
	 * Checks if the type of this field is an implementation of {@link Collection}.
	 * 
	 * <p>This is a helper method to get information of the type.</p>
	 * @return <code>true</code> if the type of this field is an implementation of {@link Collection}, <code>false</code> else
	 */
	public abstract boolean isCollection();

	/**
	 * If the type is a {@link ParameterizedType} this method returns the type argument.
	 * <p>For instance: The type of the field is <b>List&lt;String&gt;</b> then this 
	 * method returns the class representing <b>String</b>.</p>
	 * 
	 * <p>If this type is an array, the component type will be returned. For instance
	 * the field is of type <b>String[]</b>, the class representing <b>String</b> will be returned
	 * @return <code>null</code> if the  type is neither an generic collection nor an array. 
	 * <p>Else it returns the {@link Type} of the generic parameter or the component type of the array.</p>
	 * <p>If an collection without parameter is used <b>Object.class</b> will be returned.</p>
	 */
	public abstract Type getElementType();

}
