/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.edit;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

/**
 * Class providing some default validators for SWT textfields.
 * 
 * @author Hannes Niederhausen
 *
 */
public class Validators {
	
	/**
	 * A textfield verifylistener for integers
	 */
	public static VerifyListener INT_VERIFIER = new VerifyListener() {
		public void verifyText(VerifyEvent arg0) {
			for (int i=0; i<arg0.text.length(); i++) {
				if (!Character.isDigit(arg0.text.charAt(i)))
					arg0.doit = false;
			}
		}
	}; 
	
	
	public static VerifyListener DECIMAL_VERIFIER = new VerifyListener() {
		public void verifyText(VerifyEvent arg0) {
			for (int i=0; i<arg0.text.length(); i++) {
				char charAt = arg0.text.charAt(i);
				
				if (charAt=='.') {
					if (((Text) arg0.widget).getText().indexOf('.')!=-1) {
						arg0.doit=false;
						return;
					}
				} else if (!Character.isDigit(charAt)) {
					arg0.doit = false;
					return;
				}
			}
		}
	};
	
}
