/**
 * 
 */
package de.topicmapslab.kuria.runtime;

/**
 * <p>Interface for the factory which creates the binding.</p>
 * 
 * <p>The binding can be created based on any meta data. </p> 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public interface IBindingFactory {
	
	/**
	 * Creates and returns an instance of {@link BindingContainer}.
	 * 
	 * @return the created instance of {@link BindingContainer}
	 */
	public BindingContainer getBindingContainer();
}
