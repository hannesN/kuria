package de.topicmapslab.kuria.runtime.widget;

import de.topicmapslab.kuria.runtime.IPropertyBinding;

public interface IDateBinding extends IPropertyBinding {

	/** 
	 * Returns the format of the textual representation of the field.
	 * @return the textual representation of the format
	 */
	public abstract String getFormat();

}