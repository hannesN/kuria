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
package de.topicmapslab.kuria.swtgenerator.widgets;

import de.topicmapslab.kuria.swtgenerator.edit.InputMask;

/**
 * The persistent event is used to encapsulate the modified model and its copy
 * containing the new values.
 * 
 * If <code>commit</code> is <code>true</code> the widget will set the new values.
 * If false the application needs to copy the values on its own. 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class PersistEvent<T extends Object> {

	/**
	 * The original unmodified model or <code>null</code> if the model is a newly created one
	 */
	public final T model;
	
	/**
	 * The copy of the model with the new values set by an {@link InputMask}
	 */
	public final T newValues;
	
	/**
	 * Flag whether the widget should modify the original model 
	 */
	public boolean commit = true;

	/**
	 * Constructor 
	 * 
     * @param model the unmodified model
     * @param newValues the modified copy
     */
    public PersistEvent(T model, T newValues) {
	    super();
	    this.model = model;
	    this.newValues = newValues;
    }

	
}
