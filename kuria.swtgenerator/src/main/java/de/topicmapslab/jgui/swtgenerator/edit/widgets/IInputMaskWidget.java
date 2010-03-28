/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit.widgets;

import org.eclipse.swt.widgets.Composite;

import de.topicmapslab.jgui.swtgenerator.edit.IContentProvider;
import de.topicmapslab.kuria.runtime.IPropertyBinding;

/**
 * The interface for all widget types.
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public interface IInputMaskWidget {

	/**
	 * Creates the widgets for the binding.
	 * 
	 * @param parent the parent for the controls. It needs to have a gridlayout
	 */
	public void createControl(Composite parent);
	
	
	/**
	 * updates the widgets according to the model data
	 */
	public void refresh();
	
	/**
	 * Sets the model of the widget and updates their values
	 * @param model the new model for the widget or <code>null</code>
	 */
	public void setModel(Object model);
	
	/**
	 * 
	 * @return the {@link IPropertyBinding} which is represented by this Widget
	 */
	public IPropertyBinding getPropertyBinding();
	
	
	/**
	 * Sets the enable state of the widgets
	 * 
	 * @param enabled she widget state
	 */
	public void setEnabled(boolean enabled);
	
	
	/**
	 * Stores the value of the widget in the according property of the widget
	 */
	public void persist();
	
	/**
	 * Sets the {@link IContentProvider} of the model.
	 * 
	 * @param provider the content provider for the model
	 */
	public void setContentProvider(IContentProvider provider);

	/**
	 * Adds a state listener
	 * 
	 * @param listener the listener to add
	 */
	public void addStateListener(IStateListener listener);
	
	/**
	 * Removes the given listener
	 *  
	 * @param listener the listener to remove
	 */
	public void removeStateListener(IStateListener listener);

	/**
	 * Flag which indicates the state of the widget. 
	 * 
	 * @return the dirty state of the widget
	 */
	public boolean isDirty();
	
	/**
	 * Returns the value of the optional flag.
	 * 
	 * If a widget is optional its value may be empty or null.
	 * 
	 * @return <code>true</code> if the widget is optional
	 */
	public boolean isOptional();

	
	/**
	 * Checks if the value of the widget is valid. 
	 * 
	 * A check might be: If the value is non-optional the widget should contain a value which is not default.
	 * 
	 * @return <code>true</code> if the value is valid, <code>false</code> else
	 */
	public boolean isValid();
	
	/**
	 * In case the value of the widget is incalid an error message is returned.
	 * Else the return is <code>null</code>
	 * 
	 * @return <code>null</code> or an error message
	 */
	public String getErrorMessage();
}
