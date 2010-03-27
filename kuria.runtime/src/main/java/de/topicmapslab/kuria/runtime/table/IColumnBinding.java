package de.topicmapslab.kuria.runtime.table;

import java.lang.reflect.InvocationTargetException;

public interface IColumnBinding {

	/**
	 * Returns a path to an image which is rendered in every cell of the column.
	 * 
	 * @return path to an image file or <code>null</code> if no image exists
	 */
	public abstract String getColumnImage();

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