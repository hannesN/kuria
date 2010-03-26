/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit.widgets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import de.topicmapslab.jgui.swtgenerator.edit.dialog.NewInstanceWizard;
import de.topicmapslab.jgui.swtgenerator.edit.dialog.NewPrimitiveValueWizard;
import de.topicmapslab.kuria.runtime.BindingContainer;
import de.topicmapslab.kuria.runtime.PropertyBinding;
import de.topicmapslab.kuria.runtime.TextBinding;
import de.topicmapslab.kuria.runtime.util.TypeUtil;
import de.topicmapslab.kuria.runtime.widget.ComboBinding;

/**
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class ComboWidget extends LabeledWidget {

	private BindingContainer bindingContainer;
	private Button newButton;
	private Combo combo;
	
	private List<Object>  comboValues;

	public ComboWidget(PropertyBinding propertyBinding, BindingContainer bindingContainer) {
		super(propertyBinding);
		if (!(propertyBinding instanceof ComboBinding))
			throw new InvalidParameterException("Invalid binding:" + propertyBinding.getClass().getName());
		this.bindingContainer = bindingContainer;
	}

	/**
	 * {@inheritDoc}
	 */
	public void createControl(Composite parent) {
		if (!(parent.getLayout() instanceof GridLayout))
			throw new IllegalArgumentException("Parents layout need to be a GridLayout");

		GridLayout layout = (GridLayout) parent.getLayout();
		
		createLabel(parent);
		combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (getModel()==null)
					return;
				try {
	                Object val = getPropertyBinding().getValue(getModel());
	                
	                int idx = combo.getSelectionIndex();
	                if ((idx==-1) && (val==null))
	                	notifyStateListener(false);
	                else {
	                	if (val!=null)
	                		notifyStateListener(!val.equals(getCurrentSelection(idx)));
	                	else
	                		notifyStateListener(true);
	                }
                } catch (Exception e) {
                	throw new RuntimeException(e);
                }
			}
		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = layout.numColumns-1;

		if (getPropertyBinding().isShowNewButton()) {
			gd.horizontalSpan--;
			newButton = new Button(parent, SWT.PUSH);
			newButton.setText("New...");
			GridData gridData = new GridData();
			newButton.setLayoutData(gridData);
			hookButtonListener();

		}

		combo.setLayoutData(gd);

	}

	private void hookButtonListener() {
	    newButton.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent arg0) {
	    		try {
	    			Shell shell = ((Control) arg0.widget).getShell();
	    			Type type = getPropertyBinding().getType();
	    	        if ((String.class.equals(type)) || (TypeUtil.isPrimitive(type))) {
	    	            NewPrimitiveValueWizard wzrd = new NewPrimitiveValueWizard((Class<?>) type);
	    	            WizardDialog dlg = new WizardDialog(shell, wzrd);
	    	            wzrd.setWindowTitle("New "+getPropertyBinding().getLabel()+"...");
	    	            if (dlg.open()==Dialog.OK) {
	    	            	addToComboObjects(wzrd.getResult());
	    	            	notifyNewModelListener(wzrd.getResult());
	    	            }
	    	            return;
	    	        }
	    	        
	    	        if (getBindingContainer().getEditableBinding((Class<?>) type)!=null) {
	    	        	NewInstanceWizard wzrd = new NewInstanceWizard((Class<?>) type, getBindingContainer());
	    	        	WizardDialog dlg = new WizardDialog(shell, wzrd);
	    	            wzrd.setWindowTitle("New "+getPropertyBinding().getLabel()+"...");
	    	            if (dlg.open()==Dialog.OK) {
	    	            	addToComboObjects(wzrd.getModel());
	    	            	notifyNewModelListener(wzrd.getModel());
	    	            }
	    	        }
	    			
	    		} catch (Exception e) {
	    			throw new RuntimeException(e);
	    		}
	    	}
	    });
    }

	/**
	 * {@inheritDoc}
	 */
	public void refresh() {
		combo.removeAll();
		if (getModel()==null)
			return;
		try {
    		ComboBinding pb = getPropertyBinding();
			fillCombo();
    		int index = indexOfComboElement(pb.getValue(getModel()));
    		if (index >= 0)
    			combo.select(index);
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void setModel(Object model) {
	    super.setModel(model);
	    comboValues = null;
	}
	
	private void addToComboObjects(Object o) {
		try {
	        if (comboValues == null)
		    comboValues = new ArrayList<Object>();
	        comboValues.add(o);
	        addComboItem(o);
	        combo.select(comboValues.indexOf(o));
        } catch (Exception e) {
	        throw new RuntimeException(e);
        }
	}
	
	public List<Object> getComboValues() {
		if (comboValues==null)
			return Collections.emptyList();
		return comboValues;
    }
		
	private void fillCombo() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		if (!getContentProvider().hasContent(getPropertyBinding().getFieldName(), getModel()))
			return;
		
		for (Object o : getContentProvider().getElements(getPropertyBinding().getFieldName(), getModel())) {
			addComboItem(o);
		}
	}

	private void addComboItem(Object o) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
	    if (o instanceof String) {
	    	combo.add((String) o);
	    } else {
	    	TextBinding textBinding = bindingContainer.getTextBinding(o.getClass());
	    	if (textBinding != null) {
	    		combo.add(textBinding.getText(o));
	    	} else {
	    		combo.add(o.toString());
	    	}
	    }
    }


	private int indexOfComboElement(Object element) {
		String fieldName = getPropertyBinding().getFieldName();
		if ((element == null) || (!getContentProvider().hasContent(fieldName, getModel())) )
			return -1;

		Object[] elements = getContentProvider().getElements(fieldName, getModel());
		for (int i = 0; i < elements.length; i++) {
			if (element.equals(elements[i]))
				return i;
		}

		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setEnabled(boolean enabled) {
		combo.setEnabled(enabled);
		if (!enabled)
			combo.removeAll();
		
		if(newButton!=null)
			newButton.setEnabled(enabled);
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	public void persist() {
		try {
			if (!isDirty())
				return;
			
			int idx = combo.getSelectionIndex();
			if (idx > -1) {
				Object val = getCurrentSelection(idx);
				getPropertyBinding().setValue(getModel(), val);
			}
			notifyStateListener(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object getCurrentSelection(int idx) {
	    Object val =null;
	    Object[] elements = getContentProvider().getElements(getPropertyBinding().getFieldName(), getModel());
	    if (idx<elements.length) {
	    	val = elements[idx];
	    } else {
	    	idx-=elements.length;
	    	if (getComboValues().size()<idx)
	    		val = getComboValues().get(idx);
	    }
	    return val;
    }

	private BindingContainer getBindingContainer() {
	    return bindingContainer;
    }
	
	@Override
	public ComboBinding getPropertyBinding() {
		return (ComboBinding) super.getPropertyBinding();
	}
	
	@Override
	public boolean isValid() {
		if (!isOptional())
			return combo.getSelectionIndex()>-1;
			
	    return super.isValid();
	}
}
