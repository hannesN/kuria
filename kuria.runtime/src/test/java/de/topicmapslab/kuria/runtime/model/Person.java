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
