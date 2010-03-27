package de.topicmapslab.kuria.runtime.widget;

import de.topicmapslab.kuria.runtime.IPropertyBinding;

public interface IComboBinding extends IPropertyBinding {

	/**
	 * Returns the value of showNewButton
	 * @return <code>true</code> if a the "new-button" should be generated, else <code>false</code>
	 */
	public abstract boolean isShowNewButton();

}