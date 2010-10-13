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

import java.util.Map;

import de.topicmapslab.kuria.runtime.table.ITableBinding;
import de.topicmapslab.kuria.runtime.tree.ITreeNodeBinding;
import de.topicmapslab.kuria.runtime.widget.IEditableBinding;

public interface IBindingContainer {

	public abstract IEditableBinding getEditableBinding(Class<?> clazz);

	public abstract Map<Class<?>, IEditableBinding> getEditableBindings();

	public abstract ITableBinding getTableBinding(Class<?> clazz);

	public abstract Map<Class<?>, ITableBinding> getTableBindings();

	public abstract ITreeNodeBinding getTreeNodeBinding(Class<?> clazz);

	public abstract Map<Class<?>, ITreeNodeBinding> getTreeNodeBindings();

	public abstract Map<Class<?>, ITextBinding> getTextBindings();

	public abstract ITextBinding getTextBinding(Class<?> c);

	/**
	 * Sets a new label provider
	 * @param provider
	 */
	public void setLabelProvider(ILabelProvider provider);
	
	/**
	 * 
	 * @return the labelprovider or a defaule label provider which always returns the label
	 */
	public ILabelProvider getLabelProvider();
}
