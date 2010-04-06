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
package de.topicmapslab.jgui.swtgenerator.edit.widgets;

import java.security.InvalidParameterException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.topicmapslab.jgui.swtgenerator.edit.Validators;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.widget.ITextFieldBinding;
import de.topicmapslab.kuria.runtime.widget.TextFieldBinding;

/**
 * @author Hannes Niederhausen
 * 
 */
public class TextFieldWidget extends LabeledWidget {

	private Text textField;
	
	public TextFieldWidget(IPropertyBinding propertyBinding) {
		super(propertyBinding);
		if (!(propertyBinding instanceof TextFieldBinding))
			throw new InvalidParameterException("Invalid binding:" + propertyBinding.getClass().getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public void createControl(Composite parent) {
		if (!(parent.getLayout() instanceof GridLayout))
			throw new IllegalArgumentException("Parents layout need to be a GridLayout");

		GridLayout layout = (GridLayout) parent.getLayout();

		Label label = createLabel(parent);
		if (getPropertyBinding().getRows() > 1) {
			GridData gd = new GridData();
			gd.verticalAlignment = SWT.TOP;
			label.setLayoutData(gd);
		}

		int flag = (getPropertyBinding().isReadOnly()) ? SWT.READ_ONLY : 0;
		flag |= (getPropertyBinding().isPassword()) ? SWT.PASSWORD : 0;
		flag |= (getPropertyBinding().getRows() > 1) ? SWT.MULTI | SWT.V_SCROLL | SWT.WRAP: 0;

		textField = new Text(parent, flag | SWT.BORDER);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = layout.numColumns - 1;
		

		// set a height hint for multi column textfields.
		if (getPropertyBinding().getRows() > 1) {
			Font font = textField.getFont();
			int height = 10;
			if ((font != null) && (font.getFontData().length > 0)) {
				height = font.getFontData()[0].getHeight();
			}
			gd.heightHint = getPropertyBinding().getRows() * height;
			gd.grabExcessVerticalSpace = getPropertyBinding().isGrabVerticalSpace();
		} else {
			gd.grabExcessVerticalSpace = false;
		}
		textField.setLayoutData(gd);
		
		createDecoration(textField);
		
		addVerifyListener();
		addModifyListener();
	}
	
	@Override
	public boolean isValid() {
		if (!isOptional())
			return textField.getText().length()>0;
		// TODO regexp check
		return super.isValid();
	}

	/**
	 * {@inheritDoc}
	 */
	public void refresh() {
		String result = "";
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
	public ITextFieldBinding getPropertyBinding() {
		return (ITextFieldBinding) super.getPropertyBinding();
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public void setEnabled(boolean enabled) {
		textField.setEnabled(enabled);
		refresh();
	}

	public void persist() {
		try {
			if (!isDirty())
				return;
			
			Class<?> type = (Class<?>) getPropertyBinding().getType();
			String val = textField.getText();

			Object oldValue = getPropertyBinding().getValue(getModel());
			if (val.equals(oldValue))
				return;
			if ( (val.length()==0) && (oldValue==null))
				return;
			
			if (type.equals(String.class)) {
				getPropertyBinding().setValue(getModel(), val);

			} else if (val.length() != 0) {
				if ((type.equals(Integer.class)) || (type.equals(int.class))) {
					getPropertyBinding().setValue(getModel(), Integer.parseInt(val));
				} else if ((type.equals(Float.class)) || (type.equals(float.class))) {
					getPropertyBinding().setValue(getModel(), Float.parseFloat(val));
				} else if ((type.equals(Double.class)) || (type.equals(double.class))) {
					getPropertyBinding().setValue(getModel(), Double.parseDouble(val));
				}
			}
			// saved object is equal to textfield..
			// TODO need check if number values were added
			notifyStateListener(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a verify listener according to the type of the bound field.
	 */
	private void addVerifyListener() {
		if (getPropertyBinding().getType() instanceof Class<?>) {
			Class<?> type = (Class<?>) getPropertyBinding().getType();
			if ((type.equals(Integer.class)) || (type.equals(int.class))) {
				textField.addVerifyListener(Validators.INT_VERIFIER);
				return;
			}
	
			if ((type.equals(Float.class)) || (type.equals(float.class)) || (type.equals(Double.class))
			        || (type.equals(double.class))) {
				textField.addVerifyListener(Validators.DECIMAL_VERIFIER);
				return;
			}
		}
	}
	
	private void addModifyListener() {
		textField.addModifyListener(new ModifyListener() {
	
			public void modifyText(ModifyEvent arg0) {
				if (getModel() != null) {
					try {
						String text = textField.getText();
	
						boolean dirty = true;
						String tmp = "";
						Object val = getPropertyBinding().getValue(getModel());
						if (val != null)
							tmp = val.toString();
	
						if (text.equals(tmp)) {
							dirty = false;
						}
						
						if (text.length()==0) {
							setErrorMessage("No text entered");
						} else {
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
