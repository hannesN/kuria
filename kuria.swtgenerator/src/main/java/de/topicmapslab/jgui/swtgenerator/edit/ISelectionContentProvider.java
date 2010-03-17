package de.topicmapslab.jgui.swtgenerator.edit;

/**
 * This is the content provider for any selection dialog used to set a field.
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public interface ISelectionContentProvider {

	
	/**
	 * Returns the elements for an selection dialog
	 * @param fieldName the fieldname of the model , which should be set
	 * @param model the current model which is edited
	 * @return a list of possible values for the models field
	 */
	public Object[] getElements(String fieldName, Object model);	
}
