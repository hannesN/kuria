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
 * a list of {@link ChildrenBinding}s. The {@link ChildrenBinding} represent the
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
public class TreeNodeBinding {

	/** path to the image used for this node */
	private String image;

	/** the list of children bindings */
	private List<ChildrenBinding> children;

	/**
	 * Returns the list of {@link ChildrenBinding}
	 * @return the list of children of this TreeNode
	 * <p>This is an unmodifiable list, if you want to modify the list use the methods
	 * {@link #addChild(ChildrenBinding)} and {@link #removeChild(ChildrenBinding)}.</p>
	 */
	public List<ChildrenBinding> getChildren() {
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
	 * Returns the path of the image
	 * @return the path of the image or <code>null</code> if non is set.
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Adds a {@link ChildrenBinding} to this {@link TreeNodeBinding}.
	 * <p>A {@link ChildrenBinding} can be added more than once</p>
	 * 
	 * @param cb the new {@link ChildrenBinding}
	 */
	public void addChild(ChildrenBinding cb) {
		if (children == null) {
			children = new ArrayList<ChildrenBinding>();
		}
		children.add(cb);
	}

	/**
	 * Removes a {@link ChildrenBinding} from the list.
	 * <p>if the given {@link ChildrenBinding} is in the list more than one times 
	 * only the first occurrence will be removed.</p>
	 * <p> If the given {@link ChildrenBinding} is not in the list it is jsut ignored.</p>
	 * @param cb the {@link ChildrenBinding} to remove.
	 */
	public void removeChild(ChildrenBinding cb) {
		if (children != null) {
			children.remove(cb);
		}
	}

}
