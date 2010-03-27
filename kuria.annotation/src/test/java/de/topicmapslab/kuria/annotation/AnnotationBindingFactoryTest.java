package de.topicmapslab.kuria.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import de.topicmapslab.kuria.annotation.data.Address;
import de.topicmapslab.kuria.annotation.data.Person;
import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.tree.ITreeNodeBinding;
import de.topicmapslab.kuria.runtime.widget.CheckBinding;
import de.topicmapslab.kuria.runtime.widget.ComboBinding;
import de.topicmapslab.kuria.runtime.widget.DateBinding;
import de.topicmapslab.kuria.runtime.widget.GroupBinding;
import de.topicmapslab.kuria.runtime.widget.ListBinding;
import de.topicmapslab.kuria.runtime.widget.TextFieldBinding;

public class AnnotationBindingFactoryTest extends AbstractBindingTest {

	@Test
	public void testGetBindingContainer() {
		IBindingContainer bc = fac.getBindingContainer();
		try {
			assertEquals("Check table bindings", 2, bc.getTableBindings().values().size());
			assertEquals("Check editable bindings", 2, bc.getEditableBindings().values().size());
			assertEquals("Check tree bindings", 1, bc.getTreeNodeBindings().values().size());
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

			int textFieldCounter = 0;
			int comboCounter = 0;
			int groupCounter = 0;
			int checkCounter = 0;
			int listCounter = 0;
			int dateCounter = 0;
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
			}

			assertEquals("Number of TextfieldBindings:", 3, textFieldCounter);
			assertEquals("Number of ComboBindings", 1, comboCounter);
			assertEquals("Number of GroupBindings", 1, groupCounter);
			assertEquals("Number of CheckBindings", 1, checkCounter);
			assertEquals("Number of DateBindings", 1, dateCounter);
			assertEquals("Number of ListBindings", 2, listCounter);

			for (IPropertyBinding pb : bc.getEditableBinding(Person.class).getPropertieBindings()) {
				if (pb instanceof ComboBinding) {
					assertEquals("Check createNew", false, ((ComboBinding) pb).isShowNewButton());
				}
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

}
