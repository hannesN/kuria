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
package de.topicmapslab.kuria.swtgenerator.edit;

/**
 * Implementations may be used to listen to the state of an {@link InputMask}.
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public interface IInputMaskListener {

	/**
	 * Is called when the dirty state of the input masks changes. The dirty state indicates if the values of the edited model were changed.
	 */
	public void dirtyChanged();
	
	/**
	 * Some widgets provide a way to create a new model element and set it as value for a property. Examples are lists or combo boxes.
	 * If a new element was created this method is called, so the application can persist the new created model instance on its own way.
	 * 
	 * @param newElement the newly created instance
	 */
	public void newModelElement(Object newElement);	
}
