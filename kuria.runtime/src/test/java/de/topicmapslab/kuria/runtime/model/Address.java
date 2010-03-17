package de.topicmapslab.kuria.runtime.model;

/**
 * @author Hannes Niederhausen
 */
public class Address {

	private String street;

	private String number;

	private String zipcode;

	private String city;

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

	public String getZipcode() {
	    return zipcode;
    }
	
	public void setZipcode(String zipcode) {
	    this.zipcode = zipcode;
    }

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

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
