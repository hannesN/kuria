/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import de.topicmapslab.jgui.swtgenerator.edit.EditableGenerator;
import de.topicmapslab.jgui.swtgenerator.edit.InputMask;
import de.topicmapslab.jgui.swtgenerator.table.TableGenerator;
import de.topicmapslab.jgui.swtgenerator.tree.TreeGenerator;
import de.topicmapslab.kuria.runtime.BindingContainer;

/**
 * @author Hannes Niederhausen
 * 
 */
public class WidgetGenerator {

	private final TreeGenerator treeGenerator;
	private final TableGenerator tableGenerator;
	private final EditableGenerator editableGenerator;

	public WidgetGenerator(BindingContainer bindingContainer) {
		this.treeGenerator = new TreeGenerator(bindingContainer);
		tableGenerator = new TableGenerator(bindingContainer); 
		editableGenerator = new EditableGenerator(bindingContainer);
	}

	public TreeViewer generateTree(Composite parent, boolean showRoot) {
		return treeGenerator.generateTree(parent, showRoot);
	}
	
	public TableViewer generateTable(Class<?> clazz, Composite parent) {
		return tableGenerator.generateTable(parent, clazz);
	}
	
	public InputMask generateEditable(Class<?> clazz, Composite parent) {
		return editableGenerator.generateInputMask(clazz, parent);
	}
}
