/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.topicmapslab.jgui.swtgenerator.edit.widgets.CheckWidget;
import de.topicmapslab.jgui.swtgenerator.edit.widgets.ComboWidget;
import de.topicmapslab.jgui.swtgenerator.edit.widgets.CompactListWidget;
import de.topicmapslab.jgui.swtgenerator.edit.widgets.DateWidget;
import de.topicmapslab.jgui.swtgenerator.edit.widgets.GroupWidget;
import de.topicmapslab.jgui.swtgenerator.edit.widgets.IInputMaskWidget;
import de.topicmapslab.jgui.swtgenerator.edit.widgets.IStateListener;
import de.topicmapslab.jgui.swtgenerator.edit.widgets.TableSelectionWidget;
import de.topicmapslab.jgui.swtgenerator.edit.widgets.TextFieldWidget;
import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.widget.ICheckBinding;
import de.topicmapslab.kuria.runtime.widget.IComboBinding;
import de.topicmapslab.kuria.runtime.widget.IDateBinding;
import de.topicmapslab.kuria.runtime.widget.IEditableBinding;
import de.topicmapslab.kuria.runtime.widget.IGroupBinding;
import de.topicmapslab.kuria.runtime.widget.IListBinding;
import de.topicmapslab.kuria.runtime.widget.ITextFieldBinding;
import de.topicmapslab.kuria.runtime.widget.ListStyle;

/**
 * An InputMask is a wrapper of the control containing the widgets and labels
 * for a specific model type.
 * 
 * @author Hannes Niederhausen
 * 
 */
public class InputMask implements IStateListener {

	private Composite composite;

	private final Class<?> clazz;

	private Object model;

	private Map<IPropertyBinding, IInputMaskWidget> widgetMap;
	
	private Map<IPropertyBinding, Boolean> dirtyMap;
	
	private List<IInputMaskListener> inputMaskListeners;

	private final IBindingContainer bindingContainer;

	private IContentProvider contentProvider;
	
	private Map<IInputMaskWidget, String> errorMessages;
	
	public InputMask(Composite parent, Class<?> clazz, IBindingContainer container) {
		super();
		this.clazz = clazz;
		this.bindingContainer = container;
		if (container.getEditableBinding(clazz) == null)
			throw new IllegalArgumentException("Class " + clazz.getName() + " has no EdiatableBinding");
		createControl(parent);
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		
		if ((model!=null) && (!model.getClass().equals(clazz)))
			throw new IllegalArgumentException("Model needs to be instance of " + clazz.getName());
		
		this.model = model;
		
		for (IInputMaskWidget w : getWidgetMap().values()) {
			w.setModel(model);
		}
		
		if (model == null) {
			clearAndDisable();
			return;
		}
		
		
		setEnabled(true);
	}

	public void setContentProvider(IContentProvider contentProvider) {
		this.contentProvider = contentProvider;
		for (IInputMaskWidget w : getWidgetMap().values()) {
			w.setContentProvider(contentProvider);
		}
	}

	public IContentProvider getContentProvider() {
		return contentProvider;
	}

	public Composite getComposite() {
		return composite;
	}

	public void persist() {
		for (IInputMaskWidget w : getWidgetMap().values()) {
			w.persist();
		}
	}

	public void refresh(String fieldname) {
    	for (IInputMaskWidget w : getWidgetMap().values()) {
    		if (w.getPropertyBinding().getFieldName().equals(fieldname)) {
    			w.refresh();
    			return;
    		}
    	}
    }

	public void refresh() {
    	for (IInputMaskWidget w : getWidgetMap().values()) {
    		w.refresh();
    	}
    }

	public void stateChanged(IPropertyBinding property, boolean state) {
        if (state) {
        	dirtyMap.put(property, state);
        } else {
        	dirtyMap.remove(property);
        }
        // checking validity
        IInputMaskWidget w = widgetMap.get(property);
        if (w.isValid()) {
        	putErrorMessage(w, null);
        } else {// TODO get error message from widget
        	putErrorMessage(w, "Invalid Value");
        }
        notifyDirtyChanged();
    }

	public boolean isDirty() {
    	return dirtyMap.size()>0;
    }

	public List<IInputMaskListener> getInputMaskListeners() {
    	if (inputMaskListeners==null)
    		return Collections.emptyList();
        return inputMaskListeners;
    }

	public void addInputMaskListeners(IInputMaskListener listener) {
    	if (inputMaskListeners==null)
    		inputMaskListeners = new ArrayList<IInputMaskListener>();
    	inputMaskListeners.add(listener);
    }

