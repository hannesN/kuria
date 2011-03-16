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
package kuria.test.model;

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
import de.topicmapslab.kuria.annotation.widgets.Directory;
import de.topicmapslab.kuria.annotation.widgets.Editable;
import de.topicmapslab.kuria.annotation.widgets.File;
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
@TreeNode(imageMethod="getImageForPet")
public class Person {

	@de.topicmapslab.kuria.annotation.widgets.List(style=ListStyle.TABLE, createNew=true)
	private List<String> identifier;
	
	@TextField
	private String lastname;
	
	@TextField
	@Column(imageMethod="getImageForPet")
	private String firstname;

	
	@Column
	@TextField(optional=true)
	private String nickname;
	
	@Column(textMethod="getAgeText")
	private int age;

	@Group
	@Hidden
	@Column
	private Address address;
	
	@Date(optional=true, showTime=true)
	private java.util.Date appointment;
	
	
	@Children(title="children")
	@de.topicmapslab.kuria.annotation.widgets.List(style=ListStyle.COMPACT, createNew=true, optional=true)
	private List<Person> children;
	
	@Children
	@Column
	@Combo(createNew=false, optional=true)
	private Pet favPet;
	
	@Combo(createNew=true, optional=true, readOnly=true)
    private Person wife;

	@Date(optional=true, readOnly=true)
	private java.util.Date birthdate; 

	@Hidden
	private boolean dumb;

	@de.topicmapslab.kuria.annotation.widgets.List(style=ListStyle.TABLE, createNew=true, optional=true)
	@Hidden
	private Set<Pet> pets;
	
	@TextField(rows=10)
	@Hidden
	private String description;
	
	@TextField(label="Hourly Wage")
	@Hidden
	private double hourlyWage;

	
	@Directory(optional=true, readOnly=true)
	private String homeDirectory;
	
	@File(fileExtensions="*.jpg;*.png", load=true, readOnly=true)
	private String imagePath;
	
	/**
     * @param homeDirectory the homeDirectory to set
     */
    public void setHomeDirectory(String homeDirectory) {
	    this.homeDirectory = homeDirectory;
    }
    
    /**
     * @return the imagePath
     */
    public String getImagePath() {
	    return imagePath;
    }
    
    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
	    this.imagePath = imagePath;
    }
    
    /**
     * @return the homeDirectory
     */
    public String getHomeDirectory() {
	    return homeDirectory;
    }
	
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
	
	@Children(title="other", image="nothere.gif")
	public Set<Address> getOtherAddresses() {
		return Collections.emptySet();
	}
	
	public String getAgeText() {
		return Integer.toString(getAge()) + " Jahre";
	}
	
	public String getImageForPet() {
		return "tiger.png";
	}
	
	/**
     * @return the appointment
     */
    public java.util.Date getAppointment() {
	    return appointment;
    }
    
    /**
     * @param appointment the appointment to set
     */
    public void setAppointment(java.util.Date appointment) {
	    this.appointment = appointment;
    }
}
