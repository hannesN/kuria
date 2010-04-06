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
package de.topicmapslab.kuria.runtime.tree;

import de.topicmapslab.kuria.runtime.IPropertyBinding;

public interface IChildrenBinding extends IPropertyBinding {

	/**
	 * Returns the nodeTitle 
	 * @return the nodeTitle or <code>null</code> if it is not set
	 */
	public abstract String getNodeTitle();

	/**
	 * Returns the nodeImage
	 * @return the path to the node image or <code>null</code> if not set.
	 */
	public abstract String getNodeImage();

}
