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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.topicmapslab.kuria.runtime.IPropertyBinding;

/**
 * A {@link IListBinding} is used to bind properties which are collections.
 * <p>
 * The binding has a style which tells the UI generator how to visualize the collection.
 * These styles are explained in {@link ListStyle}.
 * </p>
 * <p>
 * If a collection is used, mostly the interfaces of the collection is used. To create a new value in the 
 * UI it is necessary to tell the binding which implementation should be used. If non is set {@link HashSet} is
 * used for {@link Set}s and {@link ArrayList} is used for a {@link List} type. 
 * </p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public interface IListBinding extends IPropertyBinding {

	/**
	 * Returns the list style of this binding.
	 * @return the list style of the {@link ListBinding}
	 */
	public abstract ListStyle getListStyle();

	/**
	 * {@inheritDoc}
	 * 
	 * <p>If the value is an array, and the type is an collection this method converts the values according to the
	 * result of {@link #getCollectionImplementation()}.
	 */
	public abstract void setValue(Object instance, Object value)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, IllegalArgumentException;

	/**
	 * Returns the kind of implementation used for the collection
	 * or <code>null</code> if an array is used.
	 * @return the class of the 
	 */
	public abstract Class<?> getCollectionImplementation();

	/**
	 * Returns the value of the create-new flag.
	 * @return the value of the create-new flag.
	 */
	public abstract boolean isCreateNew();

}
