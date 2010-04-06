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
