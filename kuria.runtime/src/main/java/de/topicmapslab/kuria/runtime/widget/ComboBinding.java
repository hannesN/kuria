/**
 * 
 */
package de.topicmapslab.kuria.runtime.widget;

import de.topicmapslab.kuria.runtime.PropertyBinding;

/**
 * The {@link ComboBinding} binds an attribute of type array, list or set to a 
 * selection or combo box.
 * <p>What values the selection box shows depends on the implementation of the UI Generators.
 * The {@link ComboBinding} has an additional attribute: {@link #showNewButton}. This flag indicates
 * if the UI should support the generation of a new possible value.
 * </p>  
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class ComboBinding extends PropertyBinding {

	private boolean showNewButton = false;
	
	/**
	 * Returns the value of showNewButton
	 * @return <code>true</code> if a the "new-button" should be generated, else <code>false</code>
	 */
	public boolean isShowNewButton() {
	    return showNewButton;
    }
	
	/**
	 * Sets the flag for creating a "new-button" in the UI.
	 * @param showNewButton <code>true</code> or <code>false</code>
	 */
	public void setShowNewButton(boolean showNewButton) {
	    this.showNewButton = showNewButton;
    }
}
