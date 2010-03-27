/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.PropertyBinding;

/**
 * An abstract class containg a method to create a label.
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public abstract class LabeledWidget extends AbstractWidget {

	public LabeledWidget(IPropertyBinding propertyBinding) {
	    super(propertyBinding);
    }

	/**
	 * Cretaes a label using the label value of the {@link PropertyBinding}.
	 * @param parent the parent widget of the label
	 * @return the newly created label. The text of the label is the label value of the binding + ":"
	 */
	protected Label createLabel(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(propertyBinding.getLabel() + ":");
		return label;
	}
}
