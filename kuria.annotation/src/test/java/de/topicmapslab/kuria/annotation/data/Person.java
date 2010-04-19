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
package de.topicmapslab.kuria.annotation.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import de.topicmapslab.kuria.annotation.Text;
import de.topicmapslab.kuria.annotation.table.TableElement;
import de.topicmapslab.kuria.annotation.tree.Children;
import de.topicmapslab.kuria.annotation.tree.TreeNode;
import de.topicmapslab.kuria.annotation.widgets.Directory;
import de.topicmapslab.kuria.annotation.widgets.Editable;
import de.topicmapslab.kuria.annotation.widgets.File;
import de.topicmapslab.kuria.annotation.widgets.Group;
import de.topicmapslab.kuria.annotation.widgets.TextField;

/**
 * @author niederhausen
 * 
 */
@Editable
@TreeNode
@TableElement
public class Person {
	private Set<String> identifiers;

	@TextField
	private String lastname;

	@TextField
	private String firstname;

	private int age;

	@Group
	private Address address;

	@Children
	private List<Person> children;

	private Person wife;

	private Date birthdate;

	private boolean dumb;

	@Directory
	private String homeDirectory;

	@File(fileExtensions = { "*.png;*.jpg", "*.png", "*.jpg" }, load = true)
	private String image;

	@File(fileExtensions = { "*.png;*.jpg", "*.png", "*.jpg" }, load = false)
	private String icon;

	@Text
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

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	@Children
	public Set<Address> otherAddresses() {
		return Collections.emptySet();
	}

	/**
	 * @return the homeDIrectory
	 */
	public String getHomeDirectory() {
		return homeDirectory;
	}

	/**
	 * @param homeDirectory
	 *            the homeDIrectory to set
	 */
	public void setHomeDirectory(String homeDIrectory) {
		this.homeDirectory = homeDIrectory;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
