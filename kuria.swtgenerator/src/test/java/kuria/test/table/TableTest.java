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
package kuria.test.table;

import java.util.ArrayList;

import kuria.test.model.Address;
import kuria.test.model.Person;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.Ignore;

import de.topicmapslab.kuria.annotation.AnnotationBindingFactory;
import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.swtgenerator.WidgetGenerator;

/**
 * @author Hannes Niederhausen
 * 
 */
@Ignore
public class TableTest {

	public static void main(String[] args) {
		new TableTest().run();
	}

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

	private void init(Shell shell) {
		ArrayList<Person> p = new ArrayList<Person>();
		for (int i=0; i<20; i++) {
			p.add(createPerson("Meyer", "Hans "+i, (i+1)*2));
		}
		
		
		shell.setText("TableTest");
		shell.setLayout(new GridLayout());
		Composite comp = new Composite(shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout());
		
		AnnotationBindingFactory fac = new AnnotationBindingFactory();
		fac.addClass(Person.class);
		fac.addClass(Address.class);
		
		IBindingContainer bc = fac.getBindingContainer();
	
		Label l = new Label(comp, SWT.NONE);
		l.setText("Hallo!");
		
		WidgetGenerator gen = new WidgetGenerator(bc);
		TableViewer viewer = gen.generateTable(Person.class, comp);
		viewer.getTable().getParent().setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setInput(p);
	}
	
	
	private Person createPerson(String lname, String fname, int age) {
		Person p = new Person();
		p.setFirstname(fname);
		p.setLastname(lname);
		p.setAge(age);
		return p;
	}
}
