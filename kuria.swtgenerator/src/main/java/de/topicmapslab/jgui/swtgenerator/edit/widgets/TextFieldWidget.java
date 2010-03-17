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
import de.topicmapslab.kuria.runtime.PropertyBinding;
import de.topicmapslab.kuria.runtime.widget.TextFieldBinding;

/**
 * @author Hannes Niederhausen
 * 
 */
public class TextFieldWidget extends LabeledWidget {

	private Text textField;

	public TextFieldWidget(PropertyBinding propertyBinding) {
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
		addVerifyListener();
		addModifyListener();
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
						notifyStateListener(dirty);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}
			}
		});

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TextFieldBinding getPropertyBinding() {
		return (TextFieldBinding) super.getPropertyBinding();
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

}
