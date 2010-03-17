package de.topicmapslab.kuria.annotation;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import de.topicmapslab.kuria.annotation.AnnotationBindingFactory;
import de.topicmapslab.kuria.annotation.data.Address;
import de.topicmapslab.kuria.annotation.data.Person;


public abstract class AbstractBindingTest {

	protected static AnnotationBindingFactory fac;

	public AbstractBindingTest() {
		super();
	}

	@BeforeClass
    public static void setUp() throws Exception {
    	fac = new AnnotationBindingFactory();
    	fac.addClass(Person.class);
    	fac.addClass(Address.class);
    }

	@AfterClass
    public static void tearDown() throws Exception {
    }

}