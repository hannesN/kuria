/**
 * 
 */
package de.topicmapslab.kuria.annotation.tree;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>The TreeNode annotation indicates that instances of this type may 
 * be used as tree nodes. The image attribute is a filename to an image
 * which is used in the tree.</p>
 * 
 *  <p>A {@link TreeNode} may have attributes annotated with {@link Children}, 
 *  which indicates the child nodes of this node.</p>
 *  
 *  <p>The text of this node is either the result of toString or the value of an
 *  {@link Text} annotation. You must {@link Text} only use once.</p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TreeNode {

	/** 
	 * path to the image of the node 
	 */
	String image() default "";
}
