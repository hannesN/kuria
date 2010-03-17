/**
 * 
 */
package de.topicmapslab.kuria.runtime.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.topicmapslab.kuria.runtime.PropertyBinding;

/**
 * The {@link EditableBinding} is the binding which connects a class to its different {@link PropertyBinding}s.
 * <p>A generator takes this Binding to generate a input mask for this binding. It creates a control to edit every 
 * property with an bound by a {@link PropertyBinding}.</p>
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class EditableBinding {

	private Class<?> objectType;
	private List<PropertyBinding> propertieBindings;

	
	/**
	 * Returns the {@link PropertyBinding}s of this binding.
	 * <p>This list is unmodifiable. To add or remove a binding use the methods
	 * {@link #addPropertyBinding(PropertyBinding)} and {@link #removePropertyBinding(PropertyBinding)}.
	 * 
	 * @return an unmodifiable list of the {@link PropertyBinding}s
	 */
	public List<PropertyBinding> getPropertieBindings() {
		if (propertieBindings == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(propertieBindings);
	}

	/**
	 * Adds a new {@link PropertyBinding} to the list.
	 * <p>If the binding is already in the list, it is added again.</p>
	 * @param b the new {@link PropertyBinding}
	 */
	public void addPropertyBinding(PropertyBinding b) {
		if (propertieBindings == null) {
			propertieBindings = new ArrayList<PropertyBinding>();
		}
		propertieBindings.add(b);
	}
	
	/**
	 * Removes a {@link PropertyBinding} from the list.
	 * <p>If the given {@link PropertyBinding} is more in the list more than one time
	 * only the first element in the list will be removed.</p>
	 * @param b the {@link PropertyBinding} to remove
	 */
	public void removePropertyBinding(PropertyBinding b) {
		if (propertieBindings != null) {
			propertieBindings.remove(b);
		}
	}
	
	/**
	 * Returns the bound type.
	 * @return the bound type
	 */
	public Class<?> getObjectType() {
		return objectType;
	}
	
	
	/**
	 * Sets the bound type.
	 * <p>Make sure all {@link PropertyBinding}s in this binding bind fields in this type.
	 * @param objectType the bound type
	 */
	public void setObjectType(Class<?> objectType) {
		this.objectType = objectType;
	}

}
