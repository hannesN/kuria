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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.util.TypeUtil;
import de.topicmapslab.kuria.runtime.widget.IListBinding;
import de.topicmapslab.kuria.runtime.widget.ListBinding;
import de.topicmapslab.kuria.swtgenerator.edit.dialog.NewInstanceWizard;
import de.topicmapslab.kuria.swtgenerator.edit.dialog.NewPrimitiveValueWizard;

/**
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public abstract class ListWidget extends LabeledWidget {

	private Object[] selection;
	private IBindingContainer bindingContainer;
	
	public ListWidget(IPropertyBinding propertyBinding, IBindingContainer bindingContainer) {
		super(propertyBinding);
		if (!(propertyBinding instanceof ListBinding))
			throw new InvalidParameterException("Invalid binding:" + propertyBinding.getClass().getName());
		this.bindingContainer = bindingContainer;
	}

	/**
	 * {@inheritDoc}
	 */
	public void persist() {
		if (!isDirty())
			return;
		
		try {
			
			if (getValueArray().equals(getSelection()))
				return;
			
	        getPropertyBinding().setValue(getModel(), getSelection());
	        notifyStateListener(false);
        } catch (Exception e) {
        	throw new RuntimeException(e);
        } 
	}
	
    public void refresh() {
		Object[] vals = null;
		if (getModel() == null) {
			vals = new Object[0];
		} else {
			try {
				// using cache for values
				vals = getValueArray();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		setSelection(vals);
		updateView();
	}

	@SuppressWarnings("unchecked")
    private Object[] getValueArray() throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
		Object[] vals = null;
		if (getModel()==null)
			return new Object[0];
	    if (getPropertyBinding().isArray()) {
	    	vals = (Object[]) getPropertyBinding().getValue(getModel());
	    } else if (getPropertyBinding().isCollection()) {
	    	Collection<Object> coll = ((Collection<Object>) getPropertyBinding().getValue(getModel()));
	    	if (coll != null)
	    		vals = coll.toArray();
	    }
	    if (vals == null)
	    	vals = new Object[0];
	    return vals;
    }
	
	@Override
	public boolean isValid() {
		if (!isOptional())
			return getSelection().length>0;
		
	    return super.isValid();
	}
	
	/**
	 * Updates the UI elements using the current selection.
	 * 
	 */
	protected abstract void updateView();
	
    protected void setSelection(Object[] selection) {
		this.selection = selection;
		try {
			if (selection == null)
				return;

			if (!isOptional()) {
				if (getSelection().length == 0) {
					setErrorMessage("No Item chosen");
				} else {
					setErrorMessage(null);
				}
			} else {
				setErrorMessage(null);
			}
			Object[] valueArray = getValueArray();
			if (selection.length != valueArray.length) {
				notifyStateListener(true);
				return;
			}
			List<?> l = Arrays.asList(selection);
			for (Object o : valueArray) {
				if (!l.contains(o)) {
					notifyStateListener(true);
					return;
				}
			}
			notifyStateListener(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object[] getSelection() {
		if (selection==null)
			return new Object[0];
		return selection;
	}
	
	protected void addToSelection(Object o) {
		Object[] oldSel = getSelection();
		Object[] newSelection = new Object[oldSel.length+1];
		System.arraycopy(oldSel, 0, newSelection, 0, oldSel.length);
		newSelection[oldSel.length]=o;
		setSelection(newSelection);
	}
	
	public IBindingContainer getBindingContainer() {
	    return bindingContainer;
    }
	
	protected void createNewObject(Shell shell) {
	    try {
	        Type type = getPropertyBinding().getElementType();
	        if ((String.class.equals(type)) || (TypeUtil.isPrimitive(type))) {
	            NewPrimitiveValueWizard wzrd = new NewPrimitiveValueWizard((Class<?>) type);
	            WizardDialog dlg = new WizardDialog(shell, wzrd);
	            wzrd.setWindowTitle("New "+getPropertyBinding().getLabel()+"...");
	            if (dlg.open()==Dialog.OK) {
	            	addToSelection(wzrd.getResult());
	            	notifyNewModelListener(wzrd.getResult());
	            }
	            return;
	        }
	        if (getBindingContainer().getEditableBinding((Class<?>) type)!=null) {
	        	NewInstanceWizard wzrd = new NewInstanceWizard((Class<?>) type, getBindingContainer());
	        	WizardDialog dlg = new WizardDialog(shell, wzrd);
	            wzrd.setWindowTitle("New "+getPropertyBinding().getLabel()+"...");
	            if (dlg.open()==Dialog.OK) {
	            	addToSelection(wzrd.getModel());
	            	notifyNewModelListener(wzrd.getModel());
	            }
	        }
	    } catch (Exception e) {
	    	throw new RuntimeException(e);
	    }
    }
	
	@Override
	public IListBinding getPropertyBinding() {
	    return (IListBinding) super.getPropertyBinding();
	}
}
