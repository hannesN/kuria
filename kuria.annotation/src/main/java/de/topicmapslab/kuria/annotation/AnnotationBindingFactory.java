/**
 * 
 */
package de.topicmapslab.kuria.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.topicmapslab.kuria.annotation.table.Column;
import de.topicmapslab.kuria.annotation.table.TableElement;
import de.topicmapslab.kuria.annotation.tree.Children;
import de.topicmapslab.kuria.annotation.tree.TreeNode;
import de.topicmapslab.kuria.annotation.widgets.Check;
import de.topicmapslab.kuria.annotation.widgets.Combo;
import de.topicmapslab.kuria.annotation.widgets.Date;
import de.topicmapslab.kuria.annotation.widgets.Editable;
import de.topicmapslab.kuria.annotation.widgets.Group;
import de.topicmapslab.kuria.annotation.widgets.Hidden;
import de.topicmapslab.kuria.annotation.widgets.List;
import de.topicmapslab.kuria.annotation.widgets.TextField;
import de.topicmapslab.kuria.runtime.BindingContainer;
import de.topicmapslab.kuria.runtime.GenericBindingFactory;
import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IBindingFactory;
import de.topicmapslab.kuria.runtime.PropertyBinding;
import de.topicmapslab.kuria.runtime.TextBinding;
import de.topicmapslab.kuria.runtime.table.ColumnBinding;
import de.topicmapslab.kuria.runtime.table.TableBinding;
import de.topicmapslab.kuria.runtime.tree.ChildrenBinding;
import de.topicmapslab.kuria.runtime.tree.TreeNodeBinding;
import de.topicmapslab.kuria.runtime.widget.CheckBinding;
import de.topicmapslab.kuria.runtime.widget.ComboBinding;
import de.topicmapslab.kuria.runtime.widget.DateBinding;
import de.topicmapslab.kuria.runtime.widget.EditableBinding;
import de.topicmapslab.kuria.runtime.widget.GroupBinding;
import de.topicmapslab.kuria.runtime.widget.ListBinding;
import de.topicmapslab.kuria.runtime.widget.TextFieldBinding;

