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
	 * Returns the path of the image
	 * @return the path of the image or <code>null</code> if non is set.
	 */
	public abstract String getImage();

}