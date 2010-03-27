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