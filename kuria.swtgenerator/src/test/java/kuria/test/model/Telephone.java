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
package kuria.test.model;

import de.topicmapslab.kuria.annotation.widgets.Check;
import de.topicmapslab.kuria.annotation.widgets.Editable;
import de.topicmapslab.kuria.annotation.widgets.TextField;

/**
 * @author niederhausen
 *
 */
@Editable
public class Telephone extends NamedObject {

	@TextField
	private String serialNumber;
	
	@Check(weight=0)
	private boolean works;
	
	@TextField
	private String vendor;
	
	@TextField
	private int age;

	/**
     * @return the serialNumber
     */
    public String getSerialNumber() {
    	return serialNumber;
    }

	/**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
    	this.serialNumber = serialNumber;
    }

	/**
     * @return the works
     */
    public boolean isWorks() {
    	return works;
    }

	/**
     * @param works the works to set
     */
    public void setWorks(boolean works) {
    	this.works = works;
    }

	/**
     * @return the vendor
     */
    public String getVendor() {
    	return vendor;
    }

	/**
     * @param vendor the vendor to set
     */
    public void setVendor(String vendor) {
    	this.vendor = vendor;
    }

	/**
     * @return the age
     */
    public int getAge() {
    	return age;
    }

	/**
     * @param age the age to set
     */
    public void setAge(int age) {
    	this.age = age;
    }
	
	
}
