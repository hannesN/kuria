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
