//
//  SignUpViewController.swift
//  OHaraEdward_iOS_CrossPlatform
//
//  Created by Scott O'Hara on 4/3/17.
//  Copyright © 2017 Scott O'Hara. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth
import FirebaseDatabase

class SignUpViewController: UIViewController {
    
    //MARK: --Outlets
    @IBOutlet weak var firstNameTextField: UITextField!
    @IBOutlet weak var lastNameTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    
    //MARK: --Stored properties
    //firebase database ref
    var ref: FIRDatabaseReference!
    
    //MARKK: --viewDidLoad
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //create reference to database
        ref = FIRDatabase.database().reference()
    }

    //MARK: -- Actions
    //cancel sign up
    @IBAction func cancelButton(_ sender: Any) {
        //dev
        print("CANCEL BUTTON")
        
        //return to login VC
        dismiss(animated: true, completion: nil)
    }

    //sign up new user
    @IBAction func signUpButton(_ sender: Any) {
        //dev
        print("SIGN UP BUTTON")
        
        //email/password textFields text for firebase validation
        let email = emailTextField.text
        let password = passwordTextField.text
        
        //check text fields are not empty
        if (!FieldsCheck.isEmpty(firstNameTextField, presenter: self) && !FieldsCheck.isEmpty(lastNameTextField, presenter: self) &&
            !FieldsCheck.isEmpty(emailTextField, presenter: self) && !FieldsCheck.isEmpty(passwordTextField, presenter: self)){
            //dev
            print("NOT EMPTY")
            
            //check email and password are valid
            if (FieldsCheck.validEmail(emailTextField, presenter: self) && FieldsCheck.validPassword(textField: passwordTextField, presenter: self)){
                //dev
                print("VALID")
                
                //create new firebase user
                FIRAuth.auth()?.createUser(withEmail: email!, password: password!, completion: { (user, error) in
                    
                    //optional bind and print if desctiption if error occurs
                    if let error = error {
                        //alert the user of firebase error if create user fails
                        FieldsCheck.textFieldAlert("Invalid Information", message: error.localizedDescription, presenter: self)
                        //dev
                        print(error.localizedDescription)
                        //return if error during creating user
                        return
                    }//end of error check
                    
                    //textFields text to popualte object 
                    let firstName = self.firstNameTextField.text!
                    let lastName = self.lastNameTextField.text!
                    let email = self.emailTextField.text!
                    
                    //populate user object
                    let newUser = UserData(firstName: firstName, lastName: lastName, email: email, uid: user!.uid)
                    
                    //push created user to firebase
                    self.ref.child("users").child(newUser.uid).setValue(["firstName": newUser.firstName, "lastName": newUser.lastName, "email": newUser.email, "uid":
                        newUser.uid])
                })
                //dismiss VC
                self.dismiss(animated: true, completion: nil)
            }//end email & password check
        }//end of empty check
    }//end of action signUp
}
