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
 * {@link ColumnBinding}.
 * </p>
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class TableBinding {

	/** bound class */
	private Class<?> clazz;

	/** List of columns */
	private List<ColumnBinding> columnBindings;

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
	 *            {@link ColumnBinding}s.
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Returns a list which contains the {@link ColumnBinding} of this
	 * {@link TableBinding}.
	 * <p>
	 * This list is unmodifiable. If you want to add or remove
	 * {@link ColumnBinding}, use the methods {@link #addColumnBinding} and {@link #removeColumnBinding}
	 * 
	 * @return an unmodifiable list containing the {@link ColumnBinding}s of
	 *         this binding
	 */
	public List<ColumnBinding> getColumnBindings() {
		if (columnBindings == null)
			return Collections.emptyList();
		return Collections.unmodifiableList(columnBindings);
	}

	/**
	 * Adds a new {@link ColumnBinding} to the list of {@link ColumnBinding}s.
	 * <p>
	 * This method does not check if the {@link ColumnBinding} already exists in
	 * the list. Therefore it is possible to add a {@link ColumnBinding} more
	 * than one time.
	 * </p>
	 * 
	 * @param cb
	 *            the new {@link ColumnBinding}
	 */
	public void addColumnBinding(ColumnBinding cb) {
		if (this.columnBindings == null)
			this.columnBindings = new ArrayList<ColumnBinding>();

		this.columnBindings.add(cb);
	}

	/**
	 * Removes the given {@link ColumnBinding} from the list of
	 * {@link ColumnBinding}s .
	 * <p>
	 * If the {@link ColumnBinding} is more than one times in the list, only the
	 * first appearance will be removed from the list.
	 * </p>
	 * <p>
	 * If the given {@link ColumnBinding} is not in the list nothing will
	 * happen.
	 * </p>
	 * 
	 * @param cb the {@link ColumnBinding} which schould be removed
	 */
	public void removeColumnBinding(ColumnBinding cb) {
		if (this.columnBindings != null)
			this.columnBindings.remove(cb);
	}
}
