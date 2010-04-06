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

import java.util.List;

public interface ITableBinding {

	/**
	 * Returns the class bound by this binding.
	 * 
	 * @return the bound class
	 */
	public abstract Class<?> getClazz();

	/**
	 * Returns a list which contains the {@link IColumnBinding} of this
	 * {@link TableBinding}.
	 * <p>
	 * This list is unmodifiable. If you want to add or remove
	 * {@link IColumnBinding}, use the methods {@link #addColumnBinding} and {@link #removeColumnBinding}
	 * 
	 * @return an unmodifiable list containing the {@link IColumnBinding}s of
	 *         this binding
	 */
	public abstract List<IColumnBinding> getColumnBindings();

}
