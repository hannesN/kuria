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
package de.topicmapslab.kuria.swtgenerator.table;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.ITextBinding;
import de.topicmapslab.kuria.runtime.table.IColumnBinding;
import de.topicmapslab.kuria.runtime.table.ITableBinding;
import de.topicmapslab.kuria.swtgenerator.AbstractSWTGenerator;
import de.topicmapslab.kuria.swtgenerator.IContextMenuListener;
import de.topicmapslab.kuria.swtgenerator.util.ImageRegistry;

/**
 * @author Hannes Niederhausen
 * 
 */
public class TableGenerator extends AbstractSWTGenerator {

	public TableGenerator(IBindingContainer bindingContainer) {
		super(bindingContainer);
	}

	public TableViewer generateTable(Composite parent, Class<?> clazz) {
		return generateTable(parent, clazz, null, SWT.NONE);
	}	
	public TableViewer generateTable(Composite parent, Class<?> clazz, final IContextMenuListener listener, int style) {
		ITableBinding tb = bindingContainer.getTableBinding(clazz);
		if (tb==null)
			throw new IllegalArgumentException();
		
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new TableColumnLayout());
		
		TableViewer viewer = new TableViewer(comp, style);

		viewer.setContentProvider(new ArrayContentProvider());

		generateColumns(clazz, viewer);
		
		if (listener!=null) {
			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(new IMenuListener() {
				public void menuAboutToShow(IMenuManager manager) {
					listener.createMenu(manager);
				}
			});
			Menu menu = menuMgr.createContextMenu(viewer.getControl());
			viewer.getControl().setMenu(menu);
		}
		
		return viewer;
	}

	private void generateColumns(Class<?> clazz, TableViewer tableViewer) {
		Table table = tableViewer.getTable();
	    ITableBinding tb = bindingContainer.getTableBinding(clazz);
	    TableColumnLayout layout = (TableColumnLayout) table.getParent().getLayout();
	    
	    boolean firstCol = true;
	    
	    for (IColumnBinding cb : tb.getColumnBindings()) {
	    	
	    	TableColumn tc = new TableColumn(table, 0);
	    	tc.setText(cb.getColumnTitle());
	    	tc.setWidth(50);
	    	layout.setColumnData(tc, new ColumnWeightData(1));
	    	
	    	
	    	TableViewerColumn column = new TableViewerColumn(tableViewer, tc);
	    	column.setLabelProvider(new KuriaColumnLabelProvider(cb));
	    	
	    	// listener registers to column haeder clicks and sets on click 
	    	ColumnViewerSorter sorter = new ColumnViewerSorter(tableViewer, column, cb);
	    	if (firstCol) {
	    		// set initial sort to first column
	    		sorter.setSorter(sorter, ColumnViewerSorter.ASC);
	    		firstCol = false;
	    	}
	    }
	    
	    table.setHeaderVisible(true);
    }

	public class KuriaColumnLabelProvider extends ColumnLabelProvider {

		private final IColumnBinding columnBinding;
		
		public KuriaColumnLabelProvider(IColumnBinding columnBinding) {
	        super();
	        this.columnBinding = columnBinding;
        }

		@Override
		public Image getImage(Object element) {
			if (columnBinding.getColumnImage(element)!=null)
				return ImageRegistry.getImage(columnBinding.getColumnImage(element));
			
			return null;
		}

		@Override
		public String getText(Object element) {
			try {
				
				String tmp = columnBinding.getColumnText(element);
				if (tmp!=null)
					return tmp;
				
				Object o = columnBinding.getValue(element);
				if (o instanceof String)
					return (String) o;
				else { 
					if (o!=null) {
						ITextBinding binding = bindingContainer.getTextBinding(o.getClass());
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

	}


	/*
	 * Based on the implementation in JFace Snippets.
	 * @see http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.snippets/Eclipse%20JFace%20Snippets/org/eclipse/jface/snippets/viewers/Snippet040TableViewerSorting.java?view=markup
	 */
	private class ColumnViewerSorter extends ViewerSorter {
		public static final int ASC = 1;
		
		public static final int NONE = 0;
		
		public static final int DESC = -1;
		
		private int direction = 0;
		
		private final TableViewerColumn column;
		
		private final ColumnViewer viewer;
		
		private final IColumnBinding columnBinding;
		
		public ColumnViewerSorter(ColumnViewer viewer, TableViewerColumn column, IColumnBinding columnBinding) {
			this.column = column;
			this.columnBinding = columnBinding;
			this.viewer = viewer;
			this.column.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				if( ColumnViewerSorter.this.viewer.getComparator() != null ) {
					if( ColumnViewerSorter.this.viewer.getComparator() == ColumnViewerSorter.this ) {
						int tdirection = ColumnViewerSorter.this.direction;
						
						if( tdirection == ASC ) {
							setSorter(ColumnViewerSorter.this, DESC);
						} else if( tdirection == DESC ) {
							setSorter(ColumnViewerSorter.this, NONE);
						}
					} else {
						setSorter(ColumnViewerSorter.this, ASC);
					}
				} else {
					setSorter(ColumnViewerSorter.this, ASC);
				}
			}
		});
		}
		public void setSorter(ColumnViewerSorter sorter, int direction) {
			if( direction == NONE ) {
				column.getColumn().getParent().setSortColumn(null);
				column.getColumn().getParent().setSortDirection(SWT.NONE);
				viewer.setComparator(null);
			} else {
				column.getColumn().getParent().setSortColumn(column.getColumn());
				sorter.direction = direction;
				
				if( direction == ASC ) {
					column.getColumn().getParent().setSortDirection(SWT.DOWN);
				} else {
					column.getColumn().getParent().setSortDirection(SWT.UP);
				}
				
				if( viewer.getComparator() == sorter ) {
					viewer.refresh();
				} else {
					viewer.setComparator(sorter);
				}
				
			}
		}

		public int compare(Viewer viewer, Object e1, Object e2) {
			return direction * doCompare(viewer, e1, e2);
		}
		
		protected int doCompare(Viewer viewer, Object e1, Object e2) {
			String s1 = getText(e1);
			String s2 = getText(e2);
			return s1.toLowerCase().compareTo(s2.toLowerCase());
		}
		
		public String getText(Object element) {
			try {
				
				String tmp = columnBinding.getColumnText(element);
				if (tmp!=null)
					return tmp;
				
				Object o = columnBinding.getValue(element);
				if (o instanceof String)
					return (String) o;
				else { 
					if (o!=null) {
						ITextBinding binding = bindingContainer.getTextBinding(o.getClass());
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
	}
}
