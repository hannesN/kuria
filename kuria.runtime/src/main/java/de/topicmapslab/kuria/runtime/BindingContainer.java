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

	/**
	 * Default label provider which always returns the parameter
	 */
	private static final ILabelProvider defaultProvider = new ILabelProvider() {
		public String getLabel(String label) {
			if (label==null)
				throw new IllegalArgumentException("parameter label must not be null!");
			return label;
		}
	};
	
	private ILabelProvider labelProvider;
	
	private Map<Class<?>, ITextBinding> textBindings;
	
	private Map<Class<?>, ITableBinding> tableBindings;

	private Map<Class<?>, ITreeNodeBinding> treeNodeBindings;

	private Map<Class<?>, IEditableBinding> editableBindings;

	public void setEditableBindings(Map<Class<?>, IEditableBinding> editableBindings) {
		this.editableBindings = editableBindings;
	}

	public void putEditableBindings(Class<?> c, IEditableBinding editableBinding) {
		if (editableBindings == null) {
			editableBindings = new HashMap<Class<?>, IEditableBinding>();
		}
		editableBindings.put(c, editableBinding);
	}

	/**
	 *  {@inheritDoc}
	 */
	public IEditableBinding getEditableBinding(Class<?> clazz) {
		if (editableBindings == null) {
			return null;
		}
		do {
			IEditableBinding eb = editableBindings.get(clazz);
			if (eb!=null)
				return eb;
			clazz = clazz.getSuperclass();
		} while (clazz!=Object.class);
		
		return null;
//		throw new RuntimeException("Invalid clazz - no binding founhd: "+clazz.getName());
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public IEditableBinding getDirectEditableBinding(Class<?> clazz) {
		if (editableBindings == null) {
			return null;
		}
		IEditableBinding eb = editableBindings.get(clazz);

		return eb;
//		throw new RuntimeException("Invalid clazz - no binding founhd: "+clazz.getName());
	}

	/**
	 *  {@inheritDoc}
	 */
	public Map<Class<?>, IEditableBinding> getEditableBindings() {
    	if (editableBindings==null)
    		return Collections.emptyMap();
    	return editableBindings;
    }

	public void setTableBindings(Map<Class<?>, ITableBinding> tableBindings) {
		this.tableBindings = tableBindings;
	}

	public void putTableBindings(Class<?> c, ITableBinding tableBinding) {
		if (tableBindings == null) {
			tableBindings = new HashMap<Class<?>, ITableBinding>();
		}
		tableBindings.put(c, tableBinding);
	}

	/**
	 *  {@inheritDoc}
	 */
	public ITableBinding getTableBinding(Class<?> clazz) {
    	if (tableBindings == null) {
    		return null;
    	}
    	do {
    		ITableBinding eb = tableBindings.get(clazz);
			if (eb!=null)
				return eb;
			clazz = clazz.getSuperclass();
		} while (clazz!=Object.class);
		
		return null;
    }
	
	/**
	 *  {@inheritDoc}
	 */
	public ITableBinding getDirectTableBinding(Class<?> clazz) {
		if (tableBindings == null) {
			return null;
		}
		ITableBinding eb = tableBindings.get(clazz);
		return eb;
	}

	/**
	 * 
	 *  {@inheritDoc}
	 */
	public Map<Class<?>, ITableBinding> getTableBindings() {
    	if (tableBindings==null)
    		return Collections.emptyMap();
    	return tableBindings;
    }

	public void setTreeNodeBindings(Map<Class<?>, ITreeNodeBinding> treeNodeBindings) {
		this.treeNodeBindings = treeNodeBindings;
	}

	public void putTreeNodeBindings(Class<?> c, ITreeNodeBinding treeNodeBinding) {
		if (treeNodeBindings == null) {
			treeNodeBindings = new HashMap<Class<?>, ITreeNodeBinding>();
		}
		treeNodeBindings.put(c, treeNodeBinding);
	}

	/**
	 *  {@inheritDoc}
	 */
	public ITreeNodeBinding getTreeNodeBinding(Class<?> clazz) {
		if (treeNodeBindings == null) {
			return null;
		}
		do {
			ITreeNodeBinding eb = treeNodeBindings.get(clazz);
			if (eb!=null)
				return eb;
			clazz = clazz.getSuperclass();
		} while (clazz!=Object.class);
		return null;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public ITreeNodeBinding getDirectTreeNodeBinding(Class<?> clazz) {
		if (treeNodeBindings == null) {
			return null;
		}
		ITreeNodeBinding eb = treeNodeBindings.get(clazz);
		return eb;
	}

	/**
	 *  {@inheritDoc}
	 */
	public Map<Class<?>, ITreeNodeBinding> getTreeNodeBindings() {
		if (treeNodeBindings==null)
			return Collections.emptyMap();
	    return treeNodeBindings;
    }
	
	public void setTextBindings(Map<Class<?>, ITextBinding> textBindings) {
	    this.textBindings = textBindings;
    }
	
	/**
	 *  {@inheritDoc}
	 */
	public Map<Class<?>, ITextBinding> getTextBindings() {
		if (textBindings==null)
			return Collections.emptyMap();
		
		return textBindings;
    }
	
	/**
	 * Puts a textbinding
	 * @param c the class which is bound
	 * @param textBinding the binding
	 */
	public void putTextBinding(Class<?> c, TextBinding textBinding) {
		if (textBindings == null) {
			textBindings = new HashMap<Class<?>, ITextBinding>();
		}
		textBindings.put(c, textBinding);
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public ITextBinding getTextBinding(Class<?> c) {
		if (textBindings==null) {
			return null;
		}
		do {
			ITextBinding eb = textBindings.get(c);
			if (eb!=null)
				return eb;
			c = c.getSuperclass();
		} while (c!=Object.class);
		return null;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public ITextBinding getDirectTextBinding(Class<?> c) {
		if (textBindings==null) {
			return null;
		}
		ITextBinding eb = textBindings.get(c);
		return eb;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public void setLabelProvider(ILabelProvider provider) {
	    this.labelProvider = provider;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	public ILabelProvider getLabelProvider() {
		if (labelProvider==null)
			return defaultProvider;
	    return labelProvider;
    }
}
