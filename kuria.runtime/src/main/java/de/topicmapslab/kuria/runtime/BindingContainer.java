/**
 * 
 */
package de.topicmapslab.kuria.runtime;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.topicmapslab.kuria.runtime.table.ITableBinding;
import de.topicmapslab.kuria.runtime.tree.ITreeNodeBinding;
import de.topicmapslab.kuria.runtime.widget.EditableBinding;

/**
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class BindingContainer implements IBindingContainer {

	private Map<Class<?>, TextBinding> textBindings;
	
	private Map<Class<?>, ITableBinding> tableBindings;

	private Map<Class<?>, ITreeNodeBinding> treeNodeBindings;

	private Map<Class<?>, EditableBinding> editableBindings;

	public void setEditableBindings(Map<Class<?>, EditableBinding> editableBindings) {
		this.editableBindings = editableBindings;
	}

	public void putEditableBindings(Class<?> c, EditableBinding editableBinding) {
		if (editableBindings == null) {
			editableBindings = new HashMap<Class<?>, EditableBinding>();
		}
		editableBindings.put(c, editableBinding);
	}

	/**
	 *  {@inheritDoc}
	 */
	public EditableBinding getEditableBinding(Class<?> clazz) {
		if (editableBindings == null) {
			return null;
		}
		return editableBindings.get(clazz);
	}

	/**
	 *  {@inheritDoc}
	 */
	public Map<Class<?>, EditableBinding> getEditableBindings() {
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
    	return tableBindings.get(clazz);
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
		return treeNodeBindings.get(clazz);
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public Map<Class<?>, ITreeNodeBinding> getTreeNodeBindings() {
		if (treeNodeBindings==null)
			return Collections.emptyMap();
	    return treeNodeBindings;
    }
	
	public void setTextBindings(Map<Class<?>, TextBinding> textBindings) {
	    this.textBindings = textBindings;
    }
	
	/**
	 *  {@inheritDoc}
	 */
	public Map<Class<?>, TextBinding> getTextBindings() {
		if (textBindings==null)
			return Collections.emptyMap();
		
		return textBindings;
    }
	
	public void putTextBinding(Class<?> c, TextBinding textBinding) {
		if (textBindings == null) {
			textBindings = new HashMap<Class<?>, TextBinding>();
		}
		textBindings.put(c, textBinding);
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public TextBinding getTextBinding(Class<?> c) {
		return getTextBindings().get(c);
	}
}
