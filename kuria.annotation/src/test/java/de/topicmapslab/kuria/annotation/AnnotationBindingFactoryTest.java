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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import de.topicmapslab.kuria.annotation.data.Address;
import de.topicmapslab.kuria.annotation.data.NamedObject;
import de.topicmapslab.kuria.annotation.data.Person;
import de.topicmapslab.kuria.annotation.data.Telephone;
import de.topicmapslab.kuria.annotation.data.WrappedPerson;
import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.tree.IChildrenBinding;
import de.topicmapslab.kuria.runtime.tree.ITreeNodeBinding;
import de.topicmapslab.kuria.runtime.widget.CheckBinding;
import de.topicmapslab.kuria.runtime.widget.ComboBinding;
import de.topicmapslab.kuria.runtime.widget.DateBinding;
import de.topicmapslab.kuria.runtime.widget.DirectoryBinding;
import de.topicmapslab.kuria.runtime.widget.FileBinding;
import de.topicmapslab.kuria.runtime.widget.GroupBinding;
import de.topicmapslab.kuria.runtime.widget.ICheckBinding;
import de.topicmapslab.kuria.runtime.widget.IEditableBinding;
import de.topicmapslab.kuria.runtime.widget.ListBinding;
import de.topicmapslab.kuria.runtime.widget.TextFieldBinding;

public class AnnotationBindingFactoryTest extends AbstractBindingTest {

