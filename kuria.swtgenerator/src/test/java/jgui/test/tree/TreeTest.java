/**
 * 
 */
package jgui.test.tree;

import jgui.test.model.Address;
import jgui.test.model.Person;
import jgui.test.model.Pet;

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

import de.topicmapslab.jgui.swtgenerator.WidgetGenerator;
import de.topicmapslab.kuria.annotation.AnnotationBindingFactory;
import de.topicmapslab.kuria.runtime.BindingContainer;

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
		
		BindingContainer bc = fac.getBindingContainer();
		
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
