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
package de.topicmapslab.kuria.swtgenerator.widgets;

/**
 * Listener for complex widgets like {@link MasterDetailWidget}.
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public interface IModelListener {

	/**
	 * This method is called after the persist method of an input mask was called.
	 * 
	 * The input mask gets a copy of the edited element which attribute 
	 * values need to be set to the original model. This can done inside the widget 
	 * or outside the widget, e.g. in another class to provide undo/redo support.
	 * 
	 * @param event the event containing the original model and the modified copy
	 */
	public void modelPersisted(PersistEvent<?> event);
	
	/**
	 * This method is called after the user triggered a delete event.
	 *
	 * Usually the UI provides a list and the user can delete his selection.
	 * Like the {@link PersistEvent} the {@link DeleteEvent} has a commit flag which 
	 * can be used to omit that the user interface does the deletion. In that case the application
	 * should execute the deletion.
	 * 
	 * @param event the event containing the list of elements which should be deleted 
	 */
	public void modelDeleted(DeleteEvent<?> event);
}