	@Test
	public void testGetBindingContainer() {
		IBindingContainer bc = fac.getBindingContainer();
		try {
			assertEquals("Check table bindings", 3, bc.getTableBindings().values().size());
			assertEquals("Check editable bindings", 5, bc.getEditableBindings().values().size());
			assertEquals("Check tree bindings", 2, bc.getTreeNodeBindings().values().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testPersonBindings() {
		try {
			IBindingContainer bc = fac.getBindingContainer();
			Address address = getAddress();

			Person p = new Person();
			p.setLastname("Meyer");
			p.setFirstname("Hans");
			p.setAddress(address);

			Person p2 = new Person();
			p2.setLastname("Meyer");
			p2.setFirstname("Hans junior");
			p2.setAddress(address);

			p.addChild(p2);

			assertNotNull(bc.getTextBinding(Person.class));
			assertEquals("Meyer, Hans", bc.getTextBinding(Person.class).getText(p));

			ITreeNodeBinding tnb = bc.getTreeNodeBinding(p.getClass());
			Assert.assertNotNull(tnb);

			Assert.assertNotNull("Children not null: " + tnb.getChildren());
			
			Assert.assertEquals("Children: ", 2, tnb.getChildren().size());
			
			for (IChildrenBinding c : tnb.getChildren()) {
				System.out.println(c.getType());
			}

			int textFieldCounter = 0;
			int comboCounter = 0;
			int groupCounter = 0;
			int checkCounter = 0;
			int listCounter = 0;
			int dateCounter = 0;
			int fileCounter = 0;
			int dirCounter = 0;
			for (IPropertyBinding pb : bc.getEditableBinding(Person.class).getPropertieBindings()) {
				if (pb instanceof TextFieldBinding)
					textFieldCounter++;
				if (pb instanceof ComboBinding)
					comboCounter++;
				if (pb instanceof GroupBinding)
					groupCounter++;
				if (pb instanceof CheckBinding)
					checkCounter++;
				if (pb instanceof ListBinding)
					listCounter++;
				if (pb instanceof DateBinding)
					dateCounter++;
				if (pb instanceof DirectoryBinding)
					dirCounter++;
				if (pb instanceof FileBinding)
					fileCounter++;
			}

			
			assertEquals("Number of ComboBindings", 1, comboCounter);
			assertEquals("Number of GroupBindings", 1, groupCounter);
			assertEquals("Number of CheckBindings", 1, checkCounter);
			assertEquals("Number of DateBindings", 1, dateCounter);
			assertEquals("Number of ListBindings", 2, listCounter);
			assertEquals("Number of DirectoryBindings", 1, dirCounter);
			assertEquals("Number of FileBindings", 2, fileCounter);
			assertEquals("Number of TextfieldBindings:", 3, textFieldCounter);

			for (IPropertyBinding pb : bc.getEditableBinding(Person.class).getPropertieBindings()) {
				if (pb instanceof ComboBinding) {
					assertEquals("Check createNew", false, ((ComboBinding) pb).isShowNewButton());
				}
				
				if (pb instanceof FileBinding) {
					if (pb.getFieldName().equals("image")) {
						assertTrue(((FileBinding) pb).isLoad());
					} else {
						assertFalse(((FileBinding) pb).isLoad());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testWrappedPersonBindings() {
		try {
			IBindingContainer bc = fac.getBindingContainer();
			Address address = getAddress();

			WrappedPerson p = new WrappedPerson();
			p.setLastname("Meyer");
			p.setFirstname("Hans");
			p.setAddress(address);

			Person p2 = new Person();
			p2.setLastname("Meyer");
			p2.setFirstname("Hans junior");
			p2.setAddress(address);

			p.addChild(p2);

			assertNotNull(bc.getTextBinding(WrappedPerson.class));
			assertEquals("Meyer, Hans", bc.getTextBinding(WrappedPerson.class).getText(p));

			ITreeNodeBinding tnb = bc.getTreeNodeBinding(p.getClass());
			Assert.assertNotNull(tnb);

			Assert.assertNotNull("Children not null: " + tnb.getChildren());
			
			Assert.assertEquals("Children: ", 1, tnb.getChildren().size());
			
			for (IChildrenBinding c : tnb.getChildren()) {
				System.out.println(c.getType());
			}

			

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testAddressBindings() {
		try {
			IBindingContainer bc = fac.getBindingContainer();

			Address a = getAddress();

			Assert.assertNull(bc.getTreeNodeBinding(a.getClass()));
			Assert.assertEquals("Checking number of column binding", 4, bc.getTableBinding(a.getClass())
			        .getColumnBindings().size());
			Assert.assertNotNull("Check if Textbinding exists", bc.getTextBinding(a.getClass()));

			assertEquals("Check text value", a.getText(), bc.getTextBinding(a.getClass()).getText(a));

		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	private Address getAddress() {
		Address a = new Address();
		a.setCity("Musterstadt");
		a.setNumber("132a");
		a.setPlz("00123");
		a.setStreet("Musterstraße");
		return a;
	}

	
	@Test
	public void countWrappedPersonBindings() {
		IBindingContainer bc = fac.getBindingContainer();
		int textFieldCounter = 0;
		int comboCounter = 0;
		int groupCounter = 0;
		int checkCounter = 0;
		int listCounter = 0;
		int dateCounter = 0;
		int fileCounter = 0;
		int dirCounter = 0;
		for (IPropertyBinding pb : bc.getEditableBinding(WrappedPerson.class).getPropertieBindings()) {
			if (pb instanceof TextFieldBinding)
				textFieldCounter++;
			if (pb instanceof ComboBinding)
				comboCounter++;
			if (pb instanceof GroupBinding)
				groupCounter++;
			if (pb instanceof CheckBinding)
				checkCounter++;
			if (pb instanceof ListBinding)
				listCounter++;
			if (pb instanceof DateBinding)
				dateCounter++;
			if (pb instanceof DirectoryBinding)
				dirCounter++;
			if (pb instanceof FileBinding)
				fileCounter++;
		}

		
		assertEquals("Number of ComboBindings", 1, comboCounter);
		assertEquals("Number of GroupBindings", 1, groupCounter);
		assertEquals("Number of CheckBindings", 1, checkCounter);
		assertEquals("Number of DateBindings", 1, dateCounter);
		assertEquals("Number of ListBindings", 2, listCounter);
		assertEquals("Number of DirectoryBindings", 1, dirCounter);
		assertEquals("Number of FileBindings", 2, fileCounter);
		assertEquals("Number of TextfieldBindings:", 3, textFieldCounter);

		for (IPropertyBinding pb : bc.getEditableBinding(WrappedPerson.class).getPropertieBindings()) {
			if (pb instanceof ComboBinding) {
				assertEquals("Check createNew", false, ((ComboBinding) pb).isShowNewButton());
			}
			
			if (pb instanceof FileBinding) {
				if (pb.getFieldName().equals("image")) {
					assertTrue(((FileBinding) pb).isLoad());
				} else {
					assertFalse(((FileBinding) pb).isLoad());
				}
			}
		}
	}

	@Test
	public void testInheritance() {
		IBindingContainer bc = fac.getBindingContainer();
		IEditableBinding eb = bc.getEditableBinding(NamedObject.class);
		assertNotNull(eb);
		assertNull(eb.getParentBinding());
		assertEquals(1, eb.getPropertieBindings().size());
		
		eb = bc.getEditableBinding(Telephone.class);
		assertNotNull(eb);
		assertNotNull(eb.getParentBinding());
		assertEquals(1, eb.getParentBinding().getPropertieBindings().size());
		for (IPropertyBinding pb : eb.getPropertieBindings()) {
			if (pb instanceof ICheckBinding)
				// in class annotated with weight 0 but overridden by annox xml file
				assertEquals(1,  pb.getWeight());
		}
		assertEquals(4, eb.getPropertieBindings().size());
	}
}
