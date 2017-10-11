//
//  LoginViewController.swift
//  OHaraEdward_iOS_CrossPlatform
//
//  Created by Scott O'Hara on 4/3/17.
//  Copyright © 2017 Scott O'Hara. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth

class LoginViewController: UIViewController {
    
    //MARK: --Outlets
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    
    //MARK: --Stored properties
    //firebase db ref
    var ref: FIRDatabaseReference!
    
    //MARK: --viewDidLoad
    override func viewDidLoad() {
        super.viewDidLoad()
        //set firebase to be persistenced when no network is available
        FIRDatabase.database().persistenceEnabled = true

        //init database reference
        ref = FIRDatabase.database().reference()
        
        //listener if user is signer in, perform segue
        FIRAuth.auth()?.addStateDidChangeListener{ auth, user in
            
            //check if user is signed in
            if let user = user {
                //dev
                print("ALREADY LOGGED IN " + " " + user.email!)
                
                //user signed in, perform segue
                self.performSegue(withIdentifier: "loginSegue", sender: nil)
            }
        }//end of listener
    }
    
    //MARK: --viewWillAppear
    override func viewWillAppear(_ animated: Bool) {
        //clear textFields
        self.emailTextField.text = ""
        self.passwordTextField.text = ""
    }
    
    //MARK: --Segue
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        
        //email/password textFields text for firebase validation
        let email = emailTextField.text
        let password = passwordTextField.text
        
        //authFlag
        var firebaseAuthFlag: Bool = false
        
        //check identifier
        if (identifier == "loginSegue"){
            
            if Reachability.isConnectedToNetwork() == true {
                //dev
                print("Internet Connected")
               
            } else {
                //dev
                print("Internet Disconnected")
                FieldsCheck.textFieldAlert("No Network", message: "Please connect to network", presenter: self)
                return false
            }
            
            //check textFields are not empty
            if (!FieldsCheck.isEmpty(emailTextField, presenter: self) && !FieldsCheck.isEmpty(passwordTextField, presenter: self)){
                
                //check email and password are valid
                if (FieldsCheck.validEmail(emailTextField, presenter: self) && FieldsCheck.validPassword(textField: passwordTextField, presenter: self)){
                    
                    //auth user w/ firebase & segue to contact list VC
                    FIRAuth.auth()?.signIn(withEmail: email!, password: password!, completion: { (user, error) in
                        
                        //optional bind and print if desctiption if error occurs
                        if let error = error{
                            //dev
                            print(error.localizedDescription)
                            
                            //display alert if user login fails
                            FieldsCheck.textFieldAlert("Invalid Information", message: error.localizedDescription, presenter: self)
                            return
                        }//end of error check
                        
                        //signed into firebase
                        firebaseAuthFlag = true
                    })
                
                    //if firebase authFlag is not true
                    if firebaseAuthFlag != true {
                        //do not perform segue
                        return false
                    }
                    
                    //clear fields
                    self.emailTextField.text = ""
                    self.passwordTextField.text = ""
                    
                    //perform segue
                    return true
                    
                }//end of email/password check
            }//end of empty check
        }//end of identifier check
        
        //check identifier
        if (identifier == "signUpSegue") {
            //clear fields
            self.emailTextField.text = ""
            self.passwordTextField.text = ""
        
            if Reachability.isConnectedToNetwork() == true {
                //dev
                print("Internet Connected")
                
            } else {
                //dev
                print("Internet Disconnected")
                FieldsCheck.textFieldAlert("No Network", message: "Please connect to network", presenter: self)
                return false
            }
            
            //return true, transition to sign up
            return true
        }
        
        //do not perform segue
        return false
    }//end of should perform
}
