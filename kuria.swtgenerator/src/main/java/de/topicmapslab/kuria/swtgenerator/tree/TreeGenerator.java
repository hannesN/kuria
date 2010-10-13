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
package de.topicmapslab.kuria.swtgenerator.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.tree.IChildrenBinding;
import de.topicmapslab.kuria.runtime.tree.ITreeNodeBinding;
import de.topicmapslab.kuria.runtime.util.TypeUtil;
import de.topicmapslab.kuria.swtgenerator.AbstractSWTGenerator;
import de.topicmapslab.kuria.swtgenerator.IContextMenuListener;
import de.topicmapslab.kuria.swtgenerator.util.ImageRegistry;
import de.topicmapslab.kuria.swtgenerator.util.TextBindingLabelProvider;

/**
 * @author Hannes Niederhausen
 * 
 */
public class TreeGenerator extends AbstractSWTGenerator {

	public TreeGenerator(IBindingContainer bindingContainer) {
		super(bindingContainer);
	}

	public TreeViewer generateTree(Composite parent, boolean showRoot) {
		return generateTree(parent, showRoot, null);
	}
	
	public TreeViewer generateTree(Composite parent, boolean showRoot, final IContextMenuListener listener) {
		TreeViewer viewer = new TreeViewer(parent) {
			@Override
			public void refresh() {
				((TreeBindingContentProvider) getContentProvider()).setReturnRoot(true);
				super.refresh();
			}
		};

		viewer.setContentProvider(new TreeBindingContentProvider(showRoot));
		viewer.setLabelProvider(new TextBindingLabelProvider(bindingContainer) {

			@Override
			public String getText(Object element) {
				if (element instanceof MediatorNode) {
					return ((MediatorNode) element).getTitle();
				}
				return super.getText(element);
			}

			@Override
			public Image getImage(Object element) {
				if (element instanceof MediatorNode) {
					String image = ((MediatorNode) element).getImage();
					if (image != null)
						return ImageRegistry.getImage(image);
				} else {

					ITreeNodeBinding tnb = bindingContainer.getTreeNodeBinding(element.getClass());

					if ((tnb != null) && (tnb.getImage(element) != null))
						return ImageRegistry.getImage(tnb.getImage(element));
				}
				return null;
			}
		});

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

	private class TreeBindingContentProvider implements ITreeContentProvider {

		// private TreeNode root;
		private Object root;
		private boolean showRoot;
		private boolean returnRoot;

		public TreeBindingContentProvider(boolean showRoot) {
			super();
			this.showRoot = showRoot;
		}

		public void setReturnRoot(boolean returnRoot) {
			if (showRoot) {
				this.returnRoot = returnRoot;
			}
		}

		public Object[] getChildren(Object arg0) {
			List<Object> children = null;

			if (arg0 instanceof MediatorNode) {
				children = new ArrayList<Object>();
				Object child = ((MediatorNode) arg0).getChildren();
				if (child != null) {
					addChild(children, child);
				}
			} else {
				boolean isMany = !TypeUtil.isNoCollection(arg0.getClass());

				if (!isMany && returnRoot) {
					returnRoot = false;
					return new Object[] { root };
				}

				if (isMany) {
					if (TypeUtil.isArray(arg0.getClass()))
						children = Arrays.asList((Object[]) arg0);
					else
						children = new ArrayList<Object>((Collection<?>) arg0);
				}
			}
			if (children != null)
				return children.toArray();

			children = new ArrayList<Object>();
			ITreeNodeBinding tnb = bindingContainer.getTreeNodeBinding(arg0.getClass());
			if (tnb == null)
				return new Object[0];

			for (IChildrenBinding cb : tnb.getChildren()) {
				try {
					Object child = cb.getValue(arg0);
					if (cb.getNodeTitle() != null) {
						MediatorNode n = new MediatorNode((Class<?>) cb.getElementType());
						n.setTitle(bindingContainer.getLabelProvider().getLabel(cb.getNodeTitle()));
						n.setImage(cb.getNodeImage());
						n.setChildren(child);
						children.add(n);
					} else {
						if (child != null) {
							addChild(children, child);
						}

					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			return children.toArray(new Object[children.size()]);
		}

		@SuppressWarnings("unchecked")
		private void addChild(List<Object> children, Object child) {
			if (child instanceof Collection<?>) {
				children.addAll((Collection<? extends Object>) child);
			} else {
				children.add(child);
			}
		}

		public Object getParent(Object arg0) {
			return null;
		}

		public boolean hasChildren(Object arg0) {
			return getChildren(arg0).length > 0;
		}

		public Object[] getElements(Object arg0) {
			return getChildren(arg0);
		}

		public void dispose() {
		}

		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
			root = arg2;
			setReturnRoot(true);
		}
	}
}
