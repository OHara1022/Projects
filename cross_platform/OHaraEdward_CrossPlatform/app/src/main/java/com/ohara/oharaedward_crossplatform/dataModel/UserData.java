package com.ohara.oharaedward_crossplatform.dataModel;

import java.io.Serializable;


//Edward O'Hara
//UserData
public class UserData implements Serializable{

    //stored properties
    public String firstName;
    public String lastName;
    public String email;
    public String password;//never store password in firebase, used for login w/ firebase
    public String uid;

    //firebase default constructor
    public UserData(){
    }

    //constructor
    public UserData(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
