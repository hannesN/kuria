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

import java.util.Collection;

/**
 * Event which indicates that a complex widget wants to delete 
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class DeleteEvent<T> {

	public final Collection<T> modelElements;
	
	public boolean commit = true;

	/**
     * @param modelElements
     */
    public DeleteEvent(Collection<T> modelElements) {
	    super();
	    this.modelElements = modelElements;
    }
	
}
