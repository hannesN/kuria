package de.topicmapslab.kuria.runtime;

import java.lang.reflect.InvocationTargetException;

public interface ITextBinding {

	/**
	 * Returns the text of the instance.
	 * <p>
	 * The text is either the result of the accessor method (if it does not
	 * return a string the toString method will be used on the result object) or
	 * the result of toString of the instance.
	 * </p>
	 * 
	 * @param instance
	 *            the instance of a class specified
	 * @return the text of the instance
	 * 
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 *             if no accessor method exists for the field
	 * @throws InvocationTargetException
	 */
	public abstract String getText(Object instance)
			throws IllegalAccessException, NoSuchMethodException,
			InvocationTargetException;

}