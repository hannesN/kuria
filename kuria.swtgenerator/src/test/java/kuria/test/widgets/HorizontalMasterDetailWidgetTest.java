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
package kuria.test.widgets;

import java.util.ArrayList;

import kuria.test.model.Address;
import kuria.test.model.Person;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Ignore;

import de.topicmapslab.kuria.annotation.AnnotationBindingFactory;
import de.topicmapslab.kuria.swtgenerator.widgets.MasterDetailWidget;

/**
 * @author Hannes Niederhausen
 *
 */
@Ignore
public class HorizontalMasterDetailWidgetTest {

	public static void main(String[] args) {
		new HorizontalMasterDetailWidgetTest().run();
	}
	
	private void run() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(600, 500);
		init(shell);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	/**
     * @param shell
     */
    private void init(Shell shell) {
	    shell.setLayout(new FillLayout());
	    
	    AnnotationBindingFactory fac = new AnnotationBindingFactory();
	    fac.addClass(Person.class);
	    fac.addClass(Address.class);
	    
	    
	    MasterDetailWidget<Person> mdw = new MasterDetailWidget<Person>(shell, SWT.NONE, fac.getBindingContainer(), Person.class, true);
	    mdw.setInput(new ArrayList<Person>());
    }
}
