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