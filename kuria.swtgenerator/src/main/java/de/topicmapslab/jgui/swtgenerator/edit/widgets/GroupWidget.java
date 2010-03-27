/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import de.topicmapslab.jgui.swtgenerator.WidgetGenerator;
import de.topicmapslab.jgui.swtgenerator.edit.IContentProvider;
import de.topicmapslab.jgui.swtgenerator.edit.IInputMaskListener;
import de.topicmapslab.jgui.swtgenerator.edit.InputMask;
import de.topicmapslab.kuria.runtime.BindingContainer;
import de.topicmapslab.kuria.runtime.PropertyBinding;

/**
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class GroupWidget extends AbstractWidget implements IInputMaskListener {
	private final BindingContainer bindingContainer;
	private InputMask inputMask;

	public GroupWidget(PropertyBinding propertyBinding, BindingContainer bindingContainer) {
		super(propertyBinding);
		this.bindingContainer = bindingContainer;
	}

	/**
	 * {@inheritDoc}
	 */
	public void createControl(Composite parent) {
		Group g = new Group(parent, SWT.BORDER);
		g.setText(getPropertyBinding().getLabel());
		g.setLayout(new FillLayout());
		WidgetGenerator gen = new WidgetGenerator(bindingContainer);
		inputMask = gen.generateEditable((Class<?>) getPropertyBinding().getType(), g);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ((GridLayout) parent.getLayout()).numColumns;
		g.setLayoutData(gd);

		inputMask.setContentProvider(getContentProvider());
		inputMask.addInputMaskListeners(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void persist() {
		try {
			if (!isDirty())
				return;
			
	        inputMask.persist();
	        
	        getPropertyBinding().setValue(getModel(), inputMask.getModel());
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
	}

	/**
	 * {@inheritDoc}
	 */
	public void refresh() {
		inputMask.refresh();
	}

	@Override
	public void setContentProvider(IContentProvider provider) {
		super.setContentProvider(provider);
		if (inputMask!=null)
			inputMask.setContentProvider(provider);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setEnabled(boolean enabled) {
		inputMask.setEnabled(enabled);
	}
	
	@Override
	public void setModel(Object model) {
	    super.setModel(model);
	    Object maskModel = null;
		try {
			if (model != null) {
				maskModel = getPropertyBinding().getValue(model);
			 
				if (maskModel == null) {
					// create new instance of nested property
					Class<?> type = (Class<?>) getPropertyBinding().getType();
					maskModel = type.getConstructor().newInstance();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	    inputMask.setModel(maskModel);
	}

	public void dirtyChanged() {
	   notifyStateListener(inputMask.isDirty()); 
    }

	public void newModelElement(Object newElement) {
	    notifyNewModelListener(newElement);	    
    }

	@Override
	public boolean isValid() {
		return inputMask.isValid();
	}
	
}
