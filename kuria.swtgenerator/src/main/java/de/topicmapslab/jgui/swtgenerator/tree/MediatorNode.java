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
package de.topicmapslab.jgui.swtgenerator.tree;

public class MediatorNode {
	private Object children;

	private String title;
	private String image;
	
	private final Class<?> childType;
	
	

	public MediatorNode(Class<?> childType) {
	    super();
	    this.childType = childType;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setChildren(Object children) {
		this.children = children;
	}

	public Object getChildren() {
		return children;
	}
	
	public Class<?> getChildType() {
	    return childType;
    }
}
