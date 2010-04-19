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

import de.topicmapslab.kuria.runtime.IPropertyBinding;

public interface IFileBinding extends IPropertyBinding{

	/**
	 * Returns the supported file extensions. These extensions have the form:
	 * <b>"*.extension"</b>. For every entry in the array the file dialog provides 
	 * a selection in the combo box. 
	 *
	 *  @return array of supported file extensions
	 */
	public abstract String[] getFileExtensions();
	
	/**
	 * Returns the value of the load flag.
	 * 
	 * If the load flag is <code>true</code> the file needs to exist in the file system. If <code>false</code>
	 * the file will be created on demand which can be used in a save dialog.
	 * 
	 * @return <code>true</code> if the file is loaded, <code>false</code> else
	 */
	public abstract boolean isLoad();

}