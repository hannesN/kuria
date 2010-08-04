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
package de.topicmapslab.kuria.runtime.table;

import java.lang.reflect.InvocationTargetException;

public interface IColumnBinding {
	
	/**
	 * To provide a dynamic way of setting the image, it is possible to implement a method which returns the imagepath.
	 * The name of the method is returned by this method or <code>null</code> if method exists.
	 * 
	 * @return the name of the method to retrieve a column image or <code>null</code>
	 */
	public abstract String getColumnImageMethod();
	
	/**
	 * To provide a dynamic way of setting the text of the column, it is possible to implement a method which returns the text for the column.
	 * The name of the method is returned by this method or <code>null</code> if method exists.
	 * 
	 * @return the name of the method which returns the text or <code>null</code>
	 */
	public abstract String getColumnTextMethod();
	
	/**
	 * Returns a path to an image which is rendered in the cell of the column.
	 * 
	 * @param the instance which will be rendered into the column
	 * @return path to an image file or <code>null</code> if no image exists
	 */
	public abstract String getColumnImage(Object instance);
	
	/**
	 * Returns the text which is rendered in the cell of the column.
	 * 
	 * @param the instance which will be rendered into the column
	 * @return path to an image file or <code>null</code> if no textMethod exists
	 */
	public abstract String getColumnText(Object instance);

	
	
	/**
	 * Returns the title of the column.
	 * <p>
	 * A title is used as label for the table header
	 * </p>
	 * 
	 * @return the column title for this column
	 */
	public abstract String getColumnTitle();

	public abstract Object getValue(Object instance)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;

	public abstract void setValue(Object instance, Object value)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;

}
