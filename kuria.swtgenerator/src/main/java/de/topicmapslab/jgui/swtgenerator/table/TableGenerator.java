/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.table;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.topicmapslab.jgui.swtgenerator.AbstractSWTGenerator;
import de.topicmapslab.jgui.swtgenerator.util.ImageRegistry;
import de.topicmapslab.kuria.runtime.BindingContainer;
import de.topicmapslab.kuria.runtime.TextBinding;
import de.topicmapslab.kuria.runtime.table.ColumnBinding;
import de.topicmapslab.kuria.runtime.table.TableBinding;

/**
 * @author Hannes Niederhausen
 * 
 */
public class TableGenerator extends AbstractSWTGenerator {

	public TableGenerator(BindingContainer bindingContainer) {
		super(bindingContainer);
	}

	public TableViewer generateTable(Composite parent, Class<?> clazz) {
		TableBinding tb = bindingContainer.getTableBinding(clazz);
		if (tb==null)
			throw new IllegalArgumentException();
		
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new TableColumnLayout());
		
		TableViewer viewer = new TableViewer(comp);

		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new LabelProvider());

		generateColumns(clazz, viewer.getTable());
		
		return viewer;
	}

	private void generateColumns(Class<?> clazz, Table table) {
	    TableBinding tb = bindingContainer.getTableBinding(clazz);
	    
	    TableColumnLayout layout = (TableColumnLayout) table.getParent().getLayout();
	    
	    for (ColumnBinding cb : tb.getColumnBindings()) {
	    	TableColumn tc = new TableColumn(table, 0);
	    	tc.setText(cb.getColumnTitle());
	    	tc.setWidth(50);
	    	layout.setColumnData(tc, new ColumnWeightData(1));	    	
	    }
	    table.setHeaderVisible(true);
    }

	private class LabelProvider implements ITableLabelProvider, IBaseLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			TableBinding tb = bindingContainer.getTableBinding(element.getClass());
			if (tb == null)
				throw new IllegalArgumentException("Element is " + element.getClass().getName());

			ColumnBinding cb = tb.getColumnBindings().get(columnIndex);
			if (cb.getColumnImage()!=null)
				return ImageRegistry.getImage(cb.getColumnImage());
			
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			TableBinding tb = bindingContainer.getTableBinding(element.getClass());
			if (tb == null)
				throw new IllegalArgumentException("Element is " + element.getClass().getName());

			ColumnBinding cb = tb.getColumnBindings().get(columnIndex);

			try {
				Object o = cb.getValue(element);
				if (o instanceof String)
					return (String) o;
				else { 
					if (o!=null) {
						TextBinding binding = bindingContainer.getTextBinding(o.getClass());
						if (binding==null)
							return o.toString();
						else
							return binding.getText(o);
					}
				}
				return "";
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}

	}
}
