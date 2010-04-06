/*******************************************************************************
 * Copyright 2010, Topic Maps Lab
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
