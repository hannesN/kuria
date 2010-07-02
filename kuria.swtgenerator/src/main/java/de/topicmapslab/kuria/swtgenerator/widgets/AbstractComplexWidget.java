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
package de.topicmapslab.kuria.swtgenerator.widgets;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.widget.IEditableBinding;

/**
 * Abstract class for widgets with {@link IModelListener}.
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public abstract class AbstractComplexWidget<T> {

	private List<IModelListener> listeners;
	
	private IBindingContainer bindingContainer;

	private Class<T> clazz;
	
	
	/**
	 * Constructor
	 * 
     * @param bindingContainer the {@link IBindingContainer} providing the model bindings
     */
    public AbstractComplexWidget(IBindingContainer bindingContainer, Class<T> clazz) {
	    super();
	    this.bindingContainer = bindingContainer;
	    this.clazz=clazz;
    }

	

	/**
	 * Registers a listener.
	 * 
	 * @param listener the new listener
	 */
	public void  addPersistListener(IModelListener listener) {
		if (listeners==null)
			listeners = new ArrayList<IModelListener>();
			
		listeners.add(listener);
	}
	
	/**
	 * Removes the given listener. If it wasn't registered the method does nothing.
	 * 
	 * @param listener the {@link IModelListener} to remove
	 */
	public void  removePersistListener(IModelListener listener) {
		if (listeners!=null)
			listeners.remove(listener);
	}
	
	/**
	 * Returns the registered {@link IModelListener}
	 * 
     * @return the list of {@link IModelListener}
     */
    public List<IModelListener> getListeners() {
	    if (listeners==null)
	    	return Collections.emptyList();
	    
    	return listeners;
    }

    /**
     * 
     * @param event
     */
    protected void notifyPersist(PersistEvent<?> event) {
    	for (IModelListener l : getListeners()) {
    		l.modelPersisted(event);
    		if (!event.commit)
    			return;
    	}
    }
    
    /**
     * 
     * @param event
     */
    protected void notifyDelete(DeleteEvent<?> event) {
    	for (IModelListener l : getListeners()) {
    		l.modelDeleted(event);
    		if (!event.commit)
    			return;
    	}
    }
    
    /**
     * Returns the class representing the type of the edited model
     * 
     * @return the clazz 
     */
    protected Class<T> getClazz() {
	    return clazz;
    }
    
    /**
     * Returns the {@link IBindingContainer} used by the widget.
     * 
     * @return the bindingContainer the {@link IBindingContainer} of the widget
     */
    protected IBindingContainer getBindingContainer() {
	    return bindingContainer;
    }
    
    protected Object createCopy(Object src) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    	Object copy = src.getClass().getConstructor().newInstance();
    	copyFieldValues(src, copy);
    	return copy;
    }
    
    /**
     * Method which copies the values of the attributes of the source to the target
     * @param source
     * @param target
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    protected void copyFieldValues(Object source, Object target) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    	if (source.getClass()!=target.getClass())
    		throw new IllegalArgumentException("target and source need to be of same type");
    	
    	IEditableBinding editableBinding = getBindingContainer().getEditableBinding(source.getClass());
    	
    	for (IPropertyBinding pb : editableBinding.getPropertieBindings()) {
    		if (pb.getValue(source)!=null)
    			pb.setValue(target, pb.getValue(source));
    	}
    }
}
