/**
 * 
 */
package de.topicmapslab.kuria.runtime;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.topicmapslab.kuria.runtime.table.TableBinding;
import de.topicmapslab.kuria.runtime.tree.TreeNodeBinding;
import de.topicmapslab.kuria.runtime.widget.EditableBinding;

/**
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class BindingContainer {

	private Map<Class<?>, TextBinding> textBindings;
	
	private Map<Class<?>, TableBinding> tableBindings;

	private Map<Class<?>, TreeNodeBinding> treeNodeBindings;

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

	public EditableBinding getEditableBinding(Class<?> clazz) {
		if (editableBindings == null) {
			return null;
		}
		return editableBindings.get(clazz);
	}

	public Map<Class<?>, EditableBinding> getEditableBindings() {
    	if (editableBindings==null)
    		return Collections.emptyMap();
    	return editableBindings;
    }

	public void setTableBindings(Map<Class<?>, TableBinding> tableBindings) {
		this.tableBindings = tableBindings;
	}

	public void putTableBindings(Class<?> c, TableBinding tableBinding) {
		if (tableBindings == null) {
			tableBindings = new HashMap<Class<?>, TableBinding>();
		}
		tableBindings.put(c, tableBinding);
	}

	public TableBinding getTableBinding(Class<?> clazz) {
    	if (tableBindings == null) {
    		return null;
    	}
    	return tableBindings.get(clazz);
    }

	public Map<Class<?>, TableBinding> getTableBindings() {
    	if (tableBindings==null)
    		return Collections.emptyMap();
    	return tableBindings;
    }

	public void setTreeNodeBindings(Map<Class<?>, TreeNodeBinding> treeNodeBindings) {
		this.treeNodeBindings = treeNodeBindings;
	}

	public void putTreeNodeBindings(Class<?> c, TreeNodeBinding treeNodeBinding) {
		if (treeNodeBindings == null) {
			treeNodeBindings = new HashMap<Class<?>, TreeNodeBinding>();
		}
		treeNodeBindings.put(c, treeNodeBinding);
	}

	public TreeNodeBinding getTreeNodeBinding(Class<?> clazz) {
		if (treeNodeBindings == null) {
			return null;
		}
		return treeNodeBindings.get(clazz);
	}
	
	public Map<Class<?>, TreeNodeBinding> getTreeNodeBindings() {
		if (treeNodeBindings==null)
			return Collections.emptyMap();
	    return treeNodeBindings;
    }
	
	public void setTextBindings(Map<Class<?>, TextBinding> textBindings) {
	    this.textBindings = textBindings;
    }
	
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
	
	public TextBinding getTextBinding(Class<?> c) {
		return getTextBindings().get(c);
	}
}
