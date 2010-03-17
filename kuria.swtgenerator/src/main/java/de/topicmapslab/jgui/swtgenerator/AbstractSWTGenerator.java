package de.topicmapslab.jgui.swtgenerator;

import de.topicmapslab.kuria.runtime.BindingContainer;

/**
 * 
 * @author Hannes Niederhausen
 *
 */
public abstract class AbstractSWTGenerator {

	protected final BindingContainer bindingContainer;

	public AbstractSWTGenerator(BindingContainer bindingContainer) {
		super();
		this.bindingContainer = bindingContainer;
	}

}