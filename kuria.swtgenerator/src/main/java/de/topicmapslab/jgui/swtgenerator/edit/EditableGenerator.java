/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit;

import org.eclipse.swt.widgets.Composite;

import de.topicmapslab.jgui.swtgenerator.AbstractSWTGenerator;
import de.topicmapslab.kuria.runtime.IBindingContainer;

/**
 * @author Hannes Niederhausen
 *
 */
public class EditableGenerator extends AbstractSWTGenerator {

	public EditableGenerator(IBindingContainer bindingContainer) {
	    super(bindingContainer);
    }

	public InputMask generateInputMask(Class<?> clazz, Composite parent) {
	    return new InputMask(parent, clazz, bindingContainer);
    }

}
