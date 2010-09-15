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
