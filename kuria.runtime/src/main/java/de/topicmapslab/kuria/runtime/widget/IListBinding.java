package de.topicmapslab.kuria.runtime.widget;

import java.lang.reflect.InvocationTargetException;

import de.topicmapslab.kuria.runtime.IPropertyBinding;

public interface IListBinding extends IPropertyBinding {

	/**
	 * Returns the list style of this binding.
	 * @return the list style of the {@link ListBinding}
	 */
	public abstract ListStyle getListStyle();

	/**
	 * {@inheritDoc}
	 * 
	 * <p>If the value is an array, and the type is an collection this method converts the values according to the
	 * result of {@link #getCollectionImplementation()}.
	 */
	public abstract void setValue(Object instance, Object value)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, IllegalArgumentException;

	/**
	 * Returns the kind of implementation used for the collection
	 * or <code>null</code> if an array is used.
	 * @return the class of the 
	 */
	public abstract Class<?> getCollectionImplementation();

	/**
	 * Returns the value of the create-new flag.
	 * @return the value of the create-new flag.
	 */
	public abstract boolean isCreateNew();

}