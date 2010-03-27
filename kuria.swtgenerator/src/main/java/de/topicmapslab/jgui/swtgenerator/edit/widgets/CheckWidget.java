/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import de.topicmapslab.kuria.runtime.IPropertyBinding;

/**
 * @author Hannes Niederhausen
 * 
 */
public class CheckWidget extends AbstractWidget {

	private Button button;

	public CheckWidget(IPropertyBinding propertyBinding) {
		super(propertyBinding);
	}

	/**
	 * {@inheritDoc}
	 */
	public void createControl(Composite parent) {
		button = new Button(parent, SWT.CHECK);
		button.setText(getPropertyBinding().getLabel());
		GridData gd = new GridData();
		gd.horizontalSpan = ((GridLayout)parent.getLayout()).numColumns;
		button.setLayoutData(gd);
		hookButtonListener();
	}

	private void hookButtonListener() {
	    button.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent arg0) {
	    		if (getModel()!=null) {
	    			try {
	                    Boolean b = (Boolean) getPropertyBinding().getValue(getModel());
	                    if (b.equals(button.getSelection())) {
		                    notifyStateListener(false);
	                    } else {
		                    notifyStateListener(true);
	                    }
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
	public void persist() {
		try {
			getPropertyBinding().setValue(getModel(), button.getSelection());
			notifyStateListener(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void refresh() {
		if (getModel()==null)
			return;
		try {
			if (!isDirty())
				return;
			
			Boolean value = (Boolean) getPropertyBinding().getValue(getModel());
			button.setSelection(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
		if (!enabled)
			button.setSelection(false);
	}

}
