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
package de.topicmapslab.kuria.runtime.table;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * A ColumnBinding connects a field of a class with a {@link TableBinding}.
 * <p>
 * Each {@link ColumnBinding} Specifies a title and an image of the column. The
 * title can be sued as label for the column inside a column header.
 * </p>
 * <p>
 * If an image is given is used in some UIs as visual representation of the
 * type.
 * </p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class ColumnBinding implements IColumnBinding {

	private String fieldName;

	/** The column title */
	private String columnTitle;

	/** the column image */
	private String columnImage;
	
	/** the column image method name */
	private String columnImageMethod;
	
	/** the column text method name */
	private String columnTextMethod;

	/**
	 *  {@inheritDoc}
	 */
	public String getColumnImage(Object instance) {
		try {
	        if (columnImageMethod != null) {
		        return (String) instance.getClass().getDeclaredMethod(columnImageMethod).invoke(instance);
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return columnImage;
	}

	/**
	 *  {@inheritDoc}
	 */
	public String getColumnTitle() {
		if (columnTitle == null)
			return Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		return columnTitle;
	}

	/**
	 * Sets the path to the column image.
	 * 
	 * @param columnImage
	 */
	public void setColumnImage(String columnImage) {
		this.columnImage = columnImage;
	}

	/**
	 * Sets the title of the column.
	 * 
	 * @param columnTitle
	 */
	public void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 *  {@inheritDoc}
	 */
	public Object getValue(Object instance) throws IllegalAccessException, InvocationTargetException,
	        NoSuchMethodException {
		return PropertyUtils.getProperty(instance, fieldName);
	}

	/**
	 *  {@inheritDoc}
	 */
	public void setValue(Object instance, Object value) throws IllegalAccessException, InvocationTargetException,
	        NoSuchMethodException {
		PropertyUtils.setProperty(instance, fieldName, value);
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public String getColumnImageMethod() {
	    return columnImageMethod;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public String getColumnTextMethod() {
	    return columnTextMethod;
    }
	
	/**
	 *  {@inheritDoc}
	 */
	public String getColumnText(Object instance) {
		try {
	        if (columnTextMethod != null) {
		        return (String) instance.getClass().getDeclaredMethod(columnTextMethod).invoke(instance);
	        }
	        return null;
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
		
	}
	
	/**
	 * Sets the column image method name
	 * @param columnImageMethod
	 */
	public void setColumnImageMethod(String columnImageMethod) {
	    this.columnImageMethod = columnImageMethod;
    }
	
	/**
	 * Sets the column image method name
	 * 
	 * @param columnTextMethod
	 */
	public void setColumnTextMethod(String columnTextMethod) {
	    this.columnTextMethod = columnTextMethod;
    }
	
}
