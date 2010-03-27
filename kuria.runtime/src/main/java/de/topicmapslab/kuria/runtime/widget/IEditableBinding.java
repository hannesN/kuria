package de.topicmapslab.kuria.runtime.widget;

import java.util.List;

import de.topicmapslab.kuria.runtime.PropertyBinding;

public interface IEditableBinding {

	/**
	 * Returns the {@link PropertyBinding}s of this binding.
	 * <p>This list is unmodifiable. To add or remove a binding use the methods
	 * {@link #addPropertyBinding(PropertyBinding)} and {@link #removePropertyBinding(PropertyBinding)}.
	 * 
	 * @return an unmodifiable list of the {@link PropertyBinding}s
	 */
	public abstract List<PropertyBinding> getPropertieBindings();

	/**
	 * Returns the bound type.
	 * @return the bound type
	 */
	public abstract Class<?> getObjectType();

}