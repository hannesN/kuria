package de.topicmapslab.kuria.runtime;

import java.util.Map;

import de.topicmapslab.kuria.runtime.table.ITableBinding;
import de.topicmapslab.kuria.runtime.tree.ITreeNodeBinding;
import de.topicmapslab.kuria.runtime.widget.EditableBinding;

public interface IBindingContainer {

	public abstract EditableBinding getEditableBinding(Class<?> clazz);

	public abstract Map<Class<?>, EditableBinding> getEditableBindings();

	public abstract ITableBinding getTableBinding(Class<?> clazz);

	public abstract Map<Class<?>, ITableBinding> getTableBindings();

	public abstract ITreeNodeBinding getTreeNodeBinding(Class<?> clazz);

	public abstract Map<Class<?>, ITreeNodeBinding> getTreeNodeBindings();

	public abstract Map<Class<?>, TextBinding> getTextBindings();

	public abstract TextBinding getTextBinding(Class<?> c);

}