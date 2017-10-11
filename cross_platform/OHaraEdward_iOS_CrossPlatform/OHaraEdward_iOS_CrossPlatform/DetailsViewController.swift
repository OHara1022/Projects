//
//  DetailsViewController.swift
//  OHaraEdward_iOS_CrossPlatform
//
//  Created by Scott O'Hara on 4/4/17.
//  Copyright © 2017 Scott O'Hara. All rights reserved.
//

import UIKit

class DetailsViewController: UIViewController {

    //MARK: --Outlets
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var phoneNumberLabel: UILabel!
    
    //MARK: --stored properties
    var firstNameHolder: String?
    var lastNameHolder: String?
    var phoneNumberHolder: Int?

    //MARK: --viewDidLoad
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //populate details screen w/ passed data retrieved from firebase db
        nameLabel.text = firstNameHolder! + " " + lastNameHolder!
        phoneNumberLabel.text = "\(phoneNumberHolder!)"
    }
}
