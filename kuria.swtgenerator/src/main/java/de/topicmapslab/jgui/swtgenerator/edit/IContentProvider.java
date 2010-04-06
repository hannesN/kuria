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
