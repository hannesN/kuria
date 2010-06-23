package de.topicmapslab.kuria.annotation.data;

import java.util.Date;
import java.util.List;
import java.util.Set;

import de.topicmapslab.kuria.annotation.Text;
import de.topicmapslab.kuria.annotation.table.TableElement;
import de.topicmapslab.kuria.annotation.tree.Children;
import de.topicmapslab.kuria.annotation.tree.TreeNode;
import de.topicmapslab.kuria.annotation.widgets.Check;
import de.topicmapslab.kuria.annotation.widgets.Combo;
import de.topicmapslab.kuria.annotation.widgets.Directory;
import de.topicmapslab.kuria.annotation.widgets.Editable;
import de.topicmapslab.kuria.annotation.widgets.File;
import de.topicmapslab.kuria.annotation.widgets.Group;
import de.topicmapslab.kuria.annotation.widgets.Hidden;
import de.topicmapslab.kuria.annotation.widgets.TextField;

@Editable
@TreeNode
@TableElement
public class WrappedPerson {

	@Hidden
	private Person wrappedPerson = new Person();

	@Text
	public String getName() {
	    return wrappedPerson.getName();
    }
	
	@Group
	public Address getAddress() {
	    return wrappedPerson.getAddress();
    }

	@TextField
	public int getAge() {
	    return wrappedPerson.getAge();
    }
	@Children
	@de.topicmapslab.kuria.annotation.widgets.List
	public List<Person> getChildren() {
	    return wrappedPerson.getChildren();
    }
	@TextField
	public String getLastname() {
	    return wrappedPerson.getLastname();
    }
	@TextField
	public String getFirstname() {
	    return wrappedPerson.getFirstname();
    }

	public void addChild(Person child) {
	    wrappedPerson.addChild(child);
    }

	@Combo
	public Person getWife() {
	    return wrappedPerson.getWife();
    }

	@Check
	public boolean isDumb() {
	    return wrappedPerson.isDumb();
    }

	@de.topicmapslab.kuria.annotation.widgets.List()
	public Set<String> getIdentifiers() {
	    return wrappedPerson.getIdentifiers();
    }

	@de.topicmapslab.kuria.annotation.widgets.Date
	public Date getBirthdate() {
	    return wrappedPerson.getBirthdate();
    }

	@Directory
	public String getHomeDirectory() {
	    return wrappedPerson.getHomeDirectory();
    }

	public void setHomeDirectory(String homeDIrectory) {
	    wrappedPerson.setHomeDirectory(homeDIrectory);
    }

	@File(fileExtensions = { "*.png;*.jpg", "*.png", "*.jpg" }, load = true)
	public String getImage() {
	    return wrappedPerson.getImage();
    }

	@File(fileExtensions = { "*.png;*.jpg", "*.png", "*.jpg" }, load = false)
	public String getIcon() {
	    return wrappedPerson.getIcon();
    }

	public void setAddress(Address address) {
        wrappedPerson.setAddress(address);
    }

	public void setAge(int age) {
        wrappedPerson.setAge(age);
    }

	public void setChildren(List<Person> children) {
        wrappedPerson.setChildren(children);
    }

	public void setLastname(String lastname) {
        wrappedPerson.setLastname(lastname);
    }

	public void setFirstname(String firstname) {
        wrappedPerson.setFirstname(firstname);
    }

	public void setWife(Person wife) {
        wrappedPerson.setWife(wife);
    }

	public void setIdentifiers(Set<String> identifiers) {
        wrappedPerson.setIdentifiers(identifiers);
    }

	public void setBirthdate(Date birthdate) {
        wrappedPerson.setBirthdate(birthdate);
    }

		
}
