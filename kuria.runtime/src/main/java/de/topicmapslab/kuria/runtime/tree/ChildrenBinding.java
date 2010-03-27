/**
 * 
 */
package de.topicmapslab.kuria.runtime.tree;

import de.topicmapslab.kuria.runtime.PropertyBinding;
import de.topicmapslab.kuria.runtime.table.TableBinding;

/**
 * The {@link ChildrenBinding} binds a field of a class to the classes
 * {@link ITreeNodeBinding}.
 * <p>
 * Children can be a Set or List of a specific type, an array or just a single
 * attribute. It is necessary, that the type of the field, array or collection
 * need to be bound to another {@link ITreeNodeBinding}, because a child of a
 * node needs to be rendered as node as well.
 * </p>
 * <p>
 * The {@link ChildrenBinding} has two attribute:<b>nodeTitle</b> and
 * <b>nodeImage</b>. If set, these attributes are used as mediator nod. This
 * means the children are not directly connected to the tree.
 * </p>
 * <p>
 * For instance look at the following class:
 * 
 * <pre>
 * public class Person {
 * 	List&lt;Person&gt; children;
 * 	List&lt;Person&gt; parents;
 * }
 * </pre>
 * 
 * Without a mediator node the children and parents are indistinguishable.
 * 
 * <pre>
 * root
 *   |---Name1
 *   |---Name2
 *   |---Name3
 *   |---Name4
 *   |---Name5
 * </pre>
 * 
 * With mediator node the tree could look like this:
 * 
 * <pre>
 *  root
 *   |---children
 *         |------name1
 *         |------name2
 *         |------name3
 *   |---parents
 *         |------name4
 *         |------name5
 * </pre>
 * 
 * If the {@link TableBinding} only has one {@link ChildrenBinding}, the mediator node might not be necessary.
 * If you do not want to use it, just set nodeTitle and nodeImage to <code>null</code>. 
 * </p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class ChildrenBinding extends PropertyBinding implements IChildrenBinding {

	private String nodeTitle;

	private String nodeImage;

	/**
	 *  {@inheritDoc}
	 */
	public String getNodeTitle() {
		return nodeTitle;
	}

	/**
	 * Sets the node title
	 * @param nodeTitle the text for the node or <code>null</code> if it should not be used.
	 */
	public void setNodeTitle(String nodeTitle) {
		this.nodeTitle = nodeTitle;
	}

	/**
	 *  {@inheritDoc}
	 */
	public String getNodeImage() {
		return nodeImage;
	}

	/**
	 * Sets the path to the node image
	 * @param nodeImage the path to the image for the mediator node or <code>null</code>
	 */
	public void setNodeImage(String nodeImage) {
		this.nodeImage = nodeImage;
	}

}
