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
package de.topicmapslab.kuria.swtgenerator.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.swtgenerator.WidgetGenerator;
import de.topicmapslab.kuria.swtgenerator.edit.IInputMaskListener;
import de.topicmapslab.kuria.swtgenerator.edit.InputMask;
import de.topicmapslab.kuria.swtgenerator.util.Messages;

/**
 * 
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class ModelDialog extends Dialog implements IInputMaskListener {

	private final Class<?> modelClass;
	private InputMask inputMask;
	private final IBindingContainer bindingContainer;
	private Object model;
	
	private int width = -1;
	
	/**
	 * Creates a dialog instance. Note that the window will have no visual
	 * representation (no widgets) until it is told to open. By default,
	 * <code>open</code> blocks for dialogs.
	 * 
	 * @param parentShell
	 *            the parent shell, or <code>null</code> to create a top-level
	 *            shell	 
	 * @param bindingContainer the binding container with the
	 * @param modelClass
	 */
    public ModelDialog(Shell parentShell, IBindingContainer bindingContainer, Class<?> modelClass) {
	    super(parentShell);
	    this.modelClass = modelClass;
	    this.bindingContainer = bindingContainer;
    }
    
    /**
	 * Creates a dialog instance. Note that the window will have no visual
	 * representation (no widgets) until it is told to open. By default,
	 * <code>open</code> blocks for dialogs.
	 * 
	 * @param parentShell
	 *            the parent shell, or <code>null</code> to create a top-level
	 *            shell
	 * @param bindingContainer 
	 * @param modelClass
	 * @param width
	 */
    public ModelDialog(Shell parentShell, IBindingContainer bindingContainer, Class<?> modelClass, int width) {
	    super(parentShell);
	    this.modelClass = modelClass;
	    this.bindingContainer = bindingContainer;
	    this.width = width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control createDialogArea(Composite parent) {
    	Composite comp = new Composite(parent, SWT.NONE);
    	comp.setLayoutData(new GridData(GridData.FILL_BOTH));
    	comp.setLayout(new FillLayout());
    	
    	WidgetGenerator generator = new WidgetGenerator(bindingContainer);
    	inputMask = generator.generateEditable(modelClass, comp);
    	
    	inputMask.setModel(model);
    	
    	return comp;
    }
   
    /**
     * Sets the class instance which should be modified in the dialog.
     * @param model 
     */
    public void setModel(Object model) {
    	if (model.getClass()!=modelClass) {
    		throw new IllegalArgumentException(Messages.getString("ModelDialog.MODEL_INSTANCE_OF_TYPE")); //$NON-NLS-1$
    	}
    	
    	this.model = model;
    	if (inputMask!=null)
    		inputMask.setModel(model);
    }
    
    /**
     * Returns the model which should be edited in the dialog.
     * 
     * @return the model
     */
    public Object getModel() {
	    return model;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void okPressed() {
    	// persisting the model
    	inputMask.persist();
    	inputMask.setModel(null);
        super.okPressed();
    }

	/**
     * {@inheritDoc}
     */
    public void dirtyChanged() {
    	Button okButton = getButton(IDialogConstants.OK_ID); 
    	if (okButton!=null)
    		okButton.setEnabled(inputMask.isValid());
    }

	/**
     * {@inheritDoc}
     */
    public void newModelElement(Object newElement) {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Point getInitialSize() {
    	Point p = super.getInitialSize();
    	
    	if (p.x<width)
    		p.x=width;
    	return p;
    }
}
