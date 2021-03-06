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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.util.TypeUtil;
import de.topicmapslab.kuria.swtgenerator.edit.dialog.NewInstanceWizard;
import de.topicmapslab.kuria.swtgenerator.edit.dialog.NewPrimitiveValueWizard;
import de.topicmapslab.kuria.swtgenerator.edit.dialog.SelectionDialog;
import de.topicmapslab.kuria.swtgenerator.util.Messages;
import de.topicmapslab.kuria.swtgenerator.util.TextBindingLabelProvider;

/**
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class TableSelectionWidget extends ListWidget {

	
	private TableViewer viewer;
	private Button newButton;
	private Button addButton;
	private Button removeButton;
	private String errorMessage;

	public TableSelectionWidget(IPropertyBinding propertyBinding, IBindingContainer bindingContainer) {
	    super(propertyBinding, bindingContainer);
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateView() {
		viewer.setInput(getSelection());
	}
	
	public void setEnabled(boolean enabled) {
	    viewer.getTable().setEnabled(enabled);
	    viewer.getTable().setToolTipText(propertyBinding.getDescription());

	    if (addButton!=null)
	    	addButton.setEnabled(enabled);
	    if (newButton!=null)
	    	newButton.setEnabled(enabled);
	    if (removeButton!=null)
	    	removeButton.setEnabled(enabled);
	    if (!enabled)
	    	setSelection(null);
	    updateView();
	}
	

	/**
	 * {@inheritDoc}
	 */
	public void createControl(Composite parent) {
		createLabel(parent);
		
		viewer = new TableViewer(parent, SWT.BORDER);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new TextBindingLabelProvider(getBindingContainer()));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 100;
		gd.horizontalSpan = ((GridLayout)parent.getLayout()).numColumns-2;
		viewer.getTable().setLayoutData(gd);

		
		
		if (isEditable()) {
			// button bar
			Composite bBar = new Composite(parent, SWT.NONE);
			gd = new GridData();
			gd.verticalAlignment = SWT.CENTER;
			bBar.setLayoutData(gd);
			bBar.setLayout(new GridLayout());
			
			if (getPropertyBinding().isCreateNew()) {
				newButton = new Button(bBar, SWT.PUSH);
				newButton.setText(Messages.getString("UI.NEW_BUTTON_LABEL") + " "); //$NON-NLS-2$
				newButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			}

			addButton = new Button(bBar, SWT.PUSH);
			addButton.setText(Messages.getString("UI.ADD")); //$NON-NLS-1$
			addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			removeButton = new Button(bBar, SWT.PUSH);
			removeButton.setText(Messages.getString("UI.REMOVE")); //$NON-NLS-1$
			removeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		} else {
			// its a column wider because we have no button bar
			gd.horizontalSpan++;
		}
		createDecoration(viewer.getTable());
		hookButtonListener();
	}

	private void hookButtonListener() {
		if (addButton != null) {
			addButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					SelectionDialog dlg = new SelectionDialog(((Control) e.widget).getShell(), getBindingContainer(),
					        getContentProvider(), getPropertyBinding().getFieldName(), getModel());

					if (dlg.open() == Dialog.OK) {
						Object[] oldList = getSelection();

						Set<Object> set = new HashSet<Object>();
						for (Object o : dlg.getSelection()) {
							set.add(o);
						}
						if (oldList != null) {
							for (Object o : oldList) {
								set.add(o);
							}
						}
						setSelection(set.toArray());
						updateView();
					}
				}
			});
		}
		if (newButton != null) {
			newButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					createNewObject();
				}
			});
		}
		if (removeButton != null) {
			removeButton.addSelectionListener(new SelectionAdapter() {
				@SuppressWarnings("unchecked")
				@Override
				public void widgetSelected(SelectionEvent e) {
					IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
					if (sel.isEmpty())
						setSelection(new Object[0]);
					else {
						List<Object> list = new ArrayList<Object>(Arrays.asList(getSelection()));
						Iterator<Object> it = sel.iterator();
						while (it.hasNext()) {
							Object o = it.next();
							list.remove(o);
						}
						setSelection(list.toArray());
					}
					updateView();
				}
			});
		}
    }

	private void createNewObject() {
	    try {
	        Type type = getPropertyBinding().getElementType();
	        if ((String.class.equals(type)) || (TypeUtil.isPrimitive(type))) {
	            NewPrimitiveValueWizard wzrd = new NewPrimitiveValueWizard((Class<?>) type);
	            WizardDialog dlg = new WizardDialog(newButton.getShell(), wzrd);
	            wzrd.setWindowTitle(Messages.getString("UI.NEW_LABEL")+getLabel()+"..."); //$NON-NLS-1$ //$NON-NLS-2$
	            if (dlg.open()==Dialog.OK) {
	            	addToSelection(wzrd.getResult());
	            	notifyNewModelListener(wzrd.getResult());
	            }
	            updateView();
	            return;
	        }
	        if (getBindingContainer().getEditableBinding((Class<?>) type)!=null) {
	        	NewInstanceWizard wzrd = new NewInstanceWizard((Class<?>) type, getBindingContainer(),getContentProvider());
	        	WizardDialog dlg = new WizardDialog(newButton.getShell(), wzrd);
	            wzrd.setWindowTitle(Messages.getString("UI.NEW_LABEL")+getLabel()+"..."); //$NON-NLS-1$ //$NON-NLS-2$
	            if (dlg.open()==Dialog.OK) {
	            	addToSelection(wzrd.getModel());
	            	notifyNewModelListener(wzrd.getModel());
	            }
	        }
	        updateView();
	    } catch (Exception e) {
	    	throw new RuntimeException(e);
	    }
    }
	
	public String getErrorMessage() {
		// TODO Implement Error Message 
		return this.errorMessage;
	}
	

}
