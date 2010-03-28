package de.topicmapslab.jgui.swtgenerator.edit.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.topicmapslab.jgui.swtgenerator.edit.IContentProvider;
import de.topicmapslab.kuria.runtime.IPropertyBinding;

public abstract class AbstractWidget implements IInputMaskWidget {

	private static final IContentProvider EMPTY_CONTENTPROVIDER = new IContentProvider() {

		public boolean hasContent(String fieldname, Object model) {
			return false;
		}

		public Object[] getElements(String fieldname, Object model) {
			return new Object[0];
		}
	};

	protected final IPropertyBinding propertyBinding;
	private Object model;
	private IContentProvider provider;
	private boolean dirty = false;
	private String errorMessage;

	private List<IStateListener> listeners;

	public AbstractWidget(IPropertyBinding propertyBinding) {
		super();
		this.propertyBinding = propertyBinding;
	}

	/**
	 * {@inheritDoc}
	 */
	public IPropertyBinding getPropertyBinding() {
		return propertyBinding;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModel(Object model) {
		this.model = model;
		refresh();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getModel() {
		return model;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setContentProvider(IContentProvider provider) {
		this.provider = provider;
	}

	protected IContentProvider getContentProvider() {
		if (provider == null)
			return EMPTY_CONTENTPROVIDER;

		return provider;
	}

	public List<IStateListener> getStateListeners() {
		if (listeners == null)
			return Collections.emptyList();

		return listeners;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addStateListener(IStateListener listener) {
		if (listeners == null)
			listeners = new ArrayList<IStateListener>();
		listeners.add(listener);
	}

	public boolean isDirty() {
	    return dirty;
    }
	
	/**
	 * {@inheritDoc}
	 */
	public void removeStateListener(IStateListener listener) {
		if (getStateListeners().contains(listener))
			listeners.remove(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isOptional() {
	    return propertyBinding.isOptional();
    }
	
	/**
	 * 
	 *  {@inheritDoc}
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	protected void notifyStateListener(boolean isDirty) {
		dirty = isDirty;
		for (IStateListener l : getStateListeners()) {
			l.stateChanged(propertyBinding, isDirty);
		}
	}
	
	protected void notifyNewModelListener(Object newModel) {
		for (IStateListener l : getStateListeners()) {
			l.newModelCreated(newModel);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public boolean isValid() {
	    return true;
	}
	
}