	public void removeInputMaskListeners(IInputMaskListener listener) {
    	if (getInputMaskListeners().contains(listener))
    		inputMaskListeners.remove(listener);
    }

	public void setEnabled(boolean enabled) {
    	composite.setEnabled(enabled);
		for (IInputMaskWidget w : getWidgetMap().values()) {
    		w.setEnabled(enabled);
    	}
    }
	
	public boolean isValid() {
		return getErrorMessagesMap().isEmpty();
	}
	
	public Collection<String> getErrorMessages() {
		return getErrorMessagesMap().values();
	}

	private void clearAndDisable() {
		for (IInputMaskWidget w : getWidgetMap().values()) {
			w.setEnabled(false);
			w.setModel(null);
		}
	}

	private void createControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));

		IEditableBinding eb = bindingContainer.getEditableBinding(clazz);

		for (IPropertyBinding pb : eb.getPropertieBindings()) {
			if (pb instanceof ITextFieldBinding) {
				createTextField(composite, (ITextFieldBinding) pb);
			} else if (pb instanceof IComboBinding) {
				createCombo(composite, (IComboBinding) pb);
			} else if (pb instanceof IGroupBinding) {
				createGroup(composite, (IGroupBinding) pb);
			} else if (pb instanceof ICheckBinding) {
				createCheck(composite, (ICheckBinding) pb);
			} else if (pb instanceof IListBinding) {
				createList(composite, (IListBinding) pb);
			} else if (pb instanceof IDateBinding) {
				createDate(composite, (IDateBinding) pb);
			}

		}
		
		for (IInputMaskWidget w : widgetMap.values()) {
			w.addStateListener(this);
		}
		dirtyMap = new HashMap<IPropertyBinding, Boolean>();
		clearAndDisable();
	}

	private void createList(Composite parent, final IListBinding pb) {
		IInputMaskWidget w;
		if (pb.getListStyle()==ListStyle.COMPACT) {
			w = new CompactListWidget(pb, bindingContainer);
		} else if (pb.getListStyle()==ListStyle.TABLE) {
			w = new TableSelectionWidget(pb, bindingContainer);			
		} else {
			return;
		}
		w.createControl(parent);
		putToWidgetMap(pb, w);
	}

	private void createCombo(Composite parent, final IComboBinding pb) {
		ComboWidget w = new ComboWidget(pb, bindingContainer);
		w.createControl(parent);
		putToWidgetMap(pb, w);
	}
	
	private void createDate(Composite parent, final IDateBinding pb) {
		DateWidget w = new DateWidget(pb);
		w.createControl(parent);
		putToWidgetMap(pb, w);
	}

	private void createGroup(Composite parent, IGroupBinding pb) {
		GroupWidget w = new GroupWidget(pb, bindingContainer);
		// TODO ContentProvider
		w.createControl(parent);
		putToWidgetMap(pb, w);
	}

	private void createCheck(Composite parent, ICheckBinding cb) {
		CheckWidget w = new CheckWidget(cb);
		w.createControl(parent);
		putToWidgetMap(cb, w);
	}

	private void createTextField(Composite parent, ITextFieldBinding pb) {
		TextFieldWidget w = new TextFieldWidget(pb);
		w.createControl(parent);
		putToWidgetMap(pb, w);
	}

	private Map<IPropertyBinding, IInputMaskWidget> getWidgetMap() {
		if (widgetMap == null)
			return Collections.emptyMap();
		return widgetMap;
	}
	
	private Map<IInputMaskWidget, String> getErrorMessagesMap() {
		if (errorMessages==null)
			return Collections.emptyMap();
		return errorMessages;
	}

	private void putErrorMessage(IInputMaskWidget w, String msg) {
		if (errorMessages==null) {
			errorMessages = new HashMap<IInputMaskWidget, String>();
		}
		
		if ( (msg==null) && (errorMessages.containsKey(w)) ) {
			errorMessages.remove(w);
			return;
		} 
		
		errorMessages.put(w, msg);
	}

	private void putToWidgetMap(IPropertyBinding binding, IInputMaskWidget widget) {
		if (widgetMap == null)
			widgetMap = new HashMap<IPropertyBinding, IInputMaskWidget>();
		widgetMap.put(binding, widget);
	}

	private void notifyDirtyChanged() {
		for (IInputMaskListener l : getInputMaskListeners()) {
			l.dirtyChanged();
		}
	}

	public void newModelCreated(Object newModel) {
		for (IInputMaskListener l : getInputMaskListeners()) {
			l.newModelElement(newModel);
		}
    }
	
	

}
