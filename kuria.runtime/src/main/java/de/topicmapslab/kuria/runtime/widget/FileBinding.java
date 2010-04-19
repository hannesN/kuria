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
package de.topicmapslab.kuria.runtime.widget;

import de.topicmapslab.kuria.runtime.PropertyBinding;

/**
 * This binding indicates that the UI should offer a way to render a
 * absolute path to a file and provide a way to edit this path.
 *   
 * This could be done via entering the text into a text field or choose
 * a new file via a standard dialog.
 * 
 * @author Hannes Niederhausen
 *
 */
public class FileBinding extends PropertyBinding implements IFileBinding {

	private String[] fileExtensions;
	
	/**
	 * The supported file extensions. These extensions must have the form:
	 * <b>"*.extension"</b>. For every entry in the array the file dialog provides 
	 * a selection in the combo box. 
	 * 
	 *  If you want to filter more than one extension at once use:
	 *  <b>"*.ext1;*.ext2"</b> as entry.
	 */
	public void setFileExtensions(String[] fileExtensions) {
	    this.fileExtensions = fileExtensions;
    }

	/**
	 * {@inheritDoc}
	 */
	public String[] getFileExtensions() {
	    return fileExtensions;
    }
	
	
}
