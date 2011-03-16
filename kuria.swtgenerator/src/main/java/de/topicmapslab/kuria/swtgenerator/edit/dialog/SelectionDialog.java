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
package de.topicmapslab.kuria.swtgenerator.edit.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.swtgenerator.edit.IContentProvider;
import de.topicmapslab.kuria.swtgenerator.util.TextBindingLabelProvider;

/**
 * @author Hannes Niederhausen
 *
 */
public class SelectionDialog extends Dialog {

	private final IContentProvider contentProvider;
	private final IBindingContainer bc;
	private final String fieldname;
	private final Object model;
	
	private Object[] initialSelection;
	private CheckboxTableViewer checkTableViewer;
	private Text filterText;
	
	private Object[] selection;
	
	public  SelectionDialog(Shell parentShell, IBindingContainer bindingContainer, IContentProvider contentProvider, String fieldname, Object model) {
	    super(parentShell);
	    this.bc = bindingContainer;
	    this.model = model;
	    this.fieldname = fieldname;
	    this.contentProvider = contentProvider;
	    selection = new Object[0];
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
	    Composite comp = new Composite(parent, SWT.NONE);
	    comp.setLayout(new GridLayout());
	    
	    filterText = new Text(comp, SWT.ICON_SEARCH|SWT.SEARCH|SWT.BORDER|SWT.ICON_CANCEL);
	    filterText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    filterText.addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent e) {
				checkTableViewer.refresh();
			}
		});
        
	    
	    checkTableViewer = CheckboxTableViewer.newCheckList(comp, SWT.BORDER);
	    checkTableViewer.setContentProvider(new ArrayContentProvider());
	    checkTableViewer.setLabelProvider(new TextBindingLabelProvider(bc));
	    
	    GridData gd = new GridData(GridData.FILL_BOTH);
	    gd.heightHint = 400;
	    gd.widthHint = 500;
	    checkTableViewer.getTable().setLayoutData(gd);
	    
	    checkTableViewer.setInput(contentProvider.getElements(fieldname, model));
	    if (initialSelection!=null)
	    	checkTableViewer.setCheckedElements(initialSelection);
	    
	    hookViewerListeners();
	    
	    return comp;
	}
	
	@Override
	protected void okPressed() {
		selection = checkTableViewer.getCheckedElements();
	    super.okPressed();
	}
	
	public void setInitialSelection(List<Object> selection) {
	    this.initialSelection = selection.toArray();
    }
	
	public void setInitialSelection(Object[] selection) {
		this.initialSelection = selection;
	}
	
	public Object[] getSelection() {
		return selection;
    }
	
	private void hookViewerListeners() {
		checkTableViewer.setSorter(new ViewerSorter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				TableViewer tv = (TableViewer) viewer;
				
				String t1 = ((TextBindingLabelProvider) tv.getLabelProvider()).getText(e1);
				String t2 = ((TextBindingLabelProvider) tv.getLabelProvider()).getText(e2);
			    return t1.compareTo(t2);
			}
		});
		
		checkTableViewer.addFilter(new ViewerFilter() {
			
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (filterText.getText().length()==0)
					return true;
				
				if (((CheckboxTableViewer) viewer).getChecked(element))
					return true;
				
				String text = ((TextBindingLabelProvider) ((AbstractTableViewer) viewer).getLabelProvider()).getText(element);
				
				return (text.startsWith(filterText.getText())); 
			}
		});
	}

}
