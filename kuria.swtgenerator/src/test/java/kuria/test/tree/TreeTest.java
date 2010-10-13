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
package kuria.test.tree;

import kuria.test.model.Address;
import kuria.test.model.Person;
import kuria.test.model.Pet;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Ignore;

import de.topicmapslab.kuria.annotation.AnnotationBindingFactory;
import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.ILabelProvider;
import de.topicmapslab.kuria.swtgenerator.WidgetGenerator;

/**
 * @author Hannes Niederhausen
 * 
 */
@Ignore
public class TreeTest {

	private Person modifiable;
	
	public static void main(String[] args) {
		new TreeTest().run();
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
		String[] numbers = {"I", "II", "III", "IV", "V"};
		
		
		Person father = new Person();
		father.setAge(45);
		father.setFirstname("Michael");
		father.setLastname("Mustermann");
		
		Person root = father;
		
		Pet pet = new Pet();
		pet.setName("Tiger");
		
		for (int i = 0; i < 5; i++) {
			Person p1 = createPerson("Haß", "Christian "+numbers[i], 32);
			p1.addChild(createPerson("Mustermann", "Harry"+numbers[i], 12));
			p1.setFavPet(pet);
			modifiable = p1;
			father.addChild(p1);
		}

		shell.setText("TableTest");
		shell.setLayout(new GridLayout());
		Composite comp = new Composite(shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(2, true));
		
		AnnotationBindingFactory fac = new AnnotationBindingFactory();
		fac.addClass(Person.class);
		fac.addClass(Address.class);
		fac.addClass(Pet.class);
		
		IBindingContainer bc = fac.getBindingContainer();
		bc.setLabelProvider(new ILabelProvider() {
			
			public String getLabel(String label) {
				if ("children".equals(label))
					return "Kinder";
				return label;
			}
		});
		
		WidgetGenerator gen = new WidgetGenerator(bc);
		final TreeViewer viewer = gen.generateTree(comp, false);
		viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setInput(root);
		viewer.expandAll();
		
		final TreeViewer viewer2 = gen.generateTree(comp, true);
		viewer2.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer2.setInput(root);
		viewer2.expandAll();
		
		Button button = new Button(comp, SWT.PUSH);
		button.setText("Modify");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				modifiable.setFirstname("Martin");
				modifiable.setLastname("Schumann");
				viewer.refresh(modifiable);
				viewer2.refresh(modifiable);
			}
		});
		
		button = new Button(comp, SWT.PUSH);
		button.setText("Refresh");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				modifiable.setFirstname("Harald");
				modifiable.setLastname("Meyer");
				viewer.refresh();
				viewer2.refresh();
			}
		});
	}
	
	
	private Person createPerson(String lname, String fname, int age) {
		Person p = new Person();
		p.setFirstname(fname);
		p.setLastname(lname);
		p.setAge(age);
		return p;
	}
}
