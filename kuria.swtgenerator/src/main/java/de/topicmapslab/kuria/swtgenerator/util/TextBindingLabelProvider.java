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
package de.topicmapslab.kuria.swtgenerator.util;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.ITextBinding;

public class TextBindingLabelProvider extends LabelProvider {

	/**
     * 
     */
	private final IBindingContainer bindingContainer;

	public TextBindingLabelProvider(IBindingContainer bindingContainer) {
		super();
		this.bindingContainer = bindingContainer;
	}

	@Override
	public String getText(Object element) {
		ITextBinding tb = this.bindingContainer.getTextBinding(element.getClass());
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
