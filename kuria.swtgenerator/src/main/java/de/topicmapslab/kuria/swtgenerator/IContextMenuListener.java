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
package de.topicmapslab.kuria.swtgenerator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * An instance of a listener is invoked if a context menu is created for a {@link TableViewer} or {@link TreeViewer}.
 * 
 * @author Hannes Niederhausen
 *
 */
public interface IContextMenuListener {

	/**
	 * This method is called when a context menu is created.
	 * 
	 * The listener can add actions to the manager.
	 * 
	 * @param manager
	 */
	public void createMenu(IMenuManager manager);
	
}
