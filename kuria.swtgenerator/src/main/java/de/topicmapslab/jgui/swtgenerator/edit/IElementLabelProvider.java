/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit;

/**
 * An Elementprovider is used to return the label or image for an element.
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public interface IElementLabelProvider {
	
	public String getText(Object element);
	
	public String getImage(Object element);
	
}
