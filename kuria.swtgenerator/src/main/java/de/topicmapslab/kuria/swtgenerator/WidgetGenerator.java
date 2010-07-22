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
package de.topicmapslab.kuria.swtgenerator;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.topicmapslab.kuria.runtime.BindingContainer;
import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.table.TableBinding;
import de.topicmapslab.kuria.runtime.widget.EditableBinding;
import de.topicmapslab.kuria.swtgenerator.edit.EditableGenerator;
import de.topicmapslab.kuria.swtgenerator.edit.InputMask;
import de.topicmapslab.kuria.swtgenerator.table.TableGenerator;
import de.topicmapslab.kuria.swtgenerator.tree.TreeGenerator;
import de.topicmapslab.kuria.swtgenerator.util.IImageCallback;
import de.topicmapslab.kuria.swtgenerator.util.ImageRegistry;

/**
 * The widget generator is used to create the widgets for a specific binding.
 * 
 * @author Hannes Niederhausen
 * 
 */
public class WidgetGenerator {

	private final TreeGenerator treeGenerator;
	private final TableGenerator tableGenerator;
	private final EditableGenerator editableGenerator;

	/**
	 * The constructor
	 * 
	 * @param bindingContainer the {@link BindingContainer} containing the bindings to use.
	 */
	public WidgetGenerator(IBindingContainer bindingContainer) {
		this.treeGenerator = new TreeGenerator(bindingContainer);
		tableGenerator = new TableGenerator(bindingContainer); 
		editableGenerator = new EditableGenerator(bindingContainer);
	}

	/**
	 * Generates a {@link TreeViewer} using the given parent {@link Composite}
	 * 
	 * @param parent the parent for the widget
	 * @param showRoot flag whether showing the root node
	 * @return
	 */
	public TreeViewer generateTree(Composite parent, boolean showRoot) {
		return treeGenerator.generateTree(parent, showRoot);
	}
	
	/**
	 * Generates a {@link TreeViewer} using the given parent {@link Composite} and the given implementation of
	 * {@link IContextMenuListener}.
	 * 
	 * @param parent the parent for the widget
	 * @param showRoot flag whether showing the root node
	 * @param contextMenuListener an implementation of {@link IContextMenuListener} which is called when the context menu will be opened to add more actions
	 * @return
	 */
	public TreeViewer generateTree(Composite parent, boolean showRoot, IContextMenuListener contextMenuListener) {
		return treeGenerator.generateTree(parent, showRoot, contextMenuListener);
	}
	
	/**
	 * Generates a table viewer for the specified class. The class must have a {@link TableBinding} in
	 * the {@link BindingContainer}.
	 * 
	 * @param clazz the class which is the type of the table elements
	 * @param parent the parent widget
	 * @return
	 */
	public TableViewer generateTable(Class<?> clazz, Composite parent) {
		return tableGenerator.generateTable(parent, clazz);
	}
	
	/**
	 * Generates a table viewer for the specified class. The class must have a {@link TableBinding} in
	 * the {@link BindingContainer}.
	 * 
	 * @param clazz the class which is the type of the table elements
	 * @param parent the parent widget
	 * @param contextMenuListener an implementation of {@link IContextMenuListener} which is called when the context menu will be opened to add more actions
	 * @return
	 */
	public TableViewer generateTable(Class<?> clazz, Composite parent, IContextMenuListener contextMenuListener) {
		return tableGenerator.generateTable(parent, clazz, contextMenuListener);
	}
	
	/**
	 * Generates a {@link InputMask} for the given class using the given widget as parent.
	 * the class must have a {@link EditableBinding}.
	 * 
	 * @param clazz the class which instances should be edited
	 * @param parent the parent widget
	 * @return
	 */
	public InputMask generateEditable(Class<?> clazz, Composite parent) {
		return generateEditable(clazz, parent, SWT.NONE);
	}
	
	/**
	 * Generates a {@link InputMask} for the given class using the given widget as parent.
	 * the class must have a {@link EditableBinding}.
	 * 
	 * @param clazz the class which instances should be edited
	 * @param parent the parent widget
	 * @return
	 */
	public InputMask generateEditable(Class<?> clazz, Composite parent, int style) {
		return editableGenerator.generateInputMask(clazz, parent, style);
	}
	
	/**
	 * Adds a {@link IImageCallback} instance to the internal image registry
	 * 
     * @param callbackList the callbackList to set
     */
    public static void addImageCallback(IImageCallback callback) {
	    ImageRegistry.addImageCallback(callback);
    }
    
    /**
     * Removes a {@link IImageCallback} instance to the internal image registry
     * 
     * @param callbackList the callbackList to set
     */
    public static void removeImageCallback(IImageCallback callback) {
    	ImageRegistry.removeImageCallback(callback);
    }
}
