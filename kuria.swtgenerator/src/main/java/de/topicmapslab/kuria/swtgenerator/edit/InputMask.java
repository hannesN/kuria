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
package de.topicmapslab.kuria.swtgenerator.edit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.widget.ICheckBinding;
import de.topicmapslab.kuria.runtime.widget.IComboBinding;
import de.topicmapslab.kuria.runtime.widget.IDateBinding;
import de.topicmapslab.kuria.runtime.widget.IDirectoryBinding;
import de.topicmapslab.kuria.runtime.widget.IEditableBinding;
import de.topicmapslab.kuria.runtime.widget.IFileBinding;
import de.topicmapslab.kuria.runtime.widget.IGroupBinding;
import de.topicmapslab.kuria.runtime.widget.IListBinding;
import de.topicmapslab.kuria.runtime.widget.ITextFieldBinding;
import de.topicmapslab.kuria.runtime.widget.ListStyle;
import de.topicmapslab.kuria.swtgenerator.edit.widgets.CheckWidget;
import de.topicmapslab.kuria.swtgenerator.edit.widgets.ComboWidget;
import de.topicmapslab.kuria.swtgenerator.edit.widgets.CompactListWidget;
import de.topicmapslab.kuria.swtgenerator.edit.widgets.DateWidget;
import de.topicmapslab.kuria.swtgenerator.edit.widgets.DirectoryWidget;
import de.topicmapslab.kuria.swtgenerator.edit.widgets.FileWidget;
import de.topicmapslab.kuria.swtgenerator.edit.widgets.GroupWidget;
import de.topicmapslab.kuria.swtgenerator.edit.widgets.IInputMaskWidget;
import de.topicmapslab.kuria.swtgenerator.edit.widgets.IStateListener;
import de.topicmapslab.kuria.swtgenerator.edit.widgets.TableSelectionWidget;
import de.topicmapslab.kuria.swtgenerator.edit.widgets.TextFieldWidget;

/**
 * An InputMask is a wrapper of the control containing the widgets and labels
 * for a specific model type.
 * 
 * @author Hannes Niederhausen
 * 
 */
public class InputMask implements IStateListener {

	private ScrolledComposite composite;

	private final Class<?> clazz;

	private Object model;

	private Map<IPropertyBinding, IInputMaskWidget> widgetMap;
	
	private Map<IPropertyBinding, Boolean> dirtyMap;
	
	private List<IInputMaskListener> inputMaskListeners;

	private final IBindingContainer bindingContainer;

	private IContentProvider contentProvider;
	
	private Map<IInputMaskWidget, String> errorMessages;

	private Composite container;
	
	public InputMask(Composite parent, int style, Class<?> clazz, IBindingContainer container) {
		super();
		this.clazz = clazz;
		this.bindingContainer = container;
		if (container.getEditableBinding(clazz) == null)
			throw new IllegalArgumentException("Class " + clazz.getName() + " has no EdiatableBinding");
		createControl(parent, style);
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
	
	/**
	 * Returns the container to add more widgets
	 * 
     * @return the container
     */
    public Composite getContainer() {
	    return container;
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
        } else {
        	putErrorMessage(w, w.getErrorMessage());
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
		List<String> msgs = new ArrayList<String>(getErrorMessagesMap().values());
		Collections.sort(msgs);
		return msgs;
	}

	private void clearAndDisable() {
		for (IInputMaskWidget w : getWidgetMap().values()) {
			w.setEnabled(false);
			w.setModel(null);
		}
	}

	private void createControl(Composite parent, int style) {
		composite = new ScrolledComposite(parent, SWT.V_SCROLL|SWT.H_SCROLL);
		container = new Composite(composite, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		composite.setContent(container);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);
		
		
		composite.addListener(SWT.Resize, new Listener() {
			
			public void handleEvent(Event event) {
				computeSize();
			}
		});
		
		IEditableBinding eb = bindingContainer.getEditableBinding(clazz);

		for (IPropertyBinding pb : eb.getPropertieBindings()) {
			if (pb instanceof ITextFieldBinding) {
				createTextField(container, (ITextFieldBinding) pb);
			} else if (pb instanceof IComboBinding) {
				createCombo(container, (IComboBinding) pb);
			} else if (pb instanceof IGroupBinding) {
				createGroup(container, (IGroupBinding) pb);
			} else if (pb instanceof ICheckBinding) {
				createCheck(container, (ICheckBinding) pb);
			} else if (pb instanceof IListBinding) {
				createList(container, (IListBinding) pb);
			} else if (pb instanceof IDateBinding) {
				createDate(container, (IDateBinding) pb);
			} else if (pb instanceof IDirectoryBinding) {
				createDirectory(container, (IDirectoryBinding) pb);
			} else if (pb instanceof IFileBinding) {
				createFile(container, (IFileBinding) pb);
			}
		}
		
		for (IInputMaskWidget w : widgetMap.values()) {
			w.addStateListener(this);
		}
		dirtyMap = new HashMap<IPropertyBinding, Boolean>();
		clearAndDisable();
	}

	/**
     * 
     */
    private void computeSize() {
	    Point size = container.computeSize(-1, -1);
	    composite.setMinSize(size);
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
	
	private void createDirectory(Composite parent, final IDirectoryBinding pb) {
		DirectoryWidget w = new DirectoryWidget(pb);
		w.createControl(parent);
		putToWidgetMap(pb, w);
	}
	
	private void createFile(Composite parent, final IFileBinding pb) {
		FileWidget w = new FileWidget(pb);
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
		
		if (msg==null) { 
			if (errorMessages.containsKey(w)) {
				errorMessages.remove(w);
			}
			return;
		} 
		String m = w.getPropertyBinding().getLabel() + ": " + msg;
		errorMessages.put(w, m);
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