//
//  FieldsCheck.swift
//  OHaraEdward_iOS_CrossPlatform
//
//  Created by Scott O'Hara on 4/3/17.
//  Copyright © 2017 Scott O'Hara. All rights reserved.
//

import Foundation
import UIKit

class FieldsCheck {
    
    //MARK: --Text field alert
    //alert func for text fields
    class func textFieldAlert(_ title: String, message: String, presenter: UIViewController){
        //alert controller
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        //alert action
        let alertAction = UIAlertAction(title: "OK", style: .default, handler: nil)
        //add action
        alert.addAction(alertAction)
        //present alert
        presenter.present(alert, animated: true)
    }
    
    //MARK: --Emapty check
    //func check empty text fields
    class func isEmpty(_ textField: UITextField, presenter: UIViewController) -> Bool{
        
        if (textField.text?.isEmpty)! {
            //alert the user
            textFieldAlert("Missing Information", message: "No Empty Fields", presenter: presenter)
            return true
        }
        return false
    }
    
    //MARK: --Valid email
    //func check valid email
    class func validEmail(_ textField: UITextField, presenter: UIViewController) -> Bool{
        
        //nspredicate to check for valid email
        let emailValidation = NSPredicate(format: "SELF MATCHES %@", "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,20}")
        
        //evaluate that email is valid
        if (emailValidation.evaluate(with: textField.text!)) {
            //if email valid proceed
            return true
        }
        
        //alert user email is invalid
        textFieldAlert("Email", message: "Invalid Email", presenter: presenter)
        return false
    }
    
    //MARK: --Valid password
    //func check valid password, firebase password must be six chararcters
    class func validPassword(textField: UITextField, presenter: UIViewController) -> Bool{
        
        //is password less then 6 characters alert user
        if (textField.text!.characters.count < 6) {
            
            //alert user password is invalid
            textFieldAlert("Password", message: "Minimum of 6 password characters" , presenter: presenter)
            return false
        }
        return true
    }
    
}
