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
package de.topicmapslab.kuria.runtime.model;

import java.util.List;
import java.util.Set;

public class File {

	Set<Person> persons;
	
	Address[] addresses;
	
	List<Person> parents;

	public Set<Person> getPersons() {
    	return persons;
    }

	public void setPersons(Set<Person> persons) {
    	this.persons = persons;
    }

	public Address[] getAddresses() {
    	return addresses;
    }

	public void setAddresses(Address[] addresses) {
    	this.addresses = addresses;
    }

	public List<Person> getParents() {
    	return parents;
    }

	public void setParents(List<Person> parents) {
    	this.parents = parents;
    }
	
	
	
}
