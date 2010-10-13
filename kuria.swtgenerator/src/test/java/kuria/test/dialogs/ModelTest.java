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
package kuria.test.dialogs;

import kuria.test.model.Pet;
import kuria.test.model.Person;

import org.eclipse.jface.dialogs.Dialog;
import org.junit.Ignore;

import de.topicmapslab.kuria.annotation.AnnotationBindingFactory;
import de.topicmapslab.kuria.swtgenerator.dialogs.ModelDialog;

/**
 * @author Hannes Niederhausen
 *
 */
@Ignore
public class ModelTest {

	public static void main(String[] args) {
		
		AnnotationBindingFactory fac = new AnnotationBindingFactory();
		fac.addClass(Pet.class);
		fac.addClass(Person.class);
		
		
	    ModelDialog dlg = new ModelDialog(null, fac.getBindingContainer(), Pet.class, 400);
	    
	    Pet p1 = new Pet();
	    p1.setName("Lukas");

	    dlg.setModel(p1);
	    if (dlg.open()==Dialog.OK) {
	    	
	    } else {
	    	System.out.println(((Pet) dlg.getModel()).getName().equals("Lukas"));
	    }
	    
	    dlg = new ModelDialog(null, fac.getBindingContainer(), Person.class, 500);
	    dlg.setModel(new Person());
	    if (dlg.open()==Dialog.OK) {
	    	
	    } 
	    
	    try {
	    	dlg.setModel(p1);
	    } catch (IllegalArgumentException e) {
	    	System.out.println("Exception thrown: good");
	    	return;
	    }
	    System.out.println("No Exception thrown: bad");
    }
}
