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
package de.topicmapslab.kuria.swtgenerator.edit.dialog;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.swtgenerator.WidgetGenerator;
import de.topicmapslab.kuria.swtgenerator.edit.IContentProvider;
import de.topicmapslab.kuria.swtgenerator.edit.IInputMaskListener;
import de.topicmapslab.kuria.swtgenerator.edit.InputMask;

/**
 * @author Hannes Niederhausen
 *
 */
public class NewInstanceWizard extends Wizard {
	private NewInstanceWizardPage page;
	
	private final Class<?> modelType;
	private final IBindingContainer container;
	private final Object model;

	private IContentProvider contentProvider;
	
	public NewInstanceWizard(Class<?> modelType, IBindingContainer container) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		this(modelType, container, null);
    }
	
	public NewInstanceWizard(Class<?> modelType, IBindingContainer container, IContentProvider provider) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		model = modelType.getConstructor().newInstance();
		this.modelType = modelType;
		this.container = container;
		this.contentProvider = provider;
    }
	
	@Override
	public boolean performFinish() {
		page.persist();
		return true;
	}
	
	@Override
	public void addPages() {
	    super.addPages();
	    page = new NewInstanceWizardPage();
	    addPage(page);
	}
	
	public Object getModel() {
	    return model;
    }
	
	public void setContentProvider(IContentProvider provider) {
		
	}

	private class NewInstanceWizardPage extends WizardPage implements IInputMaskListener {
		private InputMask mask;
		
		protected NewInstanceWizardPage() {
	        super("new instance wizard page");
        }

		public void createControl(Composite parent) {
			Composite comp = new Composite(parent, SWT.NONE);
			comp.setLayout(new GridLayout());
			
			WidgetGenerator gen = new WidgetGenerator(container);
			mask = gen.generateEditable(modelType, comp);
			mask.setModel(model);
			mask.getComposite().setLayoutData(new GridData(GridData.FILL_BOTH));
			mask.setContentProvider(contentProvider);
			mask.addInputMaskListeners(this);
			setControl(comp);
			setPageComplete(false);
        }
		
		private void persist() {
			mask.persist();
		}

		/**
         * {@inheritDoc}
         */
        public void dirtyChanged() {
	        setPageComplete(mask.isValid());
        }

		/**
         * {@inheritDoc}
         */
        public void newModelElement(Object newElement) {
        }
	
	}
	
}