/**
 * <p>
 * Parses a set of classes using reflections and creates bindings according to
 * the used annotation.
 * </p>
 * 
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class AnnotationBindingFactory extends GenericBindingFactory implements IBindingFactory {

	private BindingContainer bindingContainer;

	public IBindingContainer getBindingContainer() {
		if (bindingContainer == null) {
			init();
		}

		return bindingContainer;
	}

	private void init() {
		bindingContainer = new BindingContainer();
		for (Class<?> c : getClasses()) {
			parseClass(c);
		}
	}

	private void parseClass(Class<?> c) {
		// check for TreeNode annotation
		if (c.getAnnotation(TreeNode.class) != null) {
			TreeNodeBinding tnb = createTreeNodeBinding(c);
			bindingContainer.putTreeNodeBindings(c, tnb);
		}
		if (c.getAnnotation(TableElement.class) != null) {
			TableBinding tb = createTableBinding(c);
			bindingContainer.putTableBindings(c, tb);
		}
		if (c.getAnnotation(Editable.class) != null) {
			EditableBinding eb = createEditableBinding(c);
			bindingContainer.putEditableBindings(c, eb);
		}
		TextBinding tb = createTextBinding(c);
		bindingContainer.putTextBinding(c, tb);
	}

	private EditableBinding createEditableBinding(Class<?> c) {
		EditableBinding eb = new EditableBinding();

		for (Field f : c.getDeclaredFields()) {
			if (f.getAnnotation(Hidden.class) == null) {
				PropertyBinding pb = createPropertyBinding(f);
				pb.setFieldName(f.getName());
				pb.setType(f.getGenericType());
				eb.addPropertyBinding(pb);
			}
		}

		return eb;
	}

	private TextBinding createTextBinding(Class<?> c) {
		for (Field f : c.getDeclaredFields()) {
			Text text = f.getAnnotation(Text.class);
			if (text != null) {
				TextBinding tb = new TextBinding();
				tb.setFieldName(f.getName());
				return tb;
			}
		}
		for (Method m : c.getDeclaredMethods()) {
			if (m.getAnnotation(Text.class) != null) {
				TextBinding tb = new TextBinding();
				StringBuilder sb = new StringBuilder();
				char character = Character.toLowerCase(m.getName().charAt(3));
				sb.append(character);
				sb.append(m.getName().substring(4));

				tb.setFieldName(sb.toString());
				return tb;
			}
		}
		return new TextBinding();
	}

	private PropertyBinding createPropertyBinding(Field f) {
		PropertyBinding pb = null;
		if (f.getAnnotation(TextField.class) != null) {
			pb = createTextFieldBinding(f);
		} else if (f.getAnnotation(Check.class) != null) {
			pb = createCheckBinding(f);
		} else if (f.getAnnotation(Combo.class) != null) {
			pb = createComboBinding(f);
		} else if (f.getAnnotation(Group.class) != null) {
			pb = createGroupBinding(f);
		} else if (f.getAnnotation(List.class) != null) {
			pb = createListBinding(f);
		} else if (f.getAnnotation(Date.class) != null) {
			pb = createDateBinding(f);
		} else {// TODO other types
			pb = findBinding(f);
		}
		pb.setFieldName(f.getName());
		pb.setType(f.getGenericType());
		return pb;
	}

	private PropertyBinding createListBinding(Field f) {
		List l = f.getAnnotation(List.class);
		ListBinding lb = new ListBinding();
		if (l.label().length() > 0)
			lb.setLabel(l.label());
		lb.setReadOnly(l.readOnly());
		lb.setCreateNew(l.createNew());
		lb.setListStyle(l.style());
		lb.setOptional(l.optional());

		return lb;
	}

	private PropertyBinding createGroupBinding(Field f) {
		Group g = f.getAnnotation(Group.class);
		GroupBinding gb = new GroupBinding();
		if (g.label().length() > 0)
			gb.setLabel(g.label());

		gb.setReadOnly(g.readOnly());
		gb.setOptional(g.optional());
		return gb;
	}

	private PropertyBinding createComboBinding(Field f) {
		Combo c = f.getAnnotation(Combo.class);
		ComboBinding cb = new ComboBinding();
		if (c.label().length() > 0)
			cb.setLabel(c.label());

		cb.setReadOnly(c.readOnly());
		cb.setShowNewButton(c.createNew());

		cb.setOptional(c.optional());
		
		return cb;
	}
	
	private PropertyBinding createDateBinding(Field f) {
		Date d = f.getAnnotation(Date.class);
		DateBinding db = new DateBinding();
		if (d.label().length()>0)
			db.setLabel(d.label());
		db.setReadOnly(d.readOnly());
		db.setFormat(d.format());
		
		db.setOptional(d.optional());
		
		return db;
	}

	private PropertyBinding createCheckBinding(Field f) {
		Check c = f.getAnnotation(Check.class);
		CheckBinding cb = new CheckBinding();
		if (c.label().length() > 0)
			cb.setLabel(c.label());
		
		cb.setOptional(c.optional());
		
		return cb;
	}

	private PropertyBinding createTextFieldBinding(Field f) {
		TextField tf = f.getAnnotation(TextField.class);
		TextFieldBinding tfb = new TextFieldBinding();
		tfb.setReadOnly(tf.readOnly());

		if (tf.label().length() > 0)
			tfb.setLabel(tf.label());

		tfb.setGrabVerticalSpace(tf.grabVerticalSpace());
		tfb.setRegExp(tf.regexp());
		tfb.setPassword(tf.password());
		tfb.setRows(tf.rows());
		tfb.setOptional(tf.optional());
		
		return tfb;
	}

	private TableBinding createTableBinding(Class<?> c) {
		TableBinding b = new TableBinding();

		for (Field f : c.getDeclaredFields()) {
			Column col = f.getAnnotation(Column.class);
			if (col != null) {
				ColumnBinding cb = new ColumnBinding();
				if (col.image().length() > 0) {
					cb.setColumnImage(col.image());
				}
				if (col.title().length() > 0) {
					cb.setColumnTitle(col.title());
				}
				cb.setFieldName(f.getName());
				b.addColumnBinding(cb);
			}
		}

		return b;
	}

	private TreeNodeBinding createTreeNodeBinding(Class<?> c) {
		TreeNodeBinding b = new TreeNodeBinding();
		TreeNode a = c.getAnnotation(TreeNode.class);

		if (a.image().length() > 0)
			b.setImage(a.image());

		for (Field f : c.getDeclaredFields()) {
			Children children = f.getAnnotation(Children.class);
			if (children != null) {
				ChildrenBinding cb = new ChildrenBinding();
				if (children.title().length() > 0) {
					cb.setNodeTitle(children.title());
					if (children.image().length() > 0)
						cb.setNodeImage(children.image());
				}
				cb.setFieldName(f.getName());
				cb.setType(f.getGenericType());
				b.addChild(cb);
			}
		}
		
		for (Method m : c.getDeclaredMethods()) {
			Children children = m.getAnnotation(Children.class);
			if (children != null) {
				ChildrenBinding cb = new ChildrenBinding();
				if (children.title().length() > 0) {
					cb.setNodeTitle(children.title());
					if (children.image().length() > 0)
						cb.setNodeImage(children.image());
				}
				// find name
				StringBuilder sb = new StringBuilder();
				char character = Character.toLowerCase(m.getName().charAt(3));
				sb.append(character);
				sb.append(m.getName().substring(4));

				cb.setFieldName(sb.toString());
				cb.setType(m.getGenericReturnType());
				
				b.addChild(cb);
			}
		}
		return b;
	}
}
