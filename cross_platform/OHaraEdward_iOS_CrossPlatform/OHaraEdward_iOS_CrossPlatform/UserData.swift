//
//  UserData.swift
//  OHaraEdward_iOS_CrossPlatform
//
//  Created by Scott O'Hara on 4/4/17.
//  Copyright © 2017 Scott O'Hara. All rights reserved.
//

import Foundation

class UserData{
    
    //stored properties
    var firstName: String
    var lastName: String
    var email: String
    var password: String?//never store password in firebase
    var uid: String
    
    //init user properties
    init(firstName: String, lastName: String, email: String, uid: String) {
        self.firstName = firstName
        self.lastName = lastName
        self.email = email
        self.uid = uid
    }
}
