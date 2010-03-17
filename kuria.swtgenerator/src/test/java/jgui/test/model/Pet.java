/**
 * 
 */
package jgui.test.model;

import de.topicmapslab.kuria.annotation.Text;
import de.topicmapslab.kuria.annotation.tree.TreeNode;
import de.topicmapslab.kuria.annotation.widgets.Editable;

/**
 * @author niederhausen
 *
 */
@TreeNode(image="tiger.png")
@Editable
public class Pet {

	@Text
	private String name;
	
	public String getName() {
	    return name;
    }
	
	public void setName(String name) {
	    this.name = name;
    }

	@Override
    public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Pet [name=").append(name).append("]");
	    return builder.toString();
    }
	
}
