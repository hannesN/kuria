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