//
//  AddContactViewController.swift
//  OHaraEdward_iOS_CrossPlatform
//
//  Created by Scott O'Hara on 4/4/17.
//  Copyright © 2017 Scott O'Hara. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth
import FirebaseDatabase

class AddContactViewController: UIViewController {
    
    //MARK: --Outlets
    @IBOutlet weak var firstNameTextField: UITextField!
    @IBOutlet weak var lastNameTextField: UITextField!
    @IBOutlet weak var phoneNumberTextField: UITextField!
    
    //MARK: --Stored properties
    //firebase database ref
    var ref: FIRDatabaseReference!
    
    //MARK: --viewDidLoad
    override func viewDidLoad() {
        super.viewDidLoad()
        //create reference to database
        ref = FIRDatabase.database().reference()
    }
    
    //MARK: --Actions
    @IBAction func cancelAddContactBtn(_ sender: Any) {
        //dismiss VC
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func saveContact(_ sender: UIBarButtonItem) {
        //dev
        print("SAVE CONTACT BTN")
        
        //retrieve text field text
        let firstName = firstNameTextField.text
        let lastName = lastNameTextField.text
        let phoneNumber: Int? = Int(self.phoneNumberTextField.text!)
        
        //check text fields are not empty
        if (!FieldsCheck.isEmpty(firstNameTextField, presenter: self) && !FieldsCheck.isEmpty(lastNameTextField, presenter: self) && !FieldsCheck.isEmpty(phoneNumberTextField, presenter: self)){
            
            //get current user userID
            let userID = FIRAuth.auth()?.currentUser?.uid
            
            //generate key for each contact created for a single user
            let keyRef = ref.childByAutoId().key
            
            //populate object
            let newContact = ContactData(firstName: firstName!, lastName: lastName!, phoneNumber: phoneNumber!)
            
            //push created contact to firebase
            self.ref.child("contacts").child(userID!).child(keyRef).setValue(["firstName": newContact.firstName, "lastName": newContact.lastName, "phoneNumber": newContact.phoneNumber, "contactKey": keyRef])
            
            //dismiss vc 
            self.dismiss(animated: true, completion: nil)
            
        }//end empty check
    }
}
