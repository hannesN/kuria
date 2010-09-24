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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;

import de.topicmapslab.kuria.runtime.tree.IChildrenBinding;
import de.topicmapslab.kuria.runtime.util.TypeUtil;
import de.topicmapslab.kuria.runtime.widget.CheckBinding;
import de.topicmapslab.kuria.runtime.widget.ComboBinding;
import de.topicmapslab.kuria.runtime.widget.DateBinding;
import de.topicmapslab.kuria.runtime.widget.GroupBinding;
import de.topicmapslab.kuria.runtime.widget.ListBinding;
import de.topicmapslab.kuria.runtime.widget.TextFieldBinding;

/**
 * Binding for every widget binding containing the label and the read only
 * parameters.
 * 
 * <p>
 * To use this class and its subclasses you should do the following: If you set
 * this value you should use something like the following code snippet: 
 * </p>
 * <pre>
 * for (Field f : c.getDeclaredFields()) {
 *   PropertyBinding pb = new ProeprtyBinding() {}; // because its an abstract class
 *   pb.setType(f.getGenericType());
 *   pb.setName(f.getName();
 * }
 * </pre>
 * 
 * <p>You should use the existing PropertyBindings, because their the only one the Generators will understand. If you want
 * to create another generator feel free to create subclasses of this class.
 * TODO explain more about generics and stuff
 * 
 * @see {@link TextFieldBinding}, {@link CheckBinding}, {@link ComboBinding}, {@link DateBinding}, {@link ListBinding}, {@link GroupBinding},
 * {@link CheckBinding}, {@link IChildrenBinding} 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public abstract class PropertyBinding implements IPropertyBinding, Comparable<PropertyBinding> {

	private String label;
	private boolean readOnly = false;
	private boolean optional = false;
	private int weight = 1; 

	private String fieldName;
	private Type type;

	/**
	 *  {@inheritDoc}
	 */
	public String getLabel() {
		if ((label == null) && (fieldName!=null))
			label = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		return label;
	}

	/**
	 * Sets the label of this binding.
	 * 
	 * @param label
	 *            the new label value
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 *  {@inheritDoc}
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * Sets the value of the readOnly field.
	 * 
	 * @param readOnly
	 *            the new value of readOnly
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 *  {@inheritDoc}
	 */
	public boolean isOptional() {
	    return optional;
    }
	
	/**
	 * Sets the flag which indicates if the property is optional
	 * @param optional the new flag
	 */
	public void setOptional(boolean optional) {
	    this.optional = optional;
    }
	
	/**
	 * Returns the name of the field of this binding.
	 * 
	 * The fieldname must be equal to an attribute of the bound class. It is
	 * used to set and get the values of this field in an specific instance of
	 * the class containing this field.
	 * 
	 * The fieldname is also used as label, if no specific label is set.
	 * 
	 * @param fieldName
	 *            the new value of the fieldname
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 
	 *  {@inheritDoc}
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 *  {@inheritDoc}
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the type of the field.
	 * 
	 * @param type the new value of type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 *  {@inheritDoc}
	 */
	public Object getValue(Object instance) throws IllegalAccessException, InvocationTargetException,
	        NoSuchMethodException {
		return PropertyUtils.getProperty(instance, fieldName);
	}

	/**
	 * 
	 *  {@inheritDoc}
	 */
	public void setValue(Object instance, Object value) throws IllegalAccessException, InvocationTargetException,
	        NoSuchMethodException, IllegalArgumentException {
		PropertyUtils.setProperty(instance, fieldName, value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyBinding [fieldName=").append(fieldName).append(", label=").append(label).append(
		        ", readOnly=").append(readOnly).append(", type=").append(type).append("]");
		return builder.toString();
	}

	/**
	 *  {@inheritDoc}
	 */
	public boolean isArray() {
		if (type instanceof Class<?>)
			return ((Class<?>) type).isArray();
		return false;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public boolean isCollection() {
		if (type instanceof Class<?>)
			return TypeUtil.isType(type, Collection.class);
		if (type instanceof ParameterizedType) {
			Type rawType = ((ParameterizedType) type).getRawType();
			return TypeUtil.isType(rawType, Collection.class);
		}
		return false;
	}

	/**
	 *  {@inheritDoc}
	 */
	public Type getElementType() {
		try {
			return TypeUtil.getContainerType(type);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public int getWeight() {
	    return weight;
    }
	
	public void setWeight(int weight) {
	    this.weight = weight;
    }
	
	/**
	 * Compares to bindings using the weight.
	 * 
	 * {@inheritDoc}
	 * Note: this class has a natural ordering that is inconsistent with equals.
	 * 
	 */
	public int compareTo(PropertyBinding o) {
		if (getWeight()==o.getWeight())
			return 0;
		
		if (getWeight()<o.getWeight())
			return -1;
		
		return 1;
	}
}
