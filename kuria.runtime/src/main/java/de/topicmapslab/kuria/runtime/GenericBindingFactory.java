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
package de.topicmapslab.kuria.runtime;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.topicmapslab.kuria.runtime.table.ColumnBinding;
import de.topicmapslab.kuria.runtime.table.TableBinding;
import de.topicmapslab.kuria.runtime.util.TypeUtil;
import de.topicmapslab.kuria.runtime.widget.CheckBinding;
import de.topicmapslab.kuria.runtime.widget.ComboBinding;
import de.topicmapslab.kuria.runtime.widget.DateBinding;
import de.topicmapslab.kuria.runtime.widget.EditableBinding;
import de.topicmapslab.kuria.runtime.widget.ListBinding;
import de.topicmapslab.kuria.runtime.widget.ListStyle;
import de.topicmapslab.kuria.runtime.widget.TextFieldBinding;

/**
 * This factory generates bindings based on the reflection information of the
 * given classes.
 * <p>
 * All generated bindings are generated by default assumptions, for instance:
 * <ul>
 * <li>Instances of other model classes will be bound in an {@link ComboBinding}
 * </li>
 * <li>Arrays, Lists and Collections will be bound to {@link ListBinding} with
 * its {@link ListStyle} set to <b>Compact</b></li>
 * <li>All TextBindings use the toString method</li>
 * <li>Strings and numbers are bound to a TextFieldBinding</li>
 * <li>Boolean fields will be bound to a {@link CheckBinding}</li>
 * <li>For every class a {@link TableBinding} with {@link ColumnBinding} for
 * every element which is not an array or collection will be created</li>
 * </p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class GenericBindingFactory implements IBindingFactory {

	private BindingContainer bindingContainer;

	private List<Class<?>> classes = null;

	/**
	 * {@inheritDoc}
	 */
	public IBindingContainer getBindingContainer() {
		if (bindingContainer == null)
			init();
		return bindingContainer;
	}

	/**
	 * Adds a class which binding is needed.
	 * @param clazz 
	 */
	public void addClass(Class<?> clazz) {
    	if (classes == null)
    		classes = new ArrayList<Class<?>>();
    	classes.add(clazz);
    }

	/**
	 * Removes a class from the list of to bind classes
	 * @param clazz
	 */
	public void removeClass(Class<?> clazz) {
    	if (classes != null) {
    		classes.remove(clazz);
    	}
    }

	/**
	 * All classes in the given package name will be added to the to bind list.
	 * @param packageName a package name in the classpath
	 */
	public void addPackage(String packageName) {
    	throw new UnsupportedOperationException("Not implemented yet");
    }

	private void init() {
		bindingContainer = new BindingContainer();
		for (Class<?> c : getClasses()) {
			bindingContainer.putEditableBindings(c, createEditableBinding(c));
			bindingContainer.putTableBindings(c, createGenericTableBinding(c));
			bindingContainer.putTextBinding(c, createGenericTextBinding(c));
		}
	}

	/** 
	 * Just creates a {@link TextBinding} for the {@link BindingContainer}.
	 * These bindings will all use the toString methode.
	 * @param c the class which gets a {@link TextBinding}
	 * @return the instance of the classes {@link TextBinding}
	 */
	private TextBinding createGenericTextBinding(Class<?> c) {
		TextBinding tb = new TextBinding();
		return tb;
	}

	private TableBinding createGenericTableBinding(Class<?> c) {
		TableBinding tb = new TableBinding();
		for (Field f : c.getDeclaredFields()) {
			Type type = f.getGenericType();
			if ((TypeUtil.isPrimitive(type)) || TypeUtil.isNoCollection(type)) {
				ColumnBinding col = new ColumnBinding();
				col.setFieldName(f.getName());
				tb.addColumnBinding(col);
			}

		}
		return tb;
	}

	private EditableBinding createEditableBinding(Class<?> c) {
		EditableBinding eb = new EditableBinding();

		for (Field f : c.getDeclaredFields()) {
			PropertyBinding pb = findBinding(f);
			pb.setFieldName(f.getName());
			pb.setType(f.getGenericType());
			eb.addPropertyBinding(pb);
		}

		return eb;
	}

	protected PropertyBinding findBinding(Field f) {
		if (TypeUtil.isBoolean(f.getType())) {
			return createGenericCheckBinding(f);
		}
		
		if (TypeUtil.isDate(f.getType())) {
			return createGenericDateBinding(f);
		}

		if (TypeUtil.isSet(f.getType())) {
			return createGenericListBinding(f);
		}

		if (TypeUtil.isList(f.getType())) {
			return createGenericListBinding(f);
		}

		if (TypeUtil.isMap(f.getType())) {
			// TODO property binding
			return createGenericTextFieldBinding(f);
		}

		if ((!TypeUtil.isPrimitive(f.getType())) && (!String.class.equals(f.getType()))) {
			return createGenericComboBinding(f);
		}

		return createGenericTextFieldBinding(f);
	}

	private PropertyBinding createGenericListBinding(Field f) {
		ListBinding lb = new ListBinding();
		lb.setReadOnly(false);
		return lb;
	}

	private TextFieldBinding createGenericTextFieldBinding(Field f) {
		TextFieldBinding tfb = new TextFieldBinding();

		tfb.setReadOnly(false);
		tfb.setPassword(false);
		tfb.setType(f.getGenericType());

		String regExp = ".*";
		if (f.getType() == Integer.class) {
			regExp = "\\d*";
		} else if (f.getType() == Float.class) {
			regExp = "\\d*(\\.\\d)*";
		}

		tfb.setRegExp(regExp);
		return tfb;
	}

	private PropertyBinding createGenericCheckBinding(Field f) {
		CheckBinding cb = new CheckBinding();
		cb.setReadOnly(false);
		return cb;
	}

	private PropertyBinding createGenericComboBinding(Field f) {
		ComboBinding cb = new ComboBinding();
		cb.setReadOnly(false);
		return cb;
	}
	
	private PropertyBinding createGenericDateBinding(Field f) {
		DateBinding db = new DateBinding();
		db.setReadOnly(false);
		return db;
	}

	protected List<Class<?>> getClasses() {
		if (classes == null)
			return Collections.emptyList();
		return classes;
	}
}
