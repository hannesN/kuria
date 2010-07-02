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
package de.topicmapslab.kuria.swtgenerator.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.swtgenerator.WidgetGenerator;
import de.topicmapslab.kuria.swtgenerator.edit.IInputMaskListener;
import de.topicmapslab.kuria.swtgenerator.edit.InputMask;

/**
 * A widget which combines a table viewer with an input mask. The table viewer
 * lists all available instances of a specific model type and the input mask
 * provides widgets to modify existing instances and create new one.
 * 
 * It is also possible to remove instances from the widget.
 * 
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class MasterDetailWidget<T> extends AbstractComplexWidget<T> {

	private Composite parentComposite;

	private TableViewer tableViewer;

	private InputMask inputMask;

	private Composite buttonBar;

	private Button addButton;

	private Button removeButton;

	private Button saveButton;

	private Button cancelButton;

	private T currSelection;

	private List<T> input;

	private boolean horizontal;

	/**
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * 
	 */
	public MasterDetailWidget(Composite parent, int style, IBindingContainer bindingContainer, Class<T> clazz) {
		this(parent, style, bindingContainer, clazz, false);
	}

	public MasterDetailWidget(Composite parent, int style, IBindingContainer bindingContainer, Class<T> clazz, boolean horizontal) {
		super(bindingContainer, clazz);
		this.horizontal = horizontal;
		
		createControl(parent, style);
	}

	/**
	 * @param parent
	 * @param style
	 */
	private void createControl(Composite parent, int style) {
		WidgetGenerator gen = new WidgetGenerator(getBindingContainer());

		parentComposite = new Composite(parent, style);
		parentComposite.setLayout(new FormLayout());

		createTable(parentComposite, gen);
		createButtonBar(parentComposite);
		createInputMask(parentComposite, gen);

		setLayoutData();
		hookButtonListeners();
		updateButtons();
	}

	/**
	 * @param parent
	 * @param gen
	 */
	private void createInputMask(Composite parent, WidgetGenerator gen) {
		inputMask = gen.generateEditable(getClazz(), parent, SWT.V_SCROLL);
		hookInputMaskListener();
		
		Composite buttonBar = new Composite(inputMask.getContainer(), SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		gd.horizontalAlignment = SWT.RIGHT;
		buttonBar.setLayoutData(gd);
		buttonBar.setLayout(new GridLayout(2, false));
		
		saveButton = new Button(buttonBar, SWT.PUSH);
		saveButton.setText("Save");
		gd = new GridData();
		gd.widthHint = 120;
		gd.heightHint = 20;
		saveButton.setLayoutData(gd);
		
		
		cancelButton = new Button(buttonBar, SWT.PUSH);
		cancelButton.setText("Cancel");
		gd = new GridData();
		gd.widthHint = 120;
		gd.heightHint = 20;
		cancelButton.setLayoutData(gd);
	}

    private void hookInputMaskListener() {
    	inputMask.addInputMaskListeners(new IInputMaskListener() {
			
			public void newModelElement(Object newElement) {
				// DO ntohing right now..
			}
			
			public void dirtyChanged() {
				updateButtons();
			}
		});
    }

	/**
	 * @param parent
	 */
	private void createButtonBar(Composite parent) {
		buttonBar = new Composite(parent, SWT.NONE);
		buttonBar.setLayout(new FormLayout());
		
		addButton = new Button(buttonBar, SWT.PUSH);
		addButton.setText("New");
		
		removeButton = new Button(buttonBar, SWT.PUSH);
		removeButton.setText("Delete");
	}

	private void setLayoutData() {
		if (horizontal) {
			FormData fd = new FormData();
			fd.top = new FormAttachment(0, 0);
			fd.bottom = new FormAttachment(100, 0);
			fd.left = new FormAttachment(0, 0);
			fd.right = new FormAttachment(40, 0);
			tableViewer.getTable().getParent().setLayoutData(fd);
			
			
			// button bar
			fd = new FormData();
			fd.width = 120;
			fd.top = new FormAttachment(0, 0);
			fd.bottom = new FormAttachment(100, 0);
			fd.left = new FormAttachment(tableViewer.getTable().getParent(), 0);
			buttonBar.setLayoutData(fd);

			fd = new FormData();
			fd.height = 30;
			fd.top = new FormAttachment(35, 0);
			fd.left = new FormAttachment(0, 5);
			fd.right = new FormAttachment(100, 0);
			addButton.setLayoutData(fd);

			removeButton.setText("Delete");
			fd = new FormData();
			fd.height = 30;
			fd.top = new FormAttachment(addButton, 10);
			fd.left = new FormAttachment(0, 0);
			fd.right = new FormAttachment(100, 0);
			removeButton.setLayoutData(fd);
			
			// input mask
			fd = new FormData();
			fd.top = new FormAttachment(0, 0);
			fd.bottom = new FormAttachment(100, 0);
			fd.left = new FormAttachment(buttonBar, 5);
			fd.right = new FormAttachment(100, 0);
			inputMask.getComposite().setLayoutData(fd);
		} else {
			// table
			FormData fd = new FormData();
			fd.left = new FormAttachment(0, 0);
			fd.right = new FormAttachment(100, 0);
			fd.top = new FormAttachment(0, 0);
			fd.bottom = new FormAttachment(40, 0);
			tableViewer.getTable().getParent().setLayoutData(fd);
			
			
			// button bar
			fd = new FormData();
			fd.height = 30;
			fd.left = new FormAttachment(0, 0);
			fd.right = new FormAttachment(100, 0);
			fd.top = new FormAttachment(tableViewer.getTable().getParent(), 5);
			buttonBar.setLayoutData(fd);

			fd = new FormData();
			fd.width = 120;
			fd.left = new FormAttachment(35, 0);
			fd.top = new FormAttachment(0, 0);
			fd.bottom = new FormAttachment(100, 0);
			addButton.setLayoutData(fd);

			removeButton.setText("Delete");
			fd = new FormData();
			fd.width = 120;
			fd.left = new FormAttachment(addButton, 10);
			fd.top = new FormAttachment(0, 0);
			fd.bottom = new FormAttachment(100, 0);
			removeButton.setLayoutData(fd);
			
			// input mask
			fd = new FormData();
			fd.left = new FormAttachment(0, 0);
			fd.right = new FormAttachment(100, 0);
			fd.top = new FormAttachment(buttonBar, 5);
			fd.bottom = new FormAttachment(100, 0);
			inputMask.getComposite().setLayoutData(fd);
		}
	}

	/**
	 * @param parent
	 * @param gen
	 */
	private void createTable(Composite parent, WidgetGenerator gen) {
		tableViewer = gen.generateTable(getClazz(), parent);

		saveButton = new Button(parent, SWT.PUSH);
		saveButton.setText("Save");

		cancelButton = new Button(parent, SWT.PUSH);
		cancelButton.setText("Cancel");
		hookTableListener();
	}

	private void hookButtonListeners() {
		addButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				currSelection = null;
				try {
					inputMask.setModel(getClazz().getConstructor().newInstance());
					updateButtons();
				} catch (Exception e1) {
					// TODO logme
				}
			}
		});

		removeButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableViewer.getSelection().isEmpty())
					return;

				IStructuredSelection sel = (IStructuredSelection) tableViewer.getSelection();
				List<T> list = new ArrayList<T>();
				Iterator<T> it = sel.iterator();
				while (it.hasNext()) {
					list.add((T) it.next());
				}

				DeleteEvent<T> event = new DeleteEvent<T>(list);

				notifyDelete(event);
				if (event.commit) {
					if (tableViewer.getInput() instanceof List<?>) {
						input.removeAll(list);
						tableViewer.refresh();
					}
				}
				updateButtons();
			}
		});

		saveButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@SuppressWarnings("unchecked")
            @Override
			public void widgetSelected(SelectionEvent e) {
				inputMask.persist();	
				T modifiedModel = (T) inputMask.getModel();
				PersistEvent<T> event = new PersistEvent<T>(currSelection, modifiedModel);

				notifyPersist(event);

				if (event.commit) {
									
					if (currSelection == null) {
						if (tableViewer.getInput() instanceof List<?>) {
							input.add(modifiedModel);
							tableViewer.refresh();
						}
					} else {
						try {
							copyFieldValues(modifiedModel, currSelection);
							tableViewer.refresh(currSelection);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					inputMask.setModel(null);
					updateButtons();
				}

			}
		});

		cancelButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				currSelection = null;
				inputMask.setModel(null);
				updateButtons();
			}
		});

	}

	private void hookTableListener() {
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@SuppressWarnings("unchecked")
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();
				if (sel.size() == 1) {
					try {
						currSelection = (T) sel.getFirstElement();
						T copy = (T) currSelection.getClass().getConstructor().newInstance();
						copyFieldValues(currSelection, copy);
						inputMask.setModel(copy);
					} catch (Exception e) {
						// TODO log me
						e.printStackTrace();
					}
				}

				updateButtons();
			}
		});
	}

	private void updateButtons() {
		if (inputMask.getModel() == null) {
			saveButton.setEnabled(false);
			cancelButton.setEnabled(false);
			addButton.setEnabled(true);
		} else {
			cancelButton.setEnabled(true);
			saveButton.setEnabled(inputMask.isValid());
			addButton.setEnabled(false);
		}
		removeButton.setEnabled(tableViewer.getSelection().isEmpty());
	}

	/**
	 * Sets the input of the
	 * 
	 * @param input
	 */
	public void setInput(List<T> input) {
		this.input = input;
		if (tableViewer != null)
			tableViewer.setInput(input);
	}
}
