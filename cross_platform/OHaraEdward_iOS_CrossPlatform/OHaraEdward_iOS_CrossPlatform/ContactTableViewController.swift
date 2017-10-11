//
//  ContactTableViewController.swift
//  OHaraEdward_iOS_CrossPlatform
//
//  Created by Scott O'Hara on 4/4/17.
//  Copyright © 2017 Scott O'Hara. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth
import FirebaseDatabase

class ContactTableViewController: UITableViewController {
    
    //MARK: --Stored Properties
    var ref: FIRDatabaseReference!
    var tableItems: NSDictionary!
    let userID = FIRAuth.auth()?.currentUser?.uid//get current user userID
    
    //MARK: --viewDidLoad
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //assign delegate and datasoure to self
        tableView.delegate = self
        tableView.dataSource = self
        
        //init dictionary
        tableItems = NSDictionary()
        
        //set ref of database to schedules
        ref = FIRDatabase.database().reference().child("contacts").child(userID!)
        
        //keep contact sync when offline
        ref.keepSynced(true)
        
        //check network status
        if Reachability.isConnectedToNetwork() == true {
            //dev
            print("Internet Connected")
            
        } else {
            //dev
            print("Internet Disconnected")
            //alert user there is no network connection
            FieldsCheck.textFieldAlert("No Network", message: "Please check network connection", presenter: self)
        }
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        
        //add observer to listen for changes in contacts
        ref.observe(FIRDataEventType.value, with: { (snapshot) in
            
            //check if tableView has values
            if (snapshot.hasChildren() ){
                
                //get values
                self.tableItems = snapshot.value as! NSDictionary
            }
            
            //reload tableView
            self.tableView.reloadData()
        })
        
    }
    
    
    //MARK: -- Actions
    @IBAction func signOutBTN(_ sender: UIBarButtonItem) {
        //dev
        print("SIGN OUT")
        
        //sign user out w/ firebase auth
        try! FIRAuth.auth()!.signOut()
        
        //dismiss VC
        dismiss(animated: true, completion: nil)
    }
    
    // MARK: --Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        //return count of pulled data
        return tableItems.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        //configure the cell
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        
        //get values of schedule from firebase
        let obj = self.tableItems.allValues[(indexPath as NSIndexPath).row] as! NSDictionary
        
        //get object value as string
        let firstName =  obj.value(forKey: "firstName") as? String
        let lastName =  obj.value(forKey: "lastName") as? String
        
        //add info to tableView
        cell.textLabel?.text = firstName! + " " + lastName!
        //return cell
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        //if tableView has a value
        if tableItems.count != 0 {
            //perform segue
            performSegue(withIdentifier: "detailSegue", sender: indexPath)
        }
    }
    
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        //return true to allow delete
        return true
    }
    
    //MARK: TODO--delete from firebase
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            
            //get all items in dictionary
            let rowIndex = self.tableItems.allValues[indexPath.row] as! NSDictionary
            
            //get contact key
            let contactKey = rowIndex.value(forKey: "contactKey") as? String
            
            //delete selected contact from firebase
            ref.child(contactKey!).removeValue()
            
            //get al items in table view
            var tableValues = self.tableItems.allValues
            
            //dev
            print(tableValues)
            
            //remove selected row
            tableValues.remove(at: indexPath.row)
            
            //dev
            print(tableValues)
            
            //reload tableView
            tableView.reloadData()
            
            //delete rows at indexPath
            tableView.deleteRows(at: [indexPath], with: .fade)
            
        }
        //reload tableView
        tableView.reloadData()
    }
    
    //MARK: --Navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if let index = sender as? IndexPath{
            
            //get destination VC
            let details = segue.destination as! DetailsViewController
            
            //get all values from dictionary
            let obj = self.tableItems.allValues[(index as NSIndexPath).row] as! NSDictionary
            
            //dev
            print(obj.value(forKey: "firstName") as! String)
            print(obj.value(forKey: "phoneNumber") as! Int)
            
            //pass values to details
            details.firstNameHolder = obj.value(forKey: "firstName") as? String
            details.lastNameHolder = obj.value(forKey: "lastName") as? String
            details.phoneNumberHolder = obj.value(forKey: "phoneNumber") as? Int
        }
    }//end of prepare
}
