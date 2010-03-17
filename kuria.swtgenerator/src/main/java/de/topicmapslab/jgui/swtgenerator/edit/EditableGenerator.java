/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit;

import org.eclipse.swt.widgets.Composite;

import de.topicmapslab.jgui.swtgenerator.AbstractSWTGenerator;
import de.topicmapslab.kuria.runtime.BindingContainer;

/**
 * @author Hannes Niederhausen
 *
 */
public class EditableGenerator extends AbstractSWTGenerator {

	public EditableGenerator(BindingContainer bindingContainer) {
	    super(bindingContainer);
    }

	public InputMask generateInputMask(Class<?> clazz, Composite parent) {
	    return new InputMask(parent, clazz, bindingContainer);
    }

}
