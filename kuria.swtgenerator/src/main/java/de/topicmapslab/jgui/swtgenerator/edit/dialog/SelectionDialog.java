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
import de.topicmapslab.kuria.runtime.BindingContainer;

/**
 * @author Hannes Niederhausen
 *
 */
public class SelectionDialog extends Dialog {

	private final IContentProvider contentProvider;
	private final BindingContainer bc;
	private final String fieldname;
	private final Object model;
	
	private Object[] initialSelection;
	private CheckboxTableViewer checkTableViewer;
	
	private Object[] selection;
	
	public  SelectionDialog(Shell parentShell, BindingContainer bindingContainer, IContentProvider contentProvider, String fieldname, Object model) {
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
