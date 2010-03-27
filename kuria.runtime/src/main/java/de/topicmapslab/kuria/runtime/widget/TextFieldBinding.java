package de.topicmapslab.kuria.runtime.widget;

import java.util.regex.Pattern;

import de.topicmapslab.kuria.runtime.PropertyBinding;

/**
 * The {@link TextFieldBinding} binds a property to a text field.
 * <p> The text field may have a sort of verification to make sure that 
 * only correct values are stored. These can be done in two ways:
 * <ul>
 * <li> Verifiy every key pressed if its valid, e.g. if a integer value is expected every letter is false</li>
 * <li> When the new value is stored by setValue a test with a regular expression if the value is valid</li>
 * </ul>
 * 
 * The regular expression can be set in the binding. See {@link Pattern} for the syntax.
 * </p>
 * <p>Additionally the textfield can be set to a password textfield, which does not show its content.</p>
 * <p>If a larger text field is needed, set the rows attribute to the needed value.</p> 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class TextFieldBinding extends PropertyBinding implements ITextFieldBinding {

	private String regExp;
	private boolean password;
	private int rows = 1;
	private boolean grabVerticalSpace = false;
	
	/**
	 * Sets the number of rows for the needed textfield (default is 1)
	 * @param rows number of rows, must be >0
	 */
	public void setRows(int rows) {
		if (rows<1)
			this.rows=1;
		else
			this.rows = rows;
    }
	
	/**
	 *  {@inheritDoc}
	 */
	public int getRows() {
	    return rows;
    }
	
	/**
	 * Sets the regular expression for validation.
	 * See {@link Pattern} for Syntax.
	 * @param regExp
	 */
	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public String getRegExp() {
		return regExp;
	}
	
	/**
	 * Set if the textfield is a password field
	 * @param password <code>true</code> or <code>false</code>
	 */
	public void setPassword(boolean password) {
	    this.password = password;
    }
	
	/**
	 *  {@inheritDoc}
	 */
	public boolean isPassword() {
	    return password;
    }
	
	/**
	 * Sets the flag if remaining vertical space should be used for the text field.
	 * 
	 * @param grabVerticalSpace hte value of the flag
	 */
	public void setGrabVerticalSpace(boolean grabVertivalSpace) {
	    this.grabVerticalSpace = grabVertivalSpace;
    }
	
	/**
	 *  {@inheritDoc}
	 */
	public boolean isGrabVerticalSpace() {
	    return grabVerticalSpace;
    }
}
