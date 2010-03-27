/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit.widgets;

import de.topicmapslab.kuria.runtime.IPropertyBinding;

/**
 * A state listener is notified when a widget is modified. 
 * 
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public interface IStateListener {

	/**
	 * This method is invoked when the widgets values differs from the models value.
	 * The widget is in a dirty state, and needs to persist.
	 * 
	 * <p> If the persist method of the widget is called. The dirty state is reset to false
	 * and this methos is invoked.</p>
	 * 
	 * @param property the binding of the property
	 * @param isDirty the dirty state
	 */
	public void stateChanged(IPropertyBinding property, boolean isDirty);
	
	/**
	 * 
	 * @param newModel
	 */
	public void newModelCreated(Object newModel);
}
