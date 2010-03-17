/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.util;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.topicmapslab.kuria.runtime.BindingContainer;
import de.topicmapslab.kuria.runtime.TextBinding;

public class TextBindingLabelProvider extends LabelProvider {

	/**
     * 
     */
	private final BindingContainer bindingContainer;

	public TextBindingLabelProvider(BindingContainer bindingContainer) {
		super();
		this.bindingContainer = bindingContainer;
	}

	@Override
	public String getText(Object element) {
		TextBinding tb = this.bindingContainer.getTextBinding(element.getClass());
		if (tb != null)
			try {
				return tb.getText(element);
			} catch (Exception e) {
			}

		return element.toString();
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

}