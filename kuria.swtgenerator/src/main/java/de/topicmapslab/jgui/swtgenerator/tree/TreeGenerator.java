/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import de.topicmapslab.jgui.swtgenerator.AbstractSWTGenerator;
import de.topicmapslab.jgui.swtgenerator.util.ImageRegistry;
import de.topicmapslab.jgui.swtgenerator.util.TextBindingLabelProvider;
import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.tree.IChildrenBinding;
import de.topicmapslab.kuria.runtime.tree.ITreeNodeBinding;
import de.topicmapslab.kuria.runtime.util.TypeUtil;

/**
 * @author Hannes Niederhausen
 * 
 */
public class TreeGenerator extends AbstractSWTGenerator {

	public TreeGenerator(IBindingContainer bindingContainer) {
		super(bindingContainer);
	}

	public TreeViewer generateTree(Composite parent, boolean showRoot) {
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

					if ((tnb != null) && (tnb.getImage() != null))
						return ImageRegistry.getImage(tnb.getImage());
				}
				return null;
			}
		});

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

		@SuppressWarnings("unchecked")
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
						children = new ArrayList<Object>((Collection) arg0);
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
						n.setTitle(cb.getNodeTitle());
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
