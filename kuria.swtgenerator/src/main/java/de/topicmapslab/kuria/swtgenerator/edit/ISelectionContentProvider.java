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
package de.topicmapslab.kuria.swtgenerator.edit;

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
