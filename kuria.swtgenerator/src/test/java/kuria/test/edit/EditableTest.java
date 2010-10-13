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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kuria.test.model.Address;
import kuria.test.model.Person;
import kuria.test.model.Pet;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
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
import de.topicmapslab.kuria.swtgenerator.edit.IContentProvider;
import de.topicmapslab.kuria.swtgenerator.edit.IInputMaskListener;
import de.topicmapslab.kuria.swtgenerator.edit.InputMask;

/**
 * @author niederhausen
 * 
 */
@Ignore
public class EditableTest implements IInputMaskListener {
	private InputMask inputMask;
	private Button button;

	public static void main(String[] args) {
		new EditableTest().run();
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
		final Set<Pet> pets = new HashSet<Pet>();
		for (int i=0; i<5; i++) {
			pets.add(createPet("Pet #"+i));
		}
		
		ArrayList<Person> p = new ArrayList<Person>();
		for (int i=0; i<2; i++) {
			p.add(createPerson("Meyer", "Hans "+i, (i+1)*2));
		}
		
		p.get(0).setAddress(createAddress());
		
		final ArrayList<Person> children = new ArrayList<Person>();
		for (int i=0; i<2; i++) {
			children.add(createPerson("Meyer", "Hans "+i+" max", (i+1)*2));
		}
		
		
		
		shell.setText("TableTest");
		shell.setSize(800, 1000);
		shell.setLayout(new GridLayout());
		Composite comp = new Composite(shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout());
		
		AnnotationBindingFactory fac = new AnnotationBindingFactory();
		fac.addClass(Person.class);
		fac.addClass(Address.class);
		fac.addClass(Pet.class);
		
		IBindingContainer bc = fac.getBindingContainer();
	
		bc.setLabelProvider(new ILabelProvider() {
			
			public String getLabel(String label) {
				if ("FavPet".equals(label))
					return "Haustier";
				return label;
			}
		});
		
		WidgetGenerator gen = new WidgetGenerator(bc);
		final TableViewer viewer = gen.generateTable(Person.class, comp);
		viewer.getTable().getParent().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		viewer.setInput(p);
		
		
		
		inputMask = gen.generateEditable(Person.class, comp);
		inputMask.addInputMaskListeners(this);
		inputMask.getComposite().setLayoutData(new GridData(GridData.FILL_BOTH));
		inputMask.setContentProvider(new IContentProvider() {
			
			public boolean hasContent(String fieldname, Object model) {
				if ("identifier".equals(fieldname))
					return false;
				return true;
			}
			
			public Object[] getElements(String fieldname, Object model) {
				if (fieldname.equals("pets"))
					return pets.toArray();
				if (fieldname.equals("children"))
					return children.toArray();
				if ( (fieldname.equals("favPet")) && (model instanceof Person) ) {
	                Set<Pet> petsList = ((Person)model).getPets();
	                if (petsList != null)
	                	return petsList.toArray();
                }
				return new Object[0];
			}
		});
		
		button = new Button(comp, SWT.PUSH);
		button.setText("Save");
		button.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unchecked")
            @Override
			public void widgetSelected(SelectionEvent arg0) {
				inputMask.persist();
				if (!((List<Person>) viewer.getInput()).contains(inputMask.getModel())) {
					((List<Person>) viewer.getInput()).add((Person) inputMask.getModel());
				}
				inputMask.setModel(null);
				viewer.refresh();
			}
		});
		
		Button button = new Button(comp, SWT.PUSH);
		button.setText("New");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				inputMask.setModel(new Person());
			}
		});
		
		button = new Button(comp, SWT.PUSH);
		button.setText("Refresh");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				((Person)inputMask.getModel()).setPets(pets);
				inputMask.refresh("pets");
				inputMask.refresh("favPet");
			}
		});
		
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			public void selectionChanged(SelectionChangedEvent event) {
				if (viewer.getSelection().isEmpty())
					inputMask.setModel(null);
					
				IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
				inputMask.setModel(sel.getFirstElement());
			}
		});
		
		
	}
	
	private Pet createPet(String name) {
		Pet pet = new Pet();
		pet.setName(name);
		return pet;
	}

	private Person createPerson(String lname, String fname, int age) {
		Person p = new Person();
		p.setFirstname(fname);
		p.setLastname(lname);
		p.setAge(age);
		return p;
	}
	
	private Address createAddress() {
		Address a = new Address();
		
		a.setStreet("MusterStreet");
		a.setNumber("14a");
		a.setCity("Muster City");
		a.setZipcode("09876");
		
		return a;
	}

	public void dirtyChanged() {
	    System.out.println("Dirty Changed: "+inputMask.isDirty());
	    boolean valid = inputMask.isValid();
		System.out.println("Valid: "+valid);
	    button.setEnabled(valid);
    }

	public void newModelElement(Object newElement) {
		System.out.println("New Model Element "+newElement);
    }
}
