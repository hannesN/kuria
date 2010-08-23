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

import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.topicmapslab.kuria.swtgenerator.WidgetGenerator;
import de.topicmapslab.kuria.swtgenerator.table.TableGenerator.KuriaColumnLabelProvider;

/**
 * This widget consists of a text field and a table veiwer. The table viewer
 * will be filtered according to the value of the text field
 * 
 * @author Hannes Niederhausen
 * 
 */
public class FilteredTableViewerWidget {

	private Text searchText;

	private Button clearButton;

	private TableViewer tableViewer;

	private final WidgetGenerator generator;

	private final Class<?> modelClass;

	private Composite composite;

	public FilteredTableViewerWidget(WidgetGenerator generator, Class<?> modelClass) {
		super();
		this.generator = generator;
		this.modelClass = modelClass;
	}

	public void createControl(Composite parent, int style) {

		composite = new Composite(parent, SWT.NONE);

		composite.setLayout(new GridLayout(3, false));

		Label l = new Label(composite, SWT.NONE);
		l.setText("Filter:");
		searchText = new Text(composite, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				tableViewer.refresh();
            }
			
		});

		clearButton = new Button(composite, SWT.PUSH);
		clearButton.setText("Clear");
		clearButton.addSelectionListener(new SelectionAdapter(){
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchText.setText("");
			}
		});

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;

		tableViewer = generator.generateTable(modelClass, composite, null, style);
		tableViewer.getTable().getParent().setLayoutData(gd);
		tableViewer.addFilter(new Filter());
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		tableViewer.addSelectionChangedListener(listener);
	}

	public Object getInput() {
		return tableViewer.getInput();
	}

	public void addDoubleClickListener(IDoubleClickListener listener) {
		tableViewer.addDoubleClickListener(listener);
	}

	public void addFilter(ViewerFilter filter) {
		tableViewer.addFilter(filter);
	}

	public void refresh(Object element) {
		tableViewer.refresh(element);
	}

	public void refresh() {
		tableViewer.refresh();
	}

	public final void setInput(Object input) {
		tableViewer.setInput(input);
	}

	private class Filter extends ViewerFilter {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			String searchString = searchText.getText().toLowerCase();
			if (searchString.length()==0)
				return true;
			
			int nCols = tableViewer.getTable().getColumnCount();
			
			for (int i = 0; i < nCols; i++) {
				KuriaColumnLabelProvider labelProvider = (KuriaColumnLabelProvider) tableViewer.getLabelProvider(i);
				String tmp = labelProvider.getText(element);
				if (tmp.startsWith(searchString))
					return true;
			}

			return false;
		}
	}
	
	/**
     * @return the composite
     */
    public Composite getComposite() {
	    return composite;
    }
	
	/**
     * @return the tableViewer
     */
    public TableViewer getTableViewer() {
	    return tableViewer;
    }
}
