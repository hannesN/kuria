/**
 * 
 */
package de.topicmapslab.kuria.runtime.widget;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.topicmapslab.kuria.runtime.PropertyBinding;
import de.topicmapslab.kuria.runtime.util.TypeUtil;

/**
 * A {@link ListBinding} is used to bind properties which are collections.
 * <p>
 * The binding has a style which tells the UI generator how to visualize the collection.
 * These styles are explained in {@link ListStyle}.
 * </p>
 * <p>
 * If a collection is used, mostly the interfaces of the collection is used. To create a new value in the 
 * UI it is necessary to tell the binding which implementation should be used. If non is set {@link HashSet} is
 * used for {@link Set}s and {@link ArrayList} is used for a {@link List} type. 
 * </p>
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class ListBinding extends PropertyBinding implements IListBinding {

	private ListStyle listStyle = ListStyle.COMPACT;
	
	private Class<?> collectionImplementation;
	
	private boolean createNew = false;
	/**
	 *  {@inheritDoc}
	 */
	public ListStyle getListStyle() {
		return listStyle;
	}

	/**
	 * Sets the list style of this {@link ListBinding}
	 * @param listStyle the new list style
	 */
	public void setListStyle(ListStyle listStyle) {
		this.listStyle = listStyle;
	}

	/**
	 *  {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Object instance, Object value) throws IllegalAccessException, InvocationTargetException,
	        NoSuchMethodException, IllegalArgumentException {

		if (TypeUtil.isArray(value.getClass())) {
			if (TypeUtil.isArray(getType())) {
				super.setValue(instance, value);
			} else if ((TypeUtil.isSet(getType())) || (TypeUtil.isList(getType()))) {
				try {
					Class<?> colImpl = getCollectionImplementation();
					Collection<Object> collection = (Collection<Object>) colImpl.getConstructor().newInstance();
					for (Object o : (Object[]) value) {
						collection.add(o);
					}
					super.setValue(instance, collection);
					return;
				} catch (Exception e) {
					throw new IllegalArgumentException("Cant convert value type to binding type");
				}
			}
		}

		super.setValue(instance, value);
	}
	/**
	 * Sets the collection implementation used for this values.
	 * <p>Do not set the {@link #collectionImplementation} to null after calling
	 * {@link #setType(Type)}. If no specific type is set, {@link #setType(Type)} sets one.
	 * @param collectionImplementation type of the collection implementation
	 */
	public void setCollectionImplementation(Class<?> collectionImplementation) {
	    this.collectionImplementation = collectionImplementation;
    }
	
	/**
	 * {@inheritDoc}
	 * 
	 * <p>If the {@link #collectionImplementation} is <code>null</code> 
	 * and the type class is an interface or abstract class it is set according to the interface
	 * which is implemented. You might reset it with {@link #setCollectionImplementation(Class)} but never set
	 * it to <code>null</code>.
	 * </p>
	 * <p>
	 * If the type  is an instanceable class {@link #collectionImplementation} is equal to the type.
	 * </p> 
	 */
	@Override
	public void setType(Type type) {
		if (collectionImplementation==null) {
			Class<?> typeClass = null;
			if (type instanceof Class<?>) {
				typeClass = (Class<?>) type;
			} else if (type instanceof ParameterizedType) {
				typeClass = (Class<?>) ((ParameterizedType)type).getRawType();
			}
			if ( (typeClass.isInterface()) || ((typeClass.getModifiers()&Modifier.ABSTRACT)!=0) ) {
				if (TypeUtil.isList(typeClass))
					collectionImplementation = ArrayList.class;
				else if (TypeUtil.isSet(typeClass))
					collectionImplementation = HashSet.class;
			}
		}
	    super.setType(type);
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public Class<?> getCollectionImplementation() {
	    return collectionImplementation;
    }
	
	/**
	 * Sets the flag if a create-new button should be created.
	 * @param createNew the new value of the flag
	 */
	public void setCreateNew(boolean createNew) {
	    this.createNew = createNew;
    }
	
	/**
	 *  {@inheritDoc}
	 */
	public boolean isCreateNew() {
	    return createNew;
    }
}
