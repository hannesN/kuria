/**
 * 
 */
package de.topicmapslab.kuria.runtime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;

import de.topicmapslab.kuria.runtime.tree.ChildrenBinding;
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
 * {@link CheckBinding}, {@link ChildrenBinding} 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public abstract class PropertyBinding {

	private String label;
	private boolean readOnly = false;

	private String fieldName;
	private Type type;

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
	 * Returns if readOnly is true.
	 * 
	 * ReadOnly indicates, that a control is used to show the current value of
	 * an field, without providing the modification of the field.
	 * 
	 * @return <code>true</code> if readOnly is true, <code>false</code> else
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
	 * Returns the fieldname of this binding.
	 * 
	 * @return the current value of fieldname
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Returns the type of this field. This may be either a {@link Class} or an
	 * {@link ParameterizedType} based on the type of attribute in the bound
	 * class.
	 * 
	 * @return the type of the field
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
	 * Returns the value of the field in the given instance.
	 * 
	 * @param instance the instance which field value should be returned
	 * @return the value of the field in the given instance
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Object getValue(Object instance) throws IllegalAccessException, InvocationTargetException,
	        NoSuchMethodException {
		return PropertyUtils.getProperty(instance, fieldName);
	}

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
	 * Checks if the type of this field is an array.
	 * 
	 * <p>This is a helper method to get information of the type.</p>
	 * @return <code>true</code> if the type of this field is an array, <code>false</code> else
	 */
	public boolean isArray() {
		if (type instanceof Class<?>)
			return ((Class<?>) type).isArray();
		return false;
	}
	
	/**
	 * Checks if the type of this field is an implementation of {@link Collection}.
	 * 
	 * <p>This is a helper method to get information of the type.</p>
	 * @return <code>true</code> if the type of this field is an implementation of {@link Collection}, <code>false</code> else
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
	public Type getElementType() {
		try {
			return TypeUtil.getContainerType(type);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}
