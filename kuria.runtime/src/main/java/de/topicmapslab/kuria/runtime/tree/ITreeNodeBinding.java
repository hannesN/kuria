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

import java.util.List;

public interface ITreeNodeBinding {

	/**
	 * Returns the list of {@link IChildrenBinding}
	 * @return the list of children of this TreeNode
	 * <p>This is an unmodifiable list</p>
	 */
	public abstract List<IChildrenBinding> getChildren();

	/**
	 * Returns the path of the image for the given instance
	 * 
	 * @param instance the instance which image path should be returned
	 * @return the path of the image or <code>null</code> if non is set.
	 */
	public String getImage(Object instance);
	
	/**
	 * To provide a dynamic way of setting the image, it is possible to implement a method which returns the imagepath.
	 * The name of the method is returned by this method or <code>null</code> if method exists.
	 * 
	 * @return the name of the method to retrieve a column image or <code>null</code>
	 */
	public abstract String getImageMethod();
	
}
