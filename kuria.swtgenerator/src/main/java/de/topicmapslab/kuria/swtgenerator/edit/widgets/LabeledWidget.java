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
package de.topicmapslab.kuria.swtgenerator.edit.widgets;

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
	 * Creates a label using the label value of the {@link PropertyBinding}.
	 * @param parent the parent widget of the label
	 * @return the newly created label. The text of the label is the label value of the binding + ":"
	 */
	protected Label createLabel(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(propertyBinding.getLabel() + ":");
		return label;
	}
}
