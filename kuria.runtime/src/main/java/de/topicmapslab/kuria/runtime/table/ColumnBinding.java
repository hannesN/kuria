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

	/**
	 *  {@inheritDoc}
	 */
	public String getColumnImage() {
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

}
