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
 * <p>This annotation is used in an class annotated with {@link TreeNode}.
 * It indicates that the annotated field represents a set of nodes which 
 * are the children in a TreeViewer. It is also possible to annotate a 
 * method which returns a collection of children.</p>
 * 
 * <p>You can use it more than one times in a class. The children collections 
 * will be merged.</p> 
 * 
 * <p>If you set the title attribute a node with this text is created which is 
 * used as mediator node between the parent node and the children. With that
 * it is possible to distinguish different child elements.</p> 
 * 
 * <p>The image indicates the image used for the mediator. Without a title 
 * attribute the icon is ignored.</p>
 * 
 * <p>The type of the children must be annotated with {@link TreeNode}.</p> 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Children {

	/**
	 * Text of the mediator node 
	 */
	String title() default "";
	
	/**
	 * Image of the mediator node
	 */
	String image() default "";
	
}
