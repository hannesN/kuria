package de.topicmapslab.jgui.swtgenerator;

import de.topicmapslab.kuria.runtime.IBindingContainer;

/**
 * 
 * @author Hannes Niederhausen
 *
 */
public abstract class AbstractSWTGenerator {

	protected final IBindingContainer bindingContainer;

	public AbstractSWTGenerator(IBindingContainer bindingContainer) {
		super();
		this.bindingContainer = bindingContainer;
	}

}