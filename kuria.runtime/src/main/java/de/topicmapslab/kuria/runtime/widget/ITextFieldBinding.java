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

public interface ITextFieldBinding extends IPropertyBinding {

	/**
	 * Returns the number of rows.
	 * @return
	 */
	public abstract int getRows();

	/**
	 * Returns the validation regular expression
	 * @return the current expression or <code>null</code> if no validation is needed
	 */
	public abstract String getRegExp();

	/**
	 * Returns the value of the password flag
	 * @return <code>true</code> if the text field should be a password field, <code>false</code> else
	 */
	public abstract boolean isPassword();

	/**
	 * Returns the value of the flag
	 * @return the vlue of the flag
	 */
	public abstract boolean isGrabVerticalSpace();

}
