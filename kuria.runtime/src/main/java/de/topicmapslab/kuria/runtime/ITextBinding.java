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
