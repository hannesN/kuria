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

import de.topicmapslab.jgui.swtgenerator.WidgetGenerator;
import de.topicmapslab.jgui.swtgenerator.edit.InputMask;
import de.topicmapslab.kuria.runtime.IBindingContainer;

/**
 * @author Hannes Niederhausen
 *
 */
public class NewInstanceWizard extends Wizard {
	private NewInstanceWizardPage page;
	
	private final Class<?> modelType;
	private final IBindingContainer container;
	private final Object model;
	
	public NewInstanceWizard(Class<?> modelType, IBindingContainer container) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		model = modelType.getConstructor().newInstance();
		this.modelType = modelType;
		this.container = container;
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

	private class NewInstanceWizardPage extends WizardPage {
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
			setControl(comp);
        }
		
		private void persist() {
			mask.persist();
		}
		
	}
	
}
