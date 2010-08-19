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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@link TableBinding} represents the binding between a Table UI and an
 * class.
 * <p>
 * Every instance of the bound class can be used as input for a table generated
 * tables on this binding. The columns of the table are specified by the list of
 * {@link IColumnBinding}.
 * </p>
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class TableBinding implements ITableBinding {

	/** bound class */
	private Class<?> clazz;

	/** List of columns */
	private List<IColumnBinding> columnBindings;

	/**
	 * Returns the class bound by this binding.
	 * 
	 * @return the bound class
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * Sets the class which is bound by this binding.
	 * 
	 * @param clazz
	 *            the class instance which is used to build the
	 *            {@link IColumnBinding}s.
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	/**
	 *  {@inheritDoc}
	 */
	public List<IColumnBinding> getColumnBindings() {
		if (columnBindings == null)
			return Collections.emptyList();
		return Collections.unmodifiableList(columnBindings);
	}

	/**
	 * Adds a new {@link IColumnBinding} to the list of {@link IColumnBinding}s.
	 * <p>
	 * This method does not check if the {@link IColumnBinding} already exists in
	 * the list. Therefore it is possible to add a {@link IColumnBinding} more
	 * than one time.
	 * </p>
	 * 
	 * @param cb
	 *            the new {@link IColumnBinding}
	 */
	public void addColumnBinding(IColumnBinding cb) {
		if (this.columnBindings == null)
			this.columnBindings = new ArrayList<IColumnBinding>();

		this.columnBindings.add(cb);
	}

	/**
	 * Removes the given {@link IColumnBinding} from the list of
	 * {@link IColumnBinding}s .
	 * <p>
	 * If the {@link IColumnBinding} is more than one times in the list, only the
	 * first appearance will be removed from the list.
	 * </p>
	 * <p>
	 * If the given {@link IColumnBinding} is not in the list nothing will
	 * happen.
	 * </p>
	 * 
	 * @param cb the {@link IColumnBinding} which schould be removed
	 */
	public void removeColumnBinding(IColumnBinding cb) {
		if (this.columnBindings != null)
			this.columnBindings.remove(cb);
	}
}
