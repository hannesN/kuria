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

import java.io.File;
import java.security.InvalidParameterException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.widget.IFileBinding;
import de.topicmapslab.kuria.swtgenerator.util.Messages;

/**
 * @author Hannes Niederhausen
 * 
 */
public class FileWidget extends LabeledWidget {
	private Text textField;
	private Button browseButton;

	/**
	 * @param propertyBinding
	 */
	public FileWidget(IPropertyBinding propertyBinding, IBindingContainer bindingContainer) {
		super(propertyBinding, bindingContainer);
		if (!(propertyBinding instanceof IFileBinding))
			throw new InvalidParameterException(Messages.getString("UI.INVLAID_BINDING") + propertyBinding.getClass().getName()); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	public void createControl(Composite parent) {

		if (!(parent.getLayout() instanceof GridLayout))
			throw new IllegalArgumentException(Messages.getString("UI.NO_GRIDLAYOUT_IN_PARENT")); //$NON-NLS-1$

		GridLayout layout = (GridLayout) parent.getLayout();

		createLabel(parent);

		int flag = (!isEditable()) ? SWT.READ_ONLY : 0;

		textField = new Text(parent, flag | SWT.BORDER);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = layout.numColumns - 2;

		// set a height hint for multi column textfields.
		textField.setLayoutData(gd);
		textField.setToolTipText(propertyBinding.getDescription());

		if (isEditable()) {
			browseButton = new Button(parent, SWT.PUSH);
			browseButton.setText("..."); //$NON-NLS-1$
		} else {
			gd.horizontalSpan++;
		}
		createDecoration(textField);

		addModifyListener();
		addBrowseListener();

	}

	/**
     * 
     */
	private void addBrowseListener() {
		if (browseButton != null) {
			browseButton.addSelectionListener(new SelectionAdapter() {
				/**
				 * {@inheritDoc}
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					String oldPath = System.getProperty("user.home") + "/."; //$NON-NLS-1$ //$NON-NLS-2$
					if (isValid())
						oldPath = textField.getText() + "/."; //$NON-NLS-1$

					int swtFlag = (getPropertyBinding().isLoad()) ? SWT.OPEN : SWT.SAVE;

					FileDialog dlg = new FileDialog(browseButton.getShell(), swtFlag);
					dlg.setFilterPath(oldPath);
					dlg.setFilterExtensions(getPropertyBinding().getFileExtensions());
					String newPath = dlg.open();
					if (newPath != null) {
						textField.setText(newPath);
					}

				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid() {
		if (!isOptional() && isEditable()) {
			if (textField.getText().length() == 0)
				return false;
			java.io.File f = new java.io.File(textField.getText());
			if (!f.exists() && getPropertyBinding().isLoad())
				return false;
			if (!f.isFile() && getPropertyBinding().isLoad())
				return false;

			return true;
		}
		return super.isValid();
	}

	/**
	 * {@inheritDoc}
	 */
	public void refresh() {
		String result = ""; //$NON-NLS-1$
		if (getModel() != null) {
			try {
				Object value = getPropertyBinding().getValue(getModel());
				if (value != null) {
					if (value instanceof String) {
						result = (String) value;
					} else {
						result = value.toString();
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		textField.setText(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IFileBinding getPropertyBinding() {
		return (IFileBinding) super.getPropertyBinding();
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public void setEnabled(boolean enabled) {
		textField.setEnabled(enabled);
		if (browseButton!=null)
			browseButton.setEnabled(enabled);
		refresh();
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public void persist() {
		try {
			if (!isDirty())
				return;

			String val = textField.getText();

			Object oldValue = getPropertyBinding().getValue(getModel());
			if (val.equals(oldValue))
				return;
			if ((val.length() == 0) && (oldValue == null))
				return;

			getPropertyBinding().setValue(getModel(), val);

			notifyStateListener(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void addModifyListener() {
		textField.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (getModel() != null) {
					try {
						String text = textField.getText();

						boolean dirty = true;
						String tmp = ""; //$NON-NLS-1$
						Object val = getPropertyBinding().getValue(getModel());
						if (val != null)
							tmp = val.toString();

						if (text.equals(tmp)) {
							dirty = false;
						}

						if ((text.length() == 0) && (!getPropertyBinding().isOptional()&&isEditable())) {
							setErrorMessage(Messages.getString("UI.NO_TEXT_ENTERED")); //$NON-NLS-1$
						} else {
							setErrorMessage(null);
						}
						
						if (text.length()>0) {
							File f = new File(text);
							if (!f.exists() && (getPropertyBinding().isLoad()))
								setErrorMessage(Messages.getString("FileWidget.FILE_DOES_NOT_EXIST")); //$NON-NLS-1$
							else if (!f.isFile() && (getPropertyBinding().isLoad()))
								setErrorMessage(Messages.getString("FileWidget.IS_NOT_A_FILE", f.getName())); //$NON-NLS-1$
							else
								setErrorMessage(null);
						}

						notifyStateListener(dirty);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}
			}
		});

	}

}
