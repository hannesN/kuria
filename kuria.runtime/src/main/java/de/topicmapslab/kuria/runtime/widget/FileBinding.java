/**
 * 
 */
package de.topicmapslab.kuria.runtime.widget;

import de.topicmapslab.kuria.runtime.PropertyBinding;

/**
 * This binding indicates that the UI should offer a way to render a
 * absolute path to a file and provide a way to edit this path.
 *   
 * This could be done via entering the text into a text field or choose
 * a new file via a standard dialog.
 * 
 * @author Hannes Niederhausen
 *
 */
public class FileBinding extends PropertyBinding implements IFileBinding {

	private String[] fileExtensions;
	
	/**
	 * The supported file extensions. These extensions must have the form:
	 * <b>"*.extension"</b>. For every entry in the array the file dialog provides 
	 * a selection in the combo box. 
	 * 
	 *  If you want to filter more than one extension at once use:
	 *  <b>"*.ext1;*.ext2"</b> as entry.
	 */
	public void setFileExtensions(String[] fileExtensions) {
	    this.fileExtensions = fileExtensions;
    }

	/**
	 * {@inheritDoc}
	 */
	public String[] getFileExtensions() {
	    return fileExtensions;
    }
	
	
}
