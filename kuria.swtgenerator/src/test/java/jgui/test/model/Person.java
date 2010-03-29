/**
 * 
 */
package jgui.test.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import de.topicmapslab.kuria.annotation.Text;
import de.topicmapslab.kuria.annotation.table.Column;
import de.topicmapslab.kuria.annotation.table.TableElement;
import de.topicmapslab.kuria.annotation.tree.Children;
import de.topicmapslab.kuria.annotation.tree.TreeNode;
import de.topicmapslab.kuria.annotation.widgets.Combo;
import de.topicmapslab.kuria.annotation.widgets.Date;
import de.topicmapslab.kuria.annotation.widgets.Editable;
import de.topicmapslab.kuria.annotation.widgets.Group;
import de.topicmapslab.kuria.annotation.widgets.Hidden;
import de.topicmapslab.kuria.annotation.widgets.TextField;
import de.topicmapslab.kuria.runtime.widget.ListStyle;

/**
 * @author niederhausen
 *
 */
@Editable
@TableElement
@TreeNode
public class Person {

	@de.topicmapslab.kuria.annotation.widgets.List(style=ListStyle.TABLE, createNew=true)
	private List<String> identifier;
	
	@TextField
	private String lastname;
	
	@TextField
	@Column(image="tiger.png")
	private String firstname;
	
	@Column
	@TextField(optional=true)
	private String nickname;
	
	@Column
	private int age;

	@Group
	@Hidden
	private Address address;
	
	@Children
	@de.topicmapslab.kuria.annotation.widgets.List(style=ListStyle.COMPACT, createNew=true)
	private List<Person> children;
	
	@Children
	@Column
	@Combo(createNew=false, optional=true)
	private Pet favPet;
	
	@Combo(createNew=true, optional=true)
    private Person wife;

	@Date
	@Hidden
	private java.util.Date birthdate; 

	@Hidden
	private boolean dumb;

	@de.topicmapslab.kuria.annotation.widgets.List(style=ListStyle.TABLE, createNew=true)
	@Hidden
	private Set<Pet> pets;
	
	@TextField(rows=10, grabVerticalSpace=true)
	@Hidden
	private String description;
	
	@TextField(label="Hourly Wage")
	@Hidden
	private double hourlyWage;

	@Text
	public String getName() {
    	return lastname+", "+firstname;
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
	
	public Pet getFavPet() {
	    return favPet;
    }
	
	public void setFavPet(Pet pet) {
	    this.favPet = pet;
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
	
	public void setNickname(String nickname) {
	    this.nickname = nickname;
    }
	
	public String getNickname() {
	    return nickname;
    }
	
	public double getHourlyWage() {
	    return hourlyWage;
    }
	
	public void setHourlyWage(double hourlyWage) {
	    this.hourlyWage = hourlyWage;
    }
	
	public void setDescription(String description) {
	    this.description = description;
    }
	
	public String getDescription() {
	    return description;
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
	
	public Set<Pet> getPets() {
	    return pets;
    }
	
	public void setPets(Set<Pet> pets) {
	    this.pets = pets;
    }

	@Override
    public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Person [lastname=").append(lastname).append(", firstname=").append(firstname).append(
	            ", nickname=").append(nickname).append(", age=").append(age).append("]");
	    return builder.toString();
    }
	
	public List<String> getIdentifier() {
	    return identifier;
    }
	
	public void setIdentifier(List<String> identifier) {
	    this.identifier = identifier;
    }
	
	public java.util.Date getBirthdate() {
	    return birthdate;
    }
	
	public void setBirthdate(java.util.Date birthdate) {
	    this.birthdate = birthdate;
    }
	
	@Children(title="other")
	public Set<Address> getOtherAddresses() {
		return Collections.emptySet();
	}
}
