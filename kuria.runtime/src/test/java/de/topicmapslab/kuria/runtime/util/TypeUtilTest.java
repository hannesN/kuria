package de.topicmapslab.kuria.runtime.util;

import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.collections.ArrayStack;
import org.junit.Test;

import de.topicmapslab.kuria.runtime.PropertyBinding;
import de.topicmapslab.kuria.runtime.util.TypeUtil;
import de.topicmapslab.kuria.runtime.widget.CheckBinding;

@SuppressWarnings("unused")
public class TypeUtilTest {

	private List<String> stringList;
	private ArrayList<String> stringList2;
	private Vector<String> stringList3;
	private ArrayStack stack;

	private Set<Integer> set1;
	private HashSet<Integer> set2;
	@SuppressWarnings("unchecked")
    private HashSet set3;
	@Test
	public void testIsBoolean() {

		assertTrue(TypeUtil.isBoolean(Boolean.class));
		assertTrue(TypeUtil.isBoolean(boolean.class));
		assertFalse(TypeUtil.isBoolean(HashMap.class));
		assertFalse(TypeUtil.isBoolean(String.class));
		assertFalse(TypeUtil.isBoolean(int.class));
	}

	@Test
	public void testIsSet() {
		assertTrue(TypeUtil.isSet(HashSet.class));
		assertTrue(TypeUtil.isSet(Set.class));
		assertFalse(TypeUtil.isSet(HashMap.class));
		assertFalse(TypeUtil.isSet(String.class));
		assertFalse(TypeUtil.isSet(int.class));
	}

	@Test
	public void testIsList() {
		assertTrue(TypeUtil.isList(ArrayList.class));
		assertTrue(TypeUtil.isList(Vector.class));
		assertTrue(TypeUtil.isList(List.class));
		assertTrue(TypeUtil.isList(ArrayStack.class));
		assertFalse(TypeUtil.isList(HashMap.class));
		assertFalse(TypeUtil.isList(String.class));
		assertFalse(TypeUtil.isList(int.class));
	}

	@Test
	public void testIsMap() {
		assertTrue(TypeUtil.isMap(HashMap.class));
		assertTrue(TypeUtil.isMap(Map.class));
		assertFalse(TypeUtil.isMap(HashSet.class));
		assertFalse(TypeUtil.isMap(String.class));
		assertFalse(TypeUtil.isMap(int.class));
	}

	@Test
	public void testIsType() {
		assertTrue(TypeUtil.isType(CheckBinding.class, PropertyBinding.class));
		assertFalse(TypeUtil.isType(CheckBinding.class, String.class));
	}
	
	@Test
	public void testIsDate() {
		assertTrue(TypeUtil.isDate(Date.class));
		assertFalse(TypeUtil.isDate(String.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetContainerType() {
		String[] tmp3 = new String[10];
		assertEquals("Check String array: ", String.class, TypeUtil.getContainerType(tmp3.getClass()));

		String tmp4[] = new String[10];
		assertEquals("Check String array: ", String.class, TypeUtil.getContainerType(tmp4.getClass()));

		try {
			Field field = TypeUtilTest.class.getDeclaredField("stringList");
			assertEquals("Check List: ", String.class, TypeUtil.getContainerType(field.getGenericType()));
			assertTrue("Check Vector: ", TypeUtil.isList(field.getGenericType()));
			
			field = TypeUtilTest.class.getDeclaredField("stringList2");
			assertEquals("Check ArrayListV: ", String.class, TypeUtil.getContainerType(field.getGenericType()));
			assertTrue("Check Vector: ", TypeUtil.isList(field.getGenericType()));
			
			field = TypeUtilTest.class.getDeclaredField("stringList3");
			assertEquals("Check Vector: ", String.class, TypeUtil.getContainerType(field.getGenericType()));
			assertTrue("Check Vector: ", TypeUtil.isList(field.getGenericType()));

			field = TypeUtilTest.class.getDeclaredField("stack");
			assertEquals("Check Stack: ", Object.class, TypeUtil.getContainerType(field.getGenericType()));

			field = TypeUtilTest.class.getDeclaredField("set1");
			assertEquals("Check Sets Parameter: ", Integer.class, TypeUtil.getContainerType(field.getGenericType()));
			assertTrue("Check Set: ", TypeUtil.isSet(field.getGenericType()));
			
			field = TypeUtilTest.class.getDeclaredField("set2");
			assertEquals("Check HashSet: ", Integer.class, TypeUtil.getContainerType(field.getGenericType()));
			assertTrue("Check HashSet: ", TypeUtil.isSet(field.getGenericType()));
			
			field = TypeUtilTest.class.getDeclaredField("set3");
			assertEquals("Check untyped Set: ", Object.class, TypeUtil.getContainerType(field.getGenericType()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		// expecting object because no real reflection possible on var types.
		List<String> tmp = new ArrayList<String>();
		assertEquals("Check ArrayList: ", Object.class, TypeUtil.getContainerType(tmp.getClass()));
		tmp = new ArrayStack();
		assertEquals("Check ArrayList: ", Object.class, TypeUtil.getContainerType(tmp.getClass()));
		tmp = new Vector<String>();
		assertEquals("Check ArrayList: ", Object.class, TypeUtil.getContainerType(tmp.getClass()));

		Set<Integer> tmp2 = new HashSet<Integer>();
		assertEquals("Check HashSet: ", Object.class, TypeUtil.getContainerType(tmp2.getClass()));

		try {
			TypeUtil.getContainerType(String.class);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail("No exception thrown");
	}
	
	@Test
	public void testIsPrimitive() {
		assertTrue(TypeUtil.isPrimitive(boolean.class));
		assertTrue(TypeUtil.isPrimitive(Boolean.class));
		assertTrue(TypeUtil.isPrimitive(byte.class));
		assertTrue(TypeUtil.isPrimitive(Byte.class));
		assertTrue(TypeUtil.isPrimitive(Long.class));
		assertTrue(TypeUtil.isPrimitive(long.class));
		assertTrue(TypeUtil.isPrimitive(Short.class));
		assertTrue(TypeUtil.isPrimitive(short.class));
		assertTrue(TypeUtil.isPrimitive(int.class));
		assertTrue(TypeUtil.isPrimitive(Integer.class));
		assertTrue(TypeUtil.isPrimitive(char.class));
		assertTrue(TypeUtil.isPrimitive(Character.class));
		assertTrue(TypeUtil.isPrimitive(Double.class));
		assertTrue(TypeUtil.isPrimitive(double.class));
		assertTrue(TypeUtil.isPrimitive(Float.class));
		assertTrue(TypeUtil.isPrimitive(float.class));
		assertTrue(TypeUtil.isPrimitive(Double.class));
		assertTrue(TypeUtil.isPrimitive(BigInteger.class));
		assertTrue(TypeUtil.isPrimitive(BigDecimal.class));
	}
}
