/**
 * 
 */
package jgui.test.table;

import java.util.ArrayList;

import jgui.test.model.Address;
import jgui.test.model.Person;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.Ignore;

import de.topicmapslab.jgui.swtgenerator.WidgetGenerator;
import de.topicmapslab.kuria.annotation.AnnotationBindingFactory;
import de.topicmapslab.kuria.runtime.IBindingContainer;

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
