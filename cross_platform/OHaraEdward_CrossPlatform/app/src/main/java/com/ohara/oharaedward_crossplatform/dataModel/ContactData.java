package com.ohara.oharaedward_crossplatform.dataModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


//Edward O'Hara
//ContactData
public class ContactData implements Serializable {

    //stored properties
    public String firstName;
    public String lastName;
    public Long phoneNumber;

    //firebase default constructor
    public ContactData(){

    }

    //constructor
    public ContactData(String firstName, String lastName, Long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    //firebase object mapping
    public Map<String, Object> toMap(){

        //get new hashmap
        HashMap<String, Object> result = new HashMap<>();
        //result to add on change
        result.put("firstName", this.firstName);
        result.put("lastName", this.lastName);
        result.put("phoneNumber", this.phoneNumber);
        //return result
        return result;
    }


    //toString method to display name in list
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
