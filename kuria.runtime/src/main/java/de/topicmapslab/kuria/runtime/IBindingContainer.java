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

}