//
//  EditViewController.swift
//  OHaraEdward_iOS_CrossPlatform
//
//  Created by Scott O'Hara on 4/27/17.
//  Copyright © 2017 Scott O'Hara. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth
import FirebaseDatabase


class EditViewController: UIViewController {
    
    //stored properties
    var ref: FIRDatabaseReference!
    let userID = FIRAuth.auth()?.currentUser?.uid//get current user userID
    
    //MARK --Outlets
    @IBOutlet weak var firstNameTF: UITextField!
    @IBOutlet weak var lastNameTF: UITextField!
    @IBOutlet weak var phoneTF: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        //get db reference
        ref = FIRDatabase.database().reference().child("contacts").child(userID!)
    }
    
    //MARK --Actions
    @IBAction func saveButton(_ sender: Any) {
        
        //retrieve text field text
        let firstName = firstNameTF.text
        let lastName = lastNameTF.text
        let phoneNumber: Int? = Int(self.phoneTF.text!)
        
        //check text fields are not empty
        if (!FieldsCheck.isEmpty(firstNameTF, presenter: self) && !FieldsCheck.isEmpty(lastNameTF, presenter: self) && !FieldsCheck.isEmpty(phoneTF, presenter: self)){
            
            //get key
            let keyRef = ref.childByAutoId().key
            
            //dev
            print(keyRef)
            
            //populate object
            let newContact = ContactData(firstName: firstName!, lastName: lastName!, phoneNumber: phoneNumber!)
            
            //update value of selected contact
            ref.child(keyRef).updateChildValues(["firstName": newContact.firstName, "lastName": newContact.lastName, "phoneNumber":
                newContact.phoneNumber])
            
            //return to details vc
            _ = navigationController?.popViewController(animated: true)
        }
    }
}

//refernced from:
/*http://stackoverflow.com/questions/28760541/programmatically-go-back-to-previous-viewcontroller-in-swift */
