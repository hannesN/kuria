/*******************************************************************************
 * Copyright 2010, Topic Maps Lab
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
public class ComboBinding extends PropertyBinding implements IComboBinding {

	private boolean showNewButton = false;
	
	/**
	 *  {@inheritDoc}
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
