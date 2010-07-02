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
package kuria.test.edit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;

import kuria.test.model.FloatTestModel;

import org.junit.Test;

import de.topicmapslab.kuria.annotation.AnnotationBindingFactory;
import de.topicmapslab.kuria.runtime.IBindingContainer;
import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.widget.IEditableBinding;

/**
 * @author niederhausen
 *
 */

public class FloatTest {

	@Test
	public void testFloat() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		AnnotationBindingFactory fac = new AnnotationBindingFactory();
		fac.addClass(FloatTestModel.class);
		
		IBindingContainer bc = fac.getBindingContainer();
		
		IEditableBinding eb = bc.getEditableBinding(FloatTestModel.class);
		assertNotNull(eb);
		assertEquals(1, eb.getPropertieBindings().size());
		
		IPropertyBinding pb = eb.getPropertieBindings().get(0);
		
		FloatTestModel model = new FloatTestModel();
		
		
		float x = (Float) pb.getValue(model);
		assertEquals(0, x, 0);
		
		pb.setValue(model, 2.5f);
		x = (Float) pb.getValue(model);
		assertEquals(2.5f, x, 0);
		
		
	}
}
