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
import de.topicmapslab.kuria.runtime.IBindingContainer;

/**
 * @author Hannes Niederhausen
 * 
 */
public class WidgetGenerator {

	private final TreeGenerator treeGenerator;
	private final TableGenerator tableGenerator;
	private final EditableGenerator editableGenerator;

	public WidgetGenerator(IBindingContainer bindingContainer) {
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
