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
package kuria.test.edit;

import kuria.test.model.NamedObject;
import kuria.test.model.Telephone;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Ignore;

import de.topicmapslab.kuria.annotation.AnnotationBindingFactory;
import de.topicmapslab.kuria.swtgenerator.WidgetGenerator;
import de.topicmapslab.kuria.swtgenerator.edit.InputMask;

/**
 * @author niederhausen
 * 
 */
@Ignore
public class InheritageEditableTest  {
	public static void main(String[] args) {
		new InheritageEditableTest().run();
	}

	/**
     * 
     */
    private void run() {
    	Display display = new Display();
		Shell shell = new Shell(display);
		init(shell);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
    	
	    	
    }

	/**
     * 
     */
    private void init(Shell shell) {
    	shell.setText("TableTest");
		shell.setSize(800, 1000);
    	shell.setLayout(new GridLayout());
    	
	    AnnotationBindingFactory fac = new AnnotationBindingFactory();
    	fac.addClass(NamedObject.class);
    	fac.addClass(Telephone.class);
    	
	    WidgetGenerator gen = new WidgetGenerator(fac.getBindingContainer());
	    InputMask im = gen.generateEditable(Telephone.class, shell);
	    im.getComposite().setLayoutData(new GridData(GridData.FILL_BOTH));
	    
	    
	    Telephone tel = new Telephone();
	    tel.setName("Milestone");
	    tel.setVendor("Motorola");
	    tel.setWorks(true);
	    tel.setSerialNumber("abc-123-hubididup");
	    
	    im.setModel(tel);
    }
}
