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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.ITextBinding;
import de.topicmapslab.kuria.runtime.util.TypeUtil;
import de.topicmapslab.kuria.runtime.widget.ComboBinding;
import de.topicmapslab.kuria.runtime.widget.IComboBinding;
import de.topicmapslab.kuria.swtgenerator.edit.dialog.NewInstanceWizard;
import de.topicmapslab.kuria.swtgenerator.edit.dialog.NewPrimitiveValueWizard;
import de.topicmapslab.kuria.swtgenerator.util.Messages;

/**
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class ComboWidget extends LabeledWidget {

	private IBindingContainer bindingContainer;
	private Button newButton;
	private Combo combo;
	
	private List<Object>  comboValues;

	public ComboWidget(IPropertyBinding propertyBinding, IBindingContainer bindingContainer) {
		super(propertyBinding, bindingContainer);
		if (!(propertyBinding instanceof ComboBinding))
			throw new InvalidParameterException(Messages.getString("UI.INVLAID_BINDING") + propertyBinding.getClass().getName()); //$NON-NLS-1$
		this.bindingContainer = bindingContainer;
	}

	/**
	 * {@inheritDoc}
	 */
	public void createControl(Composite parent) {
		if (!(parent.getLayout() instanceof GridLayout))
			throw new IllegalArgumentException(Messages.getString("UI.NO_GRIDLAYOUT_IN_PARENT")); //$NON-NLS-1$

		GridLayout layout = (GridLayout) parent.getLayout();
		
		createLabel(parent);
		combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (getModel()==null)
					return;
				try {
	                validate();
                } catch (Exception e) {
                	throw new RuntimeException(e);
                }
			}
		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = layout.numColumns-1;

		if (getPropertyBinding().isShowNewButton()) {
			gd.horizontalSpan--;
			newButton = new Button(parent, SWT.PUSH);
			newButton.setText(Messages.getString("UI.NEW_BUTTON_LABEL")); //$NON-NLS-1$
			GridData gridData = new GridData();
			newButton.setLayoutData(gridData);
			hookButtonListener();

		}

		combo.setLayoutData(gd);
		combo.setToolTipText(propertyBinding.getDescription());
		createDecoration(combo);
		validate();
	}

	/**
	 * {@inheritDoc}
	 */
	public void refresh() {
		combo.removeAll();
		if (getModel()==null)
			return;
		try {
    		IComboBinding pb = getPropertyBinding();
			fillCombo();
    		int index = indexOfComboElement(pb.getValue(getModel()));
    		if (index >= 0) {
    			combo.select(index);
    		}
    		validate();
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void setModel(Object model) {
	    super.setModel(model);
	    comboValues = null;
	}
	
	public List<Object> getComboValues() {
		if (comboValues==null)
			return Collections.emptyList();
		return comboValues;
    }
		
	@Override
    public IComboBinding getPropertyBinding() {
    	return (IComboBinding) super.getPropertyBinding();
    }

	@Override
    public boolean isValid() {
    	if (!isOptional())
    		return combo.getSelectionIndex()>0;
    		
        return super.isValid();
    }

	private void addToComboObjects(Object o) {
    	try {
            if (comboValues == null) {
	    	    comboValues = new ArrayList<Object>();
            }
            comboValues.add(o);
            addComboItem(o);
            combo.select(comboValues.indexOf(o)+1);
            validate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	private void fillCombo() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		if (!getContentProvider().hasContent(getPropertyBinding().getFieldName(), getModel()))
			return;
		
		for (Object o : getContentProvider().getElements(getPropertyBinding().getFieldName(), getModel())) {
			addComboItem(o);
		}
	}

	private void validate() {
		if (!(getPropertyBinding().isOptional()||getPropertyBinding().isReadOnly())) {
			if (combo.getSelectionIndex()==-1) {
				setErrorMessage(Messages.getString("ComboWidget.NO_VALUE_SELECTED")); //$NON-NLS-1$
			} else {
				setErrorMessage(null);
			}			
		}
			
		try {
			if (getModel() == null)
				return;

			Object val = getPropertyBinding().getValue(getModel());
			int idx = combo.getSelectionIndex();
			if ((idx == -1) && (val == null)) {
				notifyStateListener(false);
			} else {
				if (val != null)
					notifyStateListener(!val.equals(getCurrentSelection(idx)));
				else
					notifyStateListener(true);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

	private void hookButtonListener() {
        newButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		try {
        			Shell shell = ((Control) arg0.widget).getShell();
        			Type type = getPropertyBinding().getType();
        	        if ((String.class.equals(type)) || (TypeUtil.isPrimitive(type))) {
        	            NewPrimitiveValueWizard wzrd = new NewPrimitiveValueWizard((Class<?>) type);
        	            WizardDialog dlg = new WizardDialog(shell, wzrd);
        	            wzrd.setWindowTitle(Messages.getString("ComboWidget.NEW_LABEL")+getLabel()+"..."); //$NON-NLS-1$ //$NON-NLS-2$
        	            if (dlg.open()==Dialog.OK) {
        	            	addToComboObjects(wzrd.getResult());
        	            	notifyNewModelListener(wzrd.getResult());
        	            }
        	            return;
        	        }
        	        
        	        if (getBindingContainer().getEditableBinding((Class<?>) type)!=null) {
        	        	NewInstanceWizard wzrd = new NewInstanceWizard((Class<?>) type, getBindingContainer(), getContentProvider());
        	        	WizardDialog dlg = new WizardDialog(shell, wzrd);
        	            wzrd.setWindowTitle(Messages.getString("Combo.NEW_LABEL")+getLabel()+"..."); //$NON-NLS-1$ //$NON-NLS-2$
        	            if (dlg.open()==Dialog.OK) {
        	            	addToComboObjects(wzrd.getModel());
        	            	notifyNewModelListener(wzrd.getModel());
        	            }
        	        }
        			
        		} catch (Exception e) {
        			throw new RuntimeException(e);
        		}
        	}
        });
    }

	private void addComboItem(Object o) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		if (o instanceof String) {
	    	combo.add((String) o);
	    } else {
	    	ITextBinding textBinding = bindingContainer.getTextBinding(o.getClass());
	    	if (textBinding != null) {
	    		combo.add(textBinding.getText(o));
	    	} else {
	    		combo.add(o.toString());
	    	}
	    }
    }


	private int indexOfComboElement(Object element) {
		String fieldName = getPropertyBinding().getFieldName();
		if ((element == null) || (!getContentProvider().hasContent(fieldName, getModel())) )
			return -1;

		Object[] elements = getContentProvider().getElements(fieldName, getModel());
		for (int i = 0; i < elements.length; i++) {
			if (element.equals(elements[i]))
				return i;
		}

		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setEnabled(boolean enabled) {
		combo.setEnabled(enabled);
		if (!enabled)
			combo.removeAll();
		
		if(newButton!=null)
			newButton.setEnabled(enabled);
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	public void persist() {
		try {
			if (!isDirty())
				return;
			
			int idx = combo.getSelectionIndex();
			Object val = getCurrentSelection(idx);
			getPropertyBinding().setValue(getModel(), val);

			notifyStateListener(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object getCurrentSelection(int idx) {
		Object val =null;
	    Object[] elements = getContentProvider().getElements(getPropertyBinding().getFieldName(), getModel());
	    if (idx<elements.length) {
	    	val = elements[idx];
	    } else {
	    	idx-=elements.length;
	    	if (idx<getComboValues().size())
	    		val = getComboValues().get(idx);
	    }
	    return val;
    }

	private IBindingContainer getBindingContainer() {
	    return bindingContainer;
    }
}
