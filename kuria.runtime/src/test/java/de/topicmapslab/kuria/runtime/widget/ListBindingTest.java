package de.topicmapslab.kuria.runtime.widget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.topicmapslab.kuria.runtime.model.Address;
import de.topicmapslab.kuria.runtime.model.File;
import de.topicmapslab.kuria.runtime.model.Person;
import de.topicmapslab.kuria.runtime.widget.ListBinding;
import de.topicmapslab.kuria.runtime.widget.ListStyle;

public class ListBindingTest {

	// map from testname to field
	private static Map<String, ListBinding> listBinding = new HashMap<String, ListBinding>();

	private File file;
	
	
	@BeforeClass
	public static void tearUp() {
		for (Field f : File.class.getDeclaredFields()) {
			ListBinding l = new ListBinding();
			l.setFieldName(f.getName());
			l.setListStyle(ListStyle.COMPACT);
			l.setType(f.getGenericType());
			l.setReadOnly(false);
			listBinding.put(f.getName(), l);
		}
	}
	
	@Before
	public void setUp() {
		file = new File();
		
	}
	
	@Test
	public void testPersons() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ListBinding l = listBinding.get("persons");
		
		assertTrue(l.isCollection());
		assertFalse(l.isArray());
		assertEquals(Person.class, l.getElementType());
		
		assertEquals(HashSet.class, l.getCollectionImplementation());
		assertNull(l.getValue(file));
		
		file.setPersons(new HashSet<Person>());
		assertNotNull(l.getValue(file));
		
		assertTrue("Check if result is a set", l.getValue(file) instanceof Set<?>);
		assertTrue("Check if result is a HashSet", l.getValue(file) instanceof HashSet<?>);
		
		file.setPersons(null);
		assertNull("Reset of value", l.getValue(file));
		
		l.setValue(file, new HashSet<Person>());
		assertTrue("Check if result is a set", l.getValue(file) instanceof Set<?>);
		assertTrue("Check if result is a HashSet", l.getValue(file) instanceof HashSet<?>);
		
		assertEquals("Check size of result ", 0, ((Set<?>)l.getValue(file)).size());
		
		
		Person p[] = {new Person(), new Person(), new Person(), new Person(), new Person()};
		l.setValue(file, p);
		assertTrue("Check if result is a set", l.getValue(file) instanceof Set<?>);
		assertEquals("Check size of result ", 5, ((Set<?>)l.getValue(file)).size());
	}
	
	@Test
	public void testAddresses() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ListBinding l = listBinding.get("addresses");
		
		assertFalse(l.isCollection());
		assertTrue(l.isArray());
		assertEquals(Address.class, l.getElementType());
		
		assertNull(l.getValue(file));
		
		
		file.setAddresses(new Address[]{});
		assertNotNull(l.getValue(file));
		assertTrue("Check if result is a set", l.getValue(file).getClass().isArray());
		assertEquals("Array size:" , 0, ((Address[])l.getValue(file)).length);
		
		file.setAddresses(new Address[]{new Address()});
		assertNotNull(l.getValue(file));
		assertTrue("Check if result is a set", l.getValue(file).getClass().isArray());
		assertEquals("Array size:" , 1, ((Address[])l.getValue(file)).length);
		

		Address a[] = {new Address(), new Address(), new Address()};
		l.setValue(file, a);
		assertTrue("Check if result is a set", l.getValue(file).getClass().isArray());
		assertEquals("Array size:" , 3, ((Address[])l.getValue(file)).length);
	}

	@Test
	public void testParents() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ListBinding l = listBinding.get("parents");
		
		assertTrue(l.isCollection());
		assertFalse(l.isArray());
		assertEquals(Person.class, l.getElementType());
		
		assertEquals(ArrayList.class, l.getCollectionImplementation());
		assertNull(l.getValue(file));
		
		file.setParents(new ArrayList<Person>());
		assertNotNull(l.getValue(file));
		
		assertTrue("Check if result is a List", l.getValue(file) instanceof List<?>);
		assertTrue("Check if result is a ArrayList", l.getValue(file) instanceof ArrayList<?>);
		
		file.setParents(null);
		assertNull("Reset of value", l.getValue(file));
		
		l.setValue(file, new ArrayList<Person>());
		assertTrue("Check if result is a List", l.getValue(file) instanceof List<?>);
		assertTrue("Check if result is a ArrayList", l.getValue(file) instanceof ArrayList<?>);
		
		assertEquals("Check size of result ", 0, ((List<?>)l.getValue(file)).size());
		
		
		Person p[] = {new Person(), new Person(), new Person(), new Person(), new Person()};
		l.setValue(file, p);
		assertTrue("Check if result is a set", l.getValue(file) instanceof List<?>);
		assertEquals("Check size of result ", 5, ((List<?>)l.getValue(file)).size());
	}
}
