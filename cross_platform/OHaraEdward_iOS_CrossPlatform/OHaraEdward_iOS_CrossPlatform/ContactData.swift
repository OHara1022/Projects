//
//  ContactData.swift
//  OHaraEdward_iOS_CrossPlatform
//
//  Created by Scott O'Hara on 4/4/17.
//  Copyright © 2017 Scott O'Hara. All rights reserved.
//

import Foundation

class ContactData{
    
    //stored properties
    var firstName: String
    var lastName: String
    var phoneNumber: Int
    var contactKey: String?
    
    //init contact properties
    init(firstName: String, lastName: String, phoneNumber: Int) {
        self.firstName = firstName
        self.lastName = lastName
        self.phoneNumber = phoneNumber
    }
}
