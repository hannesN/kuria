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
package de.topicmapslab.kuria.swtgenerator.edit;

import org.eclipse.swt.widgets.Composite;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.swtgenerator.AbstractSWTGenerator;

/**
 * @author Hannes Niederhausen
 *
 */
public class EditableGenerator extends AbstractSWTGenerator {

	public EditableGenerator(IBindingContainer bindingContainer) {
	    super(bindingContainer);
    }

	public InputMask generateInputMask(Class<?> clazz, Composite parent, int style) {
	    return new InputMask(parent, style, clazz, bindingContainer);
    }

}
