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
package kuria.test.edit;

import kuria.test.model.Person2;

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
public class GroupTest implements IInputMaskListener{
	private InputMask inputMask;

	public static void main(String[] args) {
		new GroupTest().run();
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
		
		initModel();
		
		
		shell.setText("TableTest");
		shell.setSize(800, 300);
		shell.setLayout(new GridLayout());
		Composite comp = new Composite(shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout());
		
		AnnotationBindingFactory fac = new AnnotationBindingFactory();
		fac.addClass(Person2.class);
		fac.addClass(Person2.Address.class);
		
		IBindingContainer bc = fac.getBindingContainer();
	
		bc.setLabelProvider(new ILabelProvider() {
			
			public String getLabel(String label) {
				if ("FavPet".equals(label))
					return "Haustier";
				return label;
			}
		});
		
		WidgetGenerator gen = new WidgetGenerator(bc);
		
		inputMask = gen.generateEditable(Person2.class, comp);
		inputMask.addInputMaskListeners(this);
		inputMask.getComposite().setLayoutData(new GridData(GridData.FILL_BOTH));
		inputMask.setContentProvider(new IContentProvider() {
			
			public boolean hasContent(String fieldname, Object model) {
				if ("identifier".equals(fieldname))
					return false;
				return true;
			}
			
			public Object[] getElements(String fieldname, Object model) {
				if (fieldname.equals("number")) {
	                return new String[] {"1", "2", "3"};
                }
				return new Object[0];
			}
		});
		
	
		Button button = new Button(comp, SWT.PUSH);
		button.setText("new");
		button.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				inputMask.setModel(new Person2());
			}
		});
		
		button = new Button(comp, SWT.PUSH);
		button.setText("persist");
		button.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				inputMask.persist();
				System.out.println("p");
			}
		});
		
	}
	
	/**
     * 
     */
    private void initModel() {
	    
    }

	

	public void dirtyChanged() {
	    System.out.println("Dirty Changed: "+inputMask.isDirty());
	    boolean valid = inputMask.isValid();
		System.out.println("Valid: "+valid);
    }

	public void newModelElement(Object newElement) {
		System.out.println("New Model Element "+newElement);
    }
}
