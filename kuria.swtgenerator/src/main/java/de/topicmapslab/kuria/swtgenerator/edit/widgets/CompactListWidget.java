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
package de.topicmapslab.kuria.swtgenerator.edit.widgets;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.ITextBinding;
import de.topicmapslab.kuria.swtgenerator.edit.IContentProvider;
import de.topicmapslab.kuria.swtgenerator.edit.dialog.SelectionDialog;
import de.topicmapslab.kuria.swtgenerator.util.Messages;

/**
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class CompactListWidget extends ListWidget {
	private Text textField;
	private Button selectionButton;
	private Button newButton;

	public CompactListWidget(IPropertyBinding propertyBinding, IBindingContainer bindingContainer) {
		super(propertyBinding, bindingContainer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateView() {
		try {
			StringBuilder builder = new StringBuilder();
			for (Object o : getSelection()) {
				ITextBinding tb = getBindingContainer().getTextBinding(o.getClass());
				if (tb != null) {
					builder.append(tb.getText(o));
				} else {
					builder.append(o.toString());
				}
				builder.append(", "); //$NON-NLS-1$
			}
			// delete the last ", "
			if (builder.length() > 0)
				builder.setLength(Math.max(0, builder.length() - 2));
			textField.setText(builder.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void createControl(Composite parent) {
		createLabel(parent);
		textField = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ((GridLayout) parent.getLayout()).numColumns - 2;
		textField.setLayoutData(gd);

		Composite bBar = new Composite(parent, SWT.NONE);
		bBar.setLayout(new GridLayout(2, false));
		
		selectionButton = new Button(bBar, SWT.PUSH);
		selectionButton.setText("..."); //$NON-NLS-1$
		
		if (getPropertyBinding().isCreateNew()) {
			newButton = new Button(bBar, SWT.NONE);
			newButton.setText(Messages.getString("UI.NEW_BUTTON_LABEL")); //$NON-NLS-1$
		}
		createDecoration(textField);
		hookButtonListener();
	}

	private void hookButtonListener() {
		selectionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell = ((Control) e.widget).getShell();
				SelectionDialog dlg = new SelectionDialog(shell, getBindingContainer(), new ListContentProvider(),
				        getPropertyBinding().getFieldName(), getModel());

				dlg.setInitialSelection(getSelection());
				if (dlg.open() == Dialog.OK) {
					try {
						setSelection(dlg.getSelection());
						updateView();
					} catch (Exception e1) {
						throw new RuntimeException(e1);
					}
				}
			}
		});
		
		if (newButton!=null) {
			newButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					createNewObject(newButton.getShell());
				}
			});
		}
	}

	public void setEnabled(boolean enabled) {
		textField.setEnabled(enabled);
		selectionButton.setEnabled(enabled);
		if (newButton!=null)
	    	newButton.setEnabled(enabled);
		if (!enabled)
			setSelection(null);
		updateView();
	}
	
	private class ListContentProvider implements IContentProvider {

		
		public Object[] getElements(String fieldname, Object model) {

			ArrayList<Object> tmp = new ArrayList<Object>(Arrays.asList(getContentProvider().getElements(fieldname, model)));
			for (Object o : getSelection()) {
				if (!tmp.contains(o)) {
					tmp.add(o);
				}
			}
			
	        return tmp.toArray();
        }

		public boolean hasContent(String fieldname, Object model) {
	        return false;
        }
		
	}
}
