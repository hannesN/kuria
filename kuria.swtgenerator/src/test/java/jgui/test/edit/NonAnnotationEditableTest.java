/**
 * 
 */
package jgui.test.edit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jgui.test.model.Address;
import jgui.test.model.Person;
import jgui.test.model.Pet;

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

import de.topicmapslab.jgui.swtgenerator.WidgetGenerator;
import de.topicmapslab.jgui.swtgenerator.edit.IContentProvider;
import de.topicmapslab.jgui.swtgenerator.edit.InputMask;
import de.topicmapslab.kuria.runtime.GenericBindingFactory;
import de.topicmapslab.kuria.runtime.IBindingContainer;

/**
 * @author niederhausen
 * 
 */
@Ignore
public class NonAnnotationEditableTest {
	public static void main(String[] args) {
		new NonAnnotationEditableTest().run();
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
		p.get(0).setPets(pets);
		
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
		
		GenericBindingFactory fac = new GenericBindingFactory();
		fac.addClass(Person.class);
		fac.addClass(Address.class);
		fac.addClass(Pet.class);
		
		IBindingContainer bc = fac.getBindingContainer();
	
		
		WidgetGenerator gen = new WidgetGenerator(bc);
		final TableViewer viewer = gen.generateTable(Person.class, comp);
		viewer.getTable().getParent().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		viewer.setInput(p);
		
		
		
		final InputMask mask = gen.generateEditable(Person.class, comp);
		mask.getComposite().setLayoutData(new GridData(GridData.FILL_BOTH));
		mask.setContentProvider(new IContentProvider() {
			
			public boolean hasContent(String fieldname, Object model) {
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
		
		Button button = new Button(comp, SWT.PUSH);
		button.setText("Save");
		button.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unchecked")
            @Override
			public void widgetSelected(SelectionEvent arg0) {
				mask.persist();
				if (!((List<Person>) viewer.getInput()).contains(mask.getModel())) {
					((List<Person>) viewer.getInput()).add((Person) mask.getModel());
				}
				mask.setModel(null);
				viewer.refresh();
			}
		});
		
		button = new Button(comp, SWT.PUSH);
		button.setText("New");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mask.setModel(new Person());
			}
		});
		
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			public void selectionChanged(SelectionChangedEvent event) {
				if (viewer.getSelection().isEmpty())
					mask.setModel(null);
					
				IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
				mask.setModel(sel.getFirstElement());
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
}
