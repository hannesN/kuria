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

public interface IDateBinding extends IPropertyBinding {

	/** 
	 * Returns the format of the textual representation of the field.
	 * @return the textual representation of the format
	 */
	public abstract String getFormat();

	/**
	 * Returns the flag whether to show the time of the date in the UI.
	 * @return the flag whether to show the time of the date in the UI.
	 */
	public abstract boolean isShowTime();
}
