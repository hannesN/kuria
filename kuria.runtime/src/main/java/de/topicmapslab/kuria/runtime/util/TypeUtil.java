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
package de.topicmapslab.kuria.runtime.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A helper class containing some methods to work with {@link Type}
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class TypeUtil {

	/**
	 * Checks if the given class represents the boolean datatype or its class.
	 * 
	 * @param clazz
	 *            the class to check
	 * @return <code>true</code> if clazz is instanceof boolean or Boolean
	 */
	public static boolean isBoolean(Type clazz) {
		return ((clazz.equals(boolean.class)) || (clazz.equals(Boolean.class)));
	}

	/**
	 * Checks if the fiven class represents a {@link Date}.
	 * @param clazz the class to check
	 * @return <code>true</code> if clazz equals {@link Date}.class, else <code>false</code>
	 */
	public static boolean isDate(Type clazz) {
		return Date.class.equals(clazz);
	}
	
	/**
	 * Checks if the given parameter represents a type which is either an implementation of
	 * {@link Set} or an subclass of an implementation.
	 * 
	 * @param clazz the class to check
	 * @return <code>true</code> if the class or a supertype of it implements
	 *         {@link Set} or the class is {@link Set}
	 */
	public static boolean isSet(Type clazz) {
		return isType(clazz, Set.class);
	}

	/**
	 * Checks if the given parameter represents a type which is either an implementation of
	 * {@link Map} or an subclass of an implementation.
	 * 
	 * @param clazz the class to check
	 * @return <code>true</code> if the class or a supertype of it implements
	 *         {@link Map} or the class is {@link Map}
	 */
	public static boolean isMap(Type clazz) {
		return isType(clazz, Map.class);
	}

	/**
	 * Checks if the given parameter represents a type which is either an implementation of
	 * {@link List} or an subclass of an implementation.
	 * 
	 * @param clazz the class to check
	 * @return <code>true</code> if the class or a supertype of it implements
	 *         {@link List} or the class is {@link List}
	 */
	public static boolean isList(Type clazz) {
		return isType(clazz, List.class);
	}

	/**
	 * Checks if the given parameter represents a type which is either an implementation of
	 * the given type or an subclass of an implementation.
	 * 
	 * @param clazz the class to check
	 * @param type the type which is checked
	 * @return <code>true</code> if the class or a supertype of it implements
	 *         the class of type or the class is the type
	 */
	public static boolean isType(Type clazz, Class<?> type) {
		if (clazz == null)
			return false;

		Class<?> tmp;
		if (clazz instanceof ParameterizedType) {
			tmp = (Class<?>) ((ParameterizedType)clazz).getRawType();
		} else {
			tmp = (Class<?>) clazz;
		}
		
		if (tmp.equals(type))
			return true;
		
		for (Class<?> c : tmp.getInterfaces()) {
			if (c.equals(type))
				return true;
		}

		if (tmp.getSuperclass() != null)
			return isType(tmp.getSuperclass(), type);

		return false;
	}

	/**
	 * Checks if the type represents an array.
	 * 
	 * @param clazz  the type to check
	 * @return <code>true</code> if clazz represents an array
	 */
	public static boolean isArray(Type clazz) {
		if (clazz == null)
			return false;
		if (clazz instanceof Class<?>)
			return ((Class<?>) clazz).isArray();
		return false;
	}

	/**
	 * 
	 * @param type
	 * @return the type of the collection parameter or array component
	 * @throws IllegalArgumentException
	 *             if the parameter is neither an array, set or list
	 */
	public static Class<?> getContainerType(Type type) {
		Class<?> clazz = null;
		if (type instanceof Class<?>)
			clazz = (Class<?>) type;

		if (isArray(clazz))
			return (clazz).getComponentType();

		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			Type[] args = pt.getActualTypeArguments();
			for (Type typeArg : args) {
				if (typeArg instanceof Class<?>)
					return (Class<?>) typeArg;
			}
		}
		// return Object if we have a collection but can't get the parameter
		// type
		if ((isList(type)) || (isSet(type)))
			return Object.class;

		// no array, no set, no list -> exception
		throw new IllegalArgumentException("Class  is neither an array nor a collection");

	}

	/**
	 * Checks if the given type represents a collection or an array. 
	 * @param type the type to check
	 * @return <code>false</code> if type is an collection or array, <code>true</code> else
	 */
	public static boolean isNoCollection(Type type) {
		if (isArray(type))
			return false;
		if (isSet(type))
			return false;
		if (isList(type))
			return false;
		if (isMap(type))
			return false;
		return true;
	}
	
	/**
	 * Checks if the given class represents a primitive type or its class
	 * wrapper.
	 * 
	 * <p>
	 * The classes which return true are:
	 * <ul>
	 * <li>Boolean</li>
	 * <li>Byte</li>
	 * <li>Long</li>
	 * <li>Short</li>
	 * <li>Integer</li>
	 * <li>Character</li>
	 * <li>Double</li>
	 * <li>Float</li>
	 * <li>Double</li>
	 * <li>BigInteger</li>
	 * <li>BigDecimal</li>
	 * <li>And all classes primitives</li>
	 * </ul>
	 * </p>
	 * 
	 * @param clazz
	 *            the class to check
	 * @return <code>true</code> if class represents a primitive type or its
	 *         class wrapper
	 */
	public static boolean isPrimitive(Type clazz) {
		return ((isType(clazz, Number.class)) || (clazz.equals(Character.class)) || (clazz.equals(Boolean.class)) || 
				((clazz instanceof Class<?>) && (((Class<?>) clazz).isPrimitive())) );
	}
}
