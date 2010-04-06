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
package de.topicmapslab.kuria.runtime.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.topicmapslab.kuria.runtime.model.Address;

/**
 * @author niederhausen
 * 
 */
public class Person {
	private Set<String> identifiers;
	
	private String lastname;

	private String firstname;

	private int age;

	private Address address;

	private List<Person> children;

	private Person wife;

	private boolean dumb;
	
	public String getName() {
		return lastname + ", " + firstname;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public List<Person> getChildren() {
		return children;
	}

	public void setChildren(List<Person> children) {
		this.children = children;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void addChild(Person child) {
		if (children == null) {
			children = new ArrayList<Person>();
		}
		children.add(child);
	}

	public Person getWife() {
		return wife;
	}

	public void setWife(Person wife) {
		this.wife = wife;
	}

	public boolean isDumb() {
		return dumb;
	}

	public void setDumb(boolean dumb) {
		this.dumb = dumb;
	}
	
	public void setIdentifiers(Set<String> identifiers) {
	    this.identifiers = identifiers;
    }
	
	public Set<String> getIdentifiers() {
	    return identifiers;
    }
}
