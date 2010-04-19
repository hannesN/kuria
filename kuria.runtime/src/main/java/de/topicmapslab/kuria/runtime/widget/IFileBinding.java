package de.topicmapslab.kuria.runtime.widget;

public interface IFileBinding {

	/**
	 * Returns the supported file extensions. These extensions have the form:
	 * <b>"*.extension"</b>. For every entry in the array the file dialog provides 
	 * a selection in the combo box. 
	 *
	 *  @return array of supported file extensions
	 */
	public abstract String[] getFileExtensions();

}