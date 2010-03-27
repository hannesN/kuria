package de.topicmapslab.kuria.runtime.widget;

public interface ITextFieldBinding {

	/**
	 * Returns the number of rows.
	 * @return
	 */
	public abstract int getRows();

	/**
	 * Returns the validation regular expression
	 * @return the current expression or <code>null</code> if no validation is needed
	 */
	public abstract String getRegExp();

	/**
	 * Returns the value of the password flag
	 * @return <code>true</code> if the text field should be a password field, <code>false</code> else
	 */
	public abstract boolean isPassword();

	/**
	 * Returns the value of the flag
	 * @return the vlue of the flag
	 */
	public abstract boolean isGrabVerticalSpace();

}