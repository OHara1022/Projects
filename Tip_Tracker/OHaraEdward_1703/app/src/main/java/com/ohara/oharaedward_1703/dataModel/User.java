package com.ohara.oharaedward_1703.dataModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//Edward O'Hara
//MDV469 - 1703
//User
public class User implements Serializable {

    //stored properties
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String uid;

    //firebase default constructor
    public User(){
    }

    //constructor
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    //firebase user mapping
    public Map<String, Object> toMap(){

        HashMap<String, Object> result = new HashMap<>();

        result.put("firstName", this.firstName);
        result.put("lastName", this.lastName);
        result.put("emailAddress", this.email);
        //***important never store password into firebase database

        //return result
        return result;
    }
}
