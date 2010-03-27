package de.topicmapslab.kuria.runtime.widget;

import java.util.List;

import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.PropertyBinding;

public interface IEditableBinding {

	/**
	 * Returns the {@link IPropertyBinding}s of this binding.
	 * <p>This list is unmodifiable. </p>
	 * 
	 * @return an unmodifiable list of the {@link PropertyBinding}s
	 */
	public abstract List<IPropertyBinding> getPropertieBindings();

	/**
	 * Returns the bound type.
	 * @return the bound type
	 */
	public abstract Class<?> getObjectType();

}