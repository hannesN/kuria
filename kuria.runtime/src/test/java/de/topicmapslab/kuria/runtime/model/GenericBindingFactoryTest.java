/**
 * 
 */
package de.topicmapslab.kuria.runtime.model;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.topicmapslab.kuria.runtime.GenericBindingFactory;
import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.widget.ICheckBinding;
import de.topicmapslab.kuria.runtime.widget.IComboBinding;
import de.topicmapslab.kuria.runtime.widget.IEditableBinding;
import de.topicmapslab.kuria.runtime.widget.IGroupBinding;
import de.topicmapslab.kuria.runtime.widget.IListBinding;
import de.topicmapslab.kuria.runtime.widget.ITextFieldBinding;

/**
 * @author bosso
 *
 */
public class GenericBindingFactoryTest {

	private static GenericBindingFactory fac;

	@BeforeClass
    public static void setUp() throws Exception {
    	fac = new GenericBindingFactory();
    	fac.addClass(Person.class);
    	fac.addClass(Address.class);
    }

	@AfterClass
    public static void tearDown() throws Exception {
    }

	@Test
	public void testPersonBindings() {
		try {
			IBindingContainer bc = fac.getBindingContainer();
			
			int textFieldCounter = 0;
			int comboCounter = 0;
			int groupCounter = 0;
			int checkCounter = 0;
			int listCounter = 0;
			for (IPropertyBinding pb : bc.getEditableBinding(Person.class).getPropertieBindings()) {
				if (pb instanceof ITextFieldBinding)
					textFieldCounter++;
				if (pb instanceof IComboBinding)
					comboCounter++;
				if (pb instanceof IGroupBinding)
					groupCounter++;
				if (pb instanceof ICheckBinding)
					checkCounter++;
				if (pb instanceof IListBinding)
					listCounter++;
			}
			
			assertEquals("Number of TextfieldBindings:", 3, textFieldCounter);
			
			assertEquals("Number of ComboBindings", 2, comboCounter);
			
			assertEquals("Number of GroupBindings", 0, groupCounter);
			
			assertEquals("Number of ListBindings", 2, listCounter);
			
			assertEquals("Number of CheckBindings", 1, checkCounter);
			
			for (IPropertyBinding pb : bc.getEditableBinding(Person.class).getPropertieBindings()) {
				if (pb instanceof IComboBinding) {
					assertEquals("Check createNew", false, ((IComboBinding) pb).isShowNewButton());
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
			Address a = getAddress();
			
			IEditableBinding eb = fac.getBindingContainer().getEditableBinding(a.getClass());
			
			for (IPropertyBinding pb : eb.getPropertieBindings()) {
				if (pb.getFieldName().equals("city"))
					assertEquals(a.getCity(), pb.getValue(a));
				if (pb.getFieldName().equals("number"))
					assertEquals(a.getNumber(), pb.getValue(a));
				if (pb.getFieldName().equals("zipcode"))
					assertEquals(a.getZipcode(), pb.getValue(a));
				if (pb.getFieldName().equals("street"))
					assertEquals(a.getStreet(), pb.getValue(a));
			}
			
			
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	private Address getAddress() {
	    Address a = new Address();
	    a.setCity("Musterstadt");
	    a.setNumber("132a");
	    a.setZipcode("00123");
	    a.setStreet("Musterstraße");
	    return a;
    }


}
