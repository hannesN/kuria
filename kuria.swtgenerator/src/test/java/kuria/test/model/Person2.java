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

import de.topicmapslab.kuria.annotation.widgets.Combo;
import de.topicmapslab.kuria.annotation.widgets.Editable;
import de.topicmapslab.kuria.annotation.widgets.Group;
import de.topicmapslab.kuria.annotation.widgets.TextField;

/**
 * @author niederhausen
 *
 */
@Editable
public class Person2 {

	private String name;
	
	@Group
	private Address address;
	
	/**
     * @param name the name to set
     */
    public void setName(String name) {
	    this.name = name;
    }
    
    /**
     * @return the name
     */
    public String getName() {
	    return name;
    }
    
    /**
     * @return the address
     */
    public Address getAddress() {
	    return address;
    }
    
    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
	    this.address = address;
    }
	
	
    @Editable
    public static class Address {
    	@TextField
    	String street;
    	
    	@Combo
    	String number;
    	
    	/**
         * @param number the number to set
         */
        public void setNumber(String number) {
	        this.number = number;
        }
        
        /**
         * @param street the street to set
         */
        public void setStreet(String street) {
	        this.street = street;
        }
        
        /**
         * @return the number
         */
        public String getNumber() {
	        return number;
        }
        
        /**
         * @return the street
         */
        public String getStreet() {
	        return street;
        }
    }
}
