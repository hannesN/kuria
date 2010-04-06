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
package de.topicmapslab.jgui.swtgenerator.edit.dialog;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import de.topicmapslab.jgui.swtgenerator.edit.Validators;

/**
 * @author Hannes Niederhausen
 *
 */
public class NewPrimitiveValueWizard extends Wizard {
	private NewPrimitiveValueWizardPage page;
	
	private final Class<?> type;
	
	private Object result;
	
	public NewPrimitiveValueWizard(Class<?> modelType) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		this.type = modelType;
    }
	
	@Override
	public boolean performFinish() {
		page.persist();
		return true;
	}
	
	@Override
	public void addPages() {
	    super.addPages();
	    page = new NewPrimitiveValueWizardPage();
	    addPage(page);
	}
	
	public Object getResult() {
	    return result;
    }

	private class NewPrimitiveValueWizardPage extends WizardPage {
		
		private Text inputField;

		protected NewPrimitiveValueWizardPage() {
	        super("new instance wizard page");
        }

		public void createControl(Composite parent) {
			Composite comp = new Composite(parent, SWT.NONE);
			comp.setLayout(new GridLayout());
			
			inputField = new Text(comp, SWT.BORDER);
			inputField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			if ((type.equals(Float.class)) || (type.equals(float.class)) || (type.equals(Double.class))
			        || (type.equals(double.class))) {
				inputField.addVerifyListener(Validators.DECIMAL_VERIFIER);
			}
			setControl(comp);
        }
		
		private void persist() {
			String val = inputField.getText();
			
			if (type.equals(String.class)) {
				result = val;

			} else if (val.length() != 0) {
				if ((type.equals(Integer.class)) || (type.equals(int.class))) {
					result = Integer.parseInt(val);
				} else if ((type.equals(Float.class)) || (type.equals(float.class))) {
					result = Float.parseFloat(val);
				} else if ((type.equals(Double.class)) || (type.equals(double.class))) {
					result = Double.parseDouble(val);
				}
			}
		}
		
	}
	
}
