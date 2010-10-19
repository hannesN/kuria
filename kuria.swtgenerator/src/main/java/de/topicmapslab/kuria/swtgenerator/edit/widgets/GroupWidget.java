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
package de.topicmapslab.kuria.swtgenerator.edit.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.swtgenerator.WidgetGenerator;
import de.topicmapslab.kuria.swtgenerator.edit.IContentProvider;
import de.topicmapslab.kuria.swtgenerator.edit.IInputMaskListener;
import de.topicmapslab.kuria.swtgenerator.edit.InputMask;
import de.topicmapslab.kuria.swtgenerator.util.Messages;

/**
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class GroupWidget extends AbstractWidget implements IInputMaskListener {
	private final IBindingContainer bindingContainer;
	private InputMask inputMask;

	public GroupWidget(IPropertyBinding propertyBinding, IBindingContainer bindingContainer) {
		super(propertyBinding, bindingContainer);
		this.bindingContainer = bindingContainer;
	}

	/**
	 * {@inheritDoc}
	 */
	public void createControl(Composite parent) {
		Group g = new Group(parent, SWT.BORDER);
		g.setText(getLabel());
		g.setLayout(new FillLayout());
		WidgetGenerator gen = new WidgetGenerator(bindingContainer);
		inputMask = gen.generateEditable((Class<?>) getPropertyBinding().getType(), g);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ((GridLayout) parent.getLayout()).numColumns;
		g.setLayoutData(gd);

		createDecoration(g);
		inputMask.setContentProvider(getContentProvider());
		inputMask.addInputMaskListeners(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void persist() {
		try {
			if (!isDirty())
				return;
			
	        inputMask.persist();
	        
	        getPropertyBinding().setValue(getModel(), inputMask.getModel());
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
	}

	/**
	 * {@inheritDoc}
	 */
	public void refresh() {
		inputMask.refresh();
	}

	@Override
	public void setContentProvider(IContentProvider provider) {
		super.setContentProvider(provider);
		if (inputMask!=null)
			inputMask.setContentProvider(provider);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setEnabled(boolean enabled) {
		inputMask.setEnabled(enabled);
	}
	
	@Override
	public void setModel(Object model) {
	    super.setModel(model);
	    Object maskModel = null;
		try {
			if (model != null) {
				maskModel = getPropertyBinding().getValue(model);
			 
				if (maskModel == null) {
					// create new instance of nested property
					Class<?> type = (Class<?>) getPropertyBinding().getType();
					maskModel = type.getConstructor().newInstance();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	    inputMask.setModel(maskModel);
	}

	public void dirtyChanged() {
		if (inputMask.isValid()) {
			setErrorMessage(null);
		} else {
			setErrorMessage(Messages.getString("GroupWidget.NOT_ITEMS_ENTERED")); //$NON-NLS-1$
		}
	   notifyStateListener(inputMask.isDirty()); 
    }

	public void newModelElement(Object newElement) {
	    notifyNewModelListener(newElement);	    
    }

	@Override
	public boolean isValid() {
		if (!getPropertyBinding().isOptional())
			return inputMask.isValid();
		return true;
	}
	
}
