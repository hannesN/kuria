package de.topicmapslab.kuria.annotation.data;

import de.topicmapslab.kuria.annotation.Text;
import de.topicmapslab.kuria.annotation.table.Column;
import de.topicmapslab.kuria.annotation.table.TableElement;
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

	public String getPlz() {
		return zipcode;
	}

	public void setPlz(String plz) {
		this.zipcode = plz;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
