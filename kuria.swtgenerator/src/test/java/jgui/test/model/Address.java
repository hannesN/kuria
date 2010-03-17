package jgui.test.model;

import de.topicmapslab.kuria.annotation.Text;
import de.topicmapslab.kuria.annotation.table.Column;
import de.topicmapslab.kuria.annotation.table.TableElement;
import de.topicmapslab.kuria.annotation.widgets.Check;
import de.topicmapslab.kuria.annotation.widgets.Editable;

/**
 * @author Hannes Niederhausen
 */
@Editable
@TableElement
public class Address {

	@Column
	private String street;

	@Column
	private String number;

	@Column
	private String zipcode;

	@Column
	private String city;

	@Check
	private boolean headQuarter;
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public void setZipcode(String zipcode) {
	    this.zipcode = zipcode;
    }
	
	public String getZipcode() {
	    return zipcode;
    }

	public boolean isHeadQuarter() {
	    return headQuarter;
    }
	
	public void setHeadQuarter(boolean headQuarter) {
	    this.headQuarter = headQuarter;
    }
	
	@Text
	public String getText() {
		StringBuilder builder = new StringBuilder();
		builder.append(street);
		builder.append(" ");
		builder.append(number);
		builder.append(", ");
		builder.append(zipcode);
		builder.append(" ");
		builder.append(city);
		
		return builder.toString();
	}
}
