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
