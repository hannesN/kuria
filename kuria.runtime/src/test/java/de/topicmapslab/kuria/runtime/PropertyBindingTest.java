package de.topicmapslab.kuria.runtime;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.topicmapslab.kuria.runtime.PropertyBinding;

@SuppressWarnings("unused")
public class PropertyBindingTest {

	@SuppressWarnings("unchecked")
    private Set set;
	private static Field setField;
	
    private List<String> list;
	private static Field listField;

	private String[] array;
	private static Field arrayField;

	private int testInt;
	private static Field testIntField;

	private PropertyBinding pb;

	@BeforeClass
	public static void gloabelSetup() throws Exception {
		listField = PropertyBindingTest.class.getDeclaredField("list");
		arrayField = PropertyBindingTest.class.getDeclaredField("array");
		testIntField = PropertyBindingTest.class.getDeclaredField("testInt");
		setField = PropertyBindingTest.class.getDeclaredField("set");
	}

	@Before
	public void setUp() throws Exception {
		pb = new PropertyBinding() {
		};
	}

	@Test
	public void testGetLabel() {
		assertNull(pb.getFieldName());
		assertNull(pb.getLabel());

		pb.setFieldName(listField.getName());
		assertEquals(listField.getName(), pb.getFieldName());

		assertEquals("List", pb.getLabel());
	}

	@Test
	public void testSetLabel() {
		assertNull(pb.getLabel());
		String label = "Test Liste";
		pb.setLabel(label);
		assertEquals(label, pb.getLabel());
	}

	@Test
	public void testIsReadOnly() {
		assertFalse(pb.isReadOnly());
	}

	@Test
	public void testSetReadOnly() {
		assertFalse(pb.isReadOnly());
		pb.setReadOnly(true);
		assertTrue(pb.isReadOnly());
	}

	@Test
	public void testSetFieldName() {
		assertNull(pb.getFieldName());
		assertNull(pb.getLabel());

		pb.setFieldName(listField.getName());
		assertEquals(listField.getName(), pb.getFieldName());
	}

	@Test
	public void testSetType() {
		assertNull(pb.getType());
		pb.setType(listField.getGenericType());
		assertEquals(listField.getGenericType(), pb.getType());
	}

	@Test
	public void testGetValue() {
	}

	@Test
	public void testSetValue() {
	}

	@Test
	public void testIsArray() {
		assertNull(pb.getType());
		pb.setType(listField.getGenericType());
		assertEquals(listField.getGenericType(), pb.getType());
		assertFalse(pb.isArray());
		
		pb.setType(arrayField.getGenericType());
		assertEquals(arrayField.getGenericType(), pb.getType());
		assertTrue(pb.isArray());
		
		pb.setType(testIntField.getGenericType());
		assertEquals(testIntField.getGenericType(), pb.getType());
		assertFalse(pb.isArray());
		
	}

	@Test
	public void testIsCollection() {
		assertNull(pb.getType());
		pb.setType(listField.getGenericType());
		assertEquals(listField.getGenericType(), pb.getType());
		assertTrue(pb.isCollection());
		
		pb.setType(setField.getGenericType());
		assertEquals(setField.getGenericType(), pb.getType());
		assertTrue(pb.isCollection());
		
		pb.setType(arrayField.getGenericType());
		assertEquals(arrayField.getGenericType(), pb.getType());
		assertFalse(pb.isCollection());
		
		pb.setType(testIntField.getGenericType());
		assertEquals(testIntField.getGenericType(), pb.getType());
		assertFalse(pb.isCollection());
	}

	@Test
	public void testGetElementType() {
		assertNull(pb.getType());
		pb.setType(listField.getGenericType());
		assertEquals(listField.getGenericType(), pb.getType());
		assertEquals(String.class, pb.getElementType());
		
		pb.setType(arrayField.getGenericType());
		assertEquals(arrayField.getGenericType(), pb.getType());
		assertEquals(String.class, pb.getElementType());
		
		pb.setType(testIntField.getGenericType());
		assertEquals(testIntField.getGenericType(), pb.getType());
		assertNull(pb.getElementType());
		
		pb.setType(setField.getGenericType());
		assertEquals(setField.getGenericType(), pb.getType());
		assertEquals(Object.class, pb.getElementType());
	}

}
