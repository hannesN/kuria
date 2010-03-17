/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit;

/**
 * Content provider for generated Comboboxes. Every {@link ComboBinding} has a field name
 * where the value is stored. This field name is used as key
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public interface IContentProvider {

	
	/**
	 * Returns the possible values for the field name.
	 * 
	 * @param fieldname the filename from the {@link ComboBinding}
	 * @param model the current model
	 * @return an array of objects. may be empty but must not be <code>null</code>.
	 */
	public Object[] getElements(String fieldname, Object model);
	
	/**
	 * Checks if the provider provides content for the given field and instance. 
	 * 
	 * <p>
	 * If the result is <code>true</code> 
	 * </p>
	 * 
	 * @param fieldname the fieldname to check
	 * @param model the model instance to edit
	 * @return <code>true</code> if {@link #getElements(String, Object)} returns a content for the field, <code>false</code> else
	 */
	public boolean hasContent(String fieldname, Object model);
}
