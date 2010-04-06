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
package de.topicmapslab.jgui.swtgenerator.edit.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import de.topicmapslab.jgui.swtgenerator.edit.IContentProvider;
import de.topicmapslab.jgui.swtgenerator.util.TextBindingLabelProvider;
import de.topicmapslab.kuria.runtime.IBindingContainer;

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

}
