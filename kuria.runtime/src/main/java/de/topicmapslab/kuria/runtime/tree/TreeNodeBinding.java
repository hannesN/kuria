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
package de.topicmapslab.kuria.runtime.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.topicmapslab.kuria.runtime.TextBinding;

/**
 * A TreeNode Binding is used to bind a class with a Tree UI element.
 * <p>
 * Every tree node may have an image, which is actually a path to the image and
 * a list of {@link IChildrenBinding}s. The {@link IChildrenBinding} represent the
 * child nodes in a tree. The list may be empty, indicating that this node has no
 * children. Every class bound to a child node needs to be bound by a TreeNode as 
 * well.</p>
 * <p>If an image is set, it will be shown for every node, mostly on the left side
 * of the node text.</p>
 * <p>It is advised to create a {@link TextBinding} for the class as well. The {@link TextBinding}
 * is used to generate the text for the node in the tree</p>
 * 
 * @author Hannes Niederhausen 
 * @version 1.0.0
 */
public class TreeNodeBinding implements ITreeNodeBinding {

	/** path to the image used for this node */
	private String image;

	/** the list of children bindings */
	private List<IChildrenBinding> children;

	/** the image method */
	private String imageMethod;
	
	/**
	 *  {@inheritDoc}
	 */
	public List<IChildrenBinding> getChildren() {
		if (children == null)
			return Collections.emptyList();
		return Collections.unmodifiableList(children);
	}

	/**
	 * Sets the path to the image
	 * @param image the path to the image or <code>null</code> if no image is wanted
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 *  {@inheritDoc}
	 */
	public String getImage(Object instance) {
		try {
	        if (imageMethod != null) {
		        return (String) instance.getClass().getDeclaredMethod(imageMethod).invoke(instance);
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return image;
	}

	/**
	 * Adds a {@link IChildrenBinding} to this {@link ITreeNodeBinding}.
	 * <p>A {@link IChildrenBinding} can be added more than once</p>
	 * 
	 * @param cb the new {@link IChildrenBinding}
	 */
	public void addChild(IChildrenBinding cb) {
		if (children == null) {
			children = new ArrayList<IChildrenBinding>();
		}
		children.add(cb);
	}

	/**
	 * Removes a {@link IChildrenBinding} from the list.
	 * <p>if the given {@link IChildrenBinding} is in the list more than one times 
	 * only the first occurrence will be removed.</p>
	 * <p> If the given {@link IChildrenBinding} is not in the list it is jsut ignored.</p>
	 * @param cb the {@link IChildrenBinding} to remove.
	 */
	public void removeChild(IChildrenBinding cb) {
		if (children != null) {
			children.remove(cb);
		}
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public String getImageMethod() {
	    return imageMethod;
    }
	
	/**
	 * Sets the image method name
	 * @param imageMethod the name of the image method
	 */
	public void setImageMethod(String imageMethod) {
	    this.imageMethod = imageMethod;
    }

}
