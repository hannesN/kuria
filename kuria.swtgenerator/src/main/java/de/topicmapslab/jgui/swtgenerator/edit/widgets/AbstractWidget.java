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
package de.topicmapslab.jgui.swtgenerator.edit.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;

import de.topicmapslab.jgui.swtgenerator.edit.IContentProvider;
import de.topicmapslab.kuria.runtime.IPropertyBinding;

public abstract class AbstractWidget implements IInputMaskWidget {

	private static final IContentProvider EMPTY_CONTENTPROVIDER = new IContentProvider() {

		public boolean hasContent(String fieldname, Object model) {
			return false;
		}

		public Object[] getElements(String fieldname, Object model) {
			return new Object[0];
		}
	};

	protected final IPropertyBinding propertyBinding;
	private Object model;
	private IContentProvider provider;
	private boolean dirty = false;
	private String errorMessage;
	private ControlDecoration textFieldDecoration;

	private List<IStateListener> listeners;

	public AbstractWidget(IPropertyBinding propertyBinding) {
		super();
		this.propertyBinding = propertyBinding;
	}

	/**
	 * {@inheritDoc}
	 */
	public IPropertyBinding getPropertyBinding() {
		return propertyBinding;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModel(Object model) {
		this.model = model;
		setErrorMessage(null);
		refresh();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getModel() {
		return model;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setContentProvider(IContentProvider provider) {
		this.provider = provider;
	}

	protected IContentProvider getContentProvider() {
		if (provider == null)
			return EMPTY_CONTENTPROVIDER;

		return provider;
	}

	public List<IStateListener> getStateListeners() {
		if (listeners == null)
			return Collections.emptyList();

		return listeners;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addStateListener(IStateListener listener) {
		if (listeners == null)
			listeners = new ArrayList<IStateListener>();
		listeners.add(listener);
	}

	public boolean isDirty() {
	    return dirty;
    }
	
	/**
	 * {@inheritDoc}
	 */
	public void removeStateListener(IStateListener listener) {
		if (getStateListeners().contains(listener))
			listeners.remove(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isOptional() {
	    return propertyBinding.isOptional();
    }
	
	/**
	 * 
	 *  {@inheritDoc}
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		updateDecoration();
	}
	
	protected void createDecoration(Control control) {
		GridData gd = (GridData) control.getLayoutData();
		if (gd==null)
			throw new RuntimeException("No GridData Set!!");
		gd.horizontalIndent = 5;
		textFieldDecoration = new ControlDecoration(control, SWT.LEFT|SWT.TOP);
		textFieldDecoration.setMarginWidth(2);
	}
	
	protected void notifyStateListener(boolean isDirty) {
		dirty = isDirty;
		for (IStateListener l : getStateListeners()) {
			l.stateChanged(propertyBinding, isDirty);
		}
	}
	
	protected void notifyNewModelListener(Object newModel) {
		for (IStateListener l : getStateListeners()) {
			l.newModelCreated(newModel);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public boolean isValid() {
	    return true;
	}
	
	/**
	 * Updates the decoration of the widget
	 */
	protected void updateDecoration() {
		if (getErrorMessage()==null) {
			textFieldDecoration.hide();
		} else {
			textFieldDecoration.setDescriptionText(getErrorMessage());
			textFieldDecoration.setImage(FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage());
			textFieldDecoration.show();
		}
	}
	
}
