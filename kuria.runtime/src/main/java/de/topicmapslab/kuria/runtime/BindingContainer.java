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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.topicmapslab.kuria.runtime.table.ITableBinding;
import de.topicmapslab.kuria.runtime.tree.ITreeNodeBinding;
import de.topicmapslab.kuria.runtime.widget.IEditableBinding;

/**
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class BindingContainer implements IBindingContainer {

	private Map<Object, ITextBinding> textBindings;
	
	private Map<Object, ITableBinding> tableBindings;

	private Map<Object, ITreeNodeBinding> treeNodeBindings;

	private Map<Object, IEditableBinding> editableBindings;

	public void setEditableBindings(Map<Object, IEditableBinding> editableBindings) {
		this.editableBindings = editableBindings;
	}

	public void putEditableBindings(Object c, IEditableBinding editableBinding) {
		if (editableBindings == null) {
			editableBindings = new HashMap<Object, IEditableBinding>();
		}
		editableBindings.put(c, editableBinding);
	}

	/**
	 *  {@inheritDoc}
	 */
	public IEditableBinding getEditableBinding(Object clazz) {
		if (editableBindings == null) {
			return null;
		}
		return editableBindings.get(clazz);
	}

	/**
	 *  {@inheritDoc}
	 */
	public Map<Object, IEditableBinding> getEditableBindings() {
    	if (editableBindings==null)
    		return Collections.emptyMap();
    	return editableBindings;
    }

	public void setTableBindings(Map<Object, ITableBinding> tableBindings) {
		this.tableBindings = tableBindings;
	}

	public void putTableBindings(Object c, ITableBinding tableBinding) {
		if (tableBindings == null) {
			tableBindings = new HashMap<Object, ITableBinding>();
		}
		tableBindings.put(c, tableBinding);
	}

	/**
	 *  {@inheritDoc}
	 */
	public ITableBinding getTableBinding(Object clazz) {
    	if (tableBindings == null) {
    		return null;
    	}
    	return tableBindings.get(clazz);
    }

	/**
	 * 
	 *  {@inheritDoc}
	 */
	public Map<Object, ITableBinding> getTableBindings() {
    	if (tableBindings==null)
    		return Collections.emptyMap();
    	return tableBindings;
    }

	public void setTreeNodeBindings(Map<Object, ITreeNodeBinding> treeNodeBindings) {
		this.treeNodeBindings = treeNodeBindings;
	}

	public void putTreeNodeBindings(Object c, ITreeNodeBinding treeNodeBinding) {
		if (treeNodeBindings == null) {
			treeNodeBindings = new HashMap<Object, ITreeNodeBinding>();
		}
		treeNodeBindings.put(c, treeNodeBinding);
	}

	/**
	 *  {@inheritDoc}
	 */
	public ITreeNodeBinding getTreeNodeBinding(Object clazz) {
		if (treeNodeBindings == null) {
			return null;
		}
		return treeNodeBindings.get(clazz);
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public Map<Object, ITreeNodeBinding> getTreeNodeBindings() {
		if (treeNodeBindings==null)
			return Collections.emptyMap();
	    return treeNodeBindings;
    }
	
	public void setTextBindings(Map<Object, ITextBinding> textBindings) {
	    this.textBindings = textBindings;
    }
	
	/**
	 *  {@inheritDoc}
	 */
	public Map<Object, ITextBinding> getTextBindings() {
		if (textBindings==null)
			return Collections.emptyMap();
		
		return textBindings;
    }
	
	public void putTextBinding(Object c, TextBinding textBinding) {
		if (textBindings == null) {
			textBindings = new HashMap<Object, ITextBinding>();
		}
		textBindings.put(c, textBinding);
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public ITextBinding getTextBinding(Object c) {
		return getTextBindings().get(c);
	}
}
