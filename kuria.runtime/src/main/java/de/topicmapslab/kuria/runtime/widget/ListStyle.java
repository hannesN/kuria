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

/**
 * Enumeration to specify the UI style of a field with multiple values, e.g. sets, lists or arrays.
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public enum ListStyle {
	/**
	 * <p>This style specifes, that the alredy set values will be rendered in a TextFiled, as comma separated
	 * text. For creation the TextBinding of the element is used. </p>
	 * 
	 * <p>To edit the field, a button is positioned next to the renderer which opens a selection  dialog.
	 * All selected elements will be set as values to the field. If the field contains values which are not 
	 * selected in the dialog, this elements will be removed.</p>
	 */
	COMPACT,
	/**
	 * <p>This style tells the UI Generator to create a table with the already set values. Nect to the table 
	 * will be a button bar, which contains a remove and an add button. If remove is pressed ever selected element in the
	 * table will be removed. To add new elements, a selection dialog will be opened like in the compact style. 
	 * Elements which are used as values but are not selected in the selection dialog remain in the field until
	 * they are removed from the table.</p>
	 */
	TABLE
	// TODO other styles like check box table
}
