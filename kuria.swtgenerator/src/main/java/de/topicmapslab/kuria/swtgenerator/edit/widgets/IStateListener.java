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
package de.topicmapslab.kuria.swtgenerator.edit.widgets;

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
