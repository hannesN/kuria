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

import java.text.SimpleDateFormat;
import java.util.Date;

import de.topicmapslab.kuria.runtime.PropertyBinding;

/**
 * The DateBinding binds an a field with the type {@link Date} to the UI.
 * <p>
 * The date has one additional attribute: format. This format specifies the format of
 * the textual representation of the date. Refer to {@link SimpleDateFormat} for more information.
 * </p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class DateBinding extends PropertyBinding implements IDateBinding {

	private String format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	/**
	 *  {@inheritDoc}
	 */
	public String getFormat() {
	    return format;
    }
	
	/**
	 * Sets the textual representation of the format.
	 * @param format the format of the textual representation
	 */
	public void setFormat(String format) {
	    this.format = format;
    }
}
