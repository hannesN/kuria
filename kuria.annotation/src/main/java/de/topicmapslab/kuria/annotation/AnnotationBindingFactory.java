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
package de.topicmapslab.kuria.annotation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

import de.topicmapslab.kuria.annotation.table.Column;
import de.topicmapslab.kuria.annotation.table.TableElement;
import de.topicmapslab.kuria.annotation.tree.Children;
import de.topicmapslab.kuria.annotation.tree.TreeNode;
import de.topicmapslab.kuria.annotation.widgets.Check;
import de.topicmapslab.kuria.annotation.widgets.Combo;
import de.topicmapslab.kuria.annotation.widgets.Date;
import de.topicmapslab.kuria.annotation.widgets.Directory;
import de.topicmapslab.kuria.annotation.widgets.Editable;
import de.topicmapslab.kuria.annotation.widgets.File;
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
import de.topicmapslab.kuria.runtime.widget.DirectoryBinding;
import de.topicmapslab.kuria.runtime.widget.EditableBinding;
import de.topicmapslab.kuria.runtime.widget.FileBinding;
import de.topicmapslab.kuria.runtime.widget.GroupBinding;
import de.topicmapslab.kuria.runtime.widget.IEditableBinding;
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
		// we already have a binding
		
		EditableBinding eb = (EditableBinding) bindingContainer.getEditableBinding(c);
		if (eb!=null)
			return eb;
		
		eb = new EditableBinding();

		Class<?> superClass = c.getSuperclass();
		if ((superClass!=null) && (superClass!=Object.class)) {
			if (getClasses().contains(superClass))
				eb.setParentBinding(createEditableBinding(superClass));
		}
		
		
		
		// caching name so we don't create multiple bindings for the same field 
		ArrayList<String> usedNames = new ArrayList<String>();
		
		for (Field f : c.getDeclaredFields()) {
			if ((hasAccessor(f, c)) && (f.getAnnotation(Hidden.class) == null)) {
				PropertyBinding pb = createPropertyBinding(f, f.getName(), f.getGenericType());
				usedNames.add(f.getName());
				eb.addPropertyBinding(pb);
			}
		}
		
		for (Method m : c.getDeclaredMethods()) {
			String methodName = m.getName();
			String fieldname = null;
			if (methodName.startsWith("get")) {
				fieldname = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
			} else	if (methodName.startsWith("is")) {
				fieldname = Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
			} 
				
			if ((fieldname==null) || (usedNames.contains(fieldname)) || (m.getAnnotations().length == 0))
				continue;

			PropertyBinding pb = createPropertyBinding(m, fieldname, m.getGenericReturnType());
			if (pb == null)
				continue;
			try {
				c.getDeclaredMethod("s" + methodName.substring(1));
			} catch (SecurityException e) {
				// TODO log
			} catch (NoSuchMethodException e) {
				pb.setReadOnly(true);
			}

			usedNames.add(fieldname);
			eb.addPropertyBinding(pb);
			
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

	private PropertyBinding createPropertyBinding(AnnotatedElement f, String name, Type type) {
		PropertyBinding pb = null;
		if (f.getAnnotation(TextField.class) != null) {
			pb = createTextFieldBinding(f.getAnnotation(TextField.class));
		} else if (f.getAnnotation(Check.class) != null) {
			pb = createCheckBinding(f.getAnnotation(Check.class));
		} else if (f.getAnnotation(Combo.class) != null) {
			pb = createComboBinding(f.getAnnotation(Combo.class));
		} else if (f.getAnnotation(Group.class) != null) {
			pb = createGroupBinding(f.getAnnotation(Group.class));
		} else if (f.getAnnotation(List.class) != null) {
			pb = createListBinding(f.getAnnotation(List.class));
		} else if (f.getAnnotation(Date.class) != null) {
			pb = createDateBinding(f.getAnnotation(Date.class));
		} else if (f.getAnnotation(Directory.class) != null) {
			pb = createDirectoryBinding(f.getAnnotation(Directory.class));
		} else if (f.getAnnotation(File.class) != null) {
			pb = createFileBinding(f.getAnnotation(File.class));
		} else if (f instanceof Field){// TODO other types
			pb = findBinding((Field) f);
		} else {
			return null;
		}
		pb.setFieldName(name);
		pb.setType(type);
		return pb;
	}

	private PropertyBinding createFileBinding(File file) {
		FileBinding fb = new FileBinding();
		if (file.label().length() > 0)
			fb.setLabel(file.label());
		fb.setReadOnly(file.readOnly());
		fb.setOptional(file.optional());
		fb.setFileExtensions(file.fileExtensions());
		fb.setLoad(file.load());
		fb.setWeight(file.weight());
		
		return fb;
    }

	private PropertyBinding createDirectoryBinding(Directory directory) {
		DirectoryBinding db = new DirectoryBinding();
		if (directory.label().length() > 0)
			db.setLabel(directory.label());
		db.setReadOnly(directory.readOnly());
		db.setOptional(directory.optional());
		db.setWeight(directory.weight());
	    
		return db;
    }

	private PropertyBinding createListBinding(List list) {
		ListBinding lb = new ListBinding();
		if (list.label().length() > 0)
			lb.setLabel(list.label());
		lb.setReadOnly(list.readOnly());
		lb.setCreateNew(list.createNew());
		lb.setListStyle(list.style());
		lb.setOptional(list.optional());
		lb.setWeight(list.weight());
		
		return lb;
	}

	private PropertyBinding createGroupBinding(Group group) {
		GroupBinding gb = new GroupBinding();
		if (group.label().length() > 0)
			gb.setLabel(group.label());

		gb.setReadOnly(group.readOnly());
		gb.setOptional(group.optional());
		gb.setWeight(group.weight());
		return gb;
	}

	private PropertyBinding createComboBinding(Combo combo) {
		ComboBinding cb = new ComboBinding();
		if (combo.label().length() > 0)
			cb.setLabel(combo.label());

		cb.setReadOnly(combo.readOnly());
		cb.setShowNewButton(combo.createNew());
		cb.setOptional(combo.optional());
		cb.setWeight(combo.weight());
		
		return cb;
	}
	
	private PropertyBinding createDateBinding(Date date) {
		DateBinding db = new DateBinding();
		if (date.label().length()>0)
			db.setLabel(date.label());
		db.setReadOnly(date.readOnly());
		db.setFormat(date.format());
		
		db.setOptional(date.optional());
		db.setWeight(date.weight());
		
		return db;
	}

	private PropertyBinding createCheckBinding(Check check) {
		CheckBinding cb = new CheckBinding();
		if (check.label().length() > 0)
			cb.setLabel(check.label());
		
		cb.setOptional(check.optional());
		cb.setWeight(check.weight());
		
		return cb;
	}

	private PropertyBinding createTextFieldBinding(TextField textfield) {
		TextFieldBinding tfb = new TextFieldBinding();
		tfb.setReadOnly(textfield.readOnly());

		if (textfield.label().length() > 0)
			tfb.setLabel(textfield.label());

		tfb.setGrabVerticalSpace(textfield.grabVerticalSpace());
		tfb.setRegExp(textfield.regexp());
		tfb.setPassword(textfield.password());
		tfb.setRows(textfield.rows());
		tfb.setOptional(textfield.optional());
		tfb.setWeight(textfield.weight());
		
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
				if (col.imageMethod().length() > 0) {
					cb.setColumnImageMethod(col.imageMethod());
				}
				if (col.textMethod().length() > 0) {
					cb.setColumnTextMethod(col.textMethod());
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
		if (a.imageMethod().length() > 0)
			b.setImageMethod(a.imageMethod());

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
