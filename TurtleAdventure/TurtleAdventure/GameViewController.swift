//
//  GameViewController.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/5/16.
//  Copyright (c) 2016 Scott O'Hara. All rights reserved.
//

import SpriteKit
import UIKit

class GameViewController: UIViewController {
    
    //set the screen sizes. 
    let height = UIScreen.main.bounds
    .height
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let scene = MainMenu(fileNamed:"GameScene") {
            // Configure the view.
            let skView = self.view as! SKView
            
            skView.showsFPS = true
            skView.showsNodeCount = true
            skView.showsPhysics = false
            
            /* Sprite Kit applies additional optimizations to improve rendering performance */
            skView.ignoresSiblingOrder = true
            
            /* Set the scale mode to scale to fit the window */
            scene.scaleMode = .fill
            
            //set size of scene to bounds of screen
            scene.size =  self.view.bounds.size
            
            skView.presentScene(scene)
        }
    }
    
    override var shouldAutorotate : Bool {
        return true
    }
    
    override var supportedInterfaceOrientations : UIInterfaceOrientationMask {
        if UIDevice.current.userInterfaceIdiom == .phone {
            return .allButUpsideDown
        } else {
            return .all
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Release any cached data, images, etc that aren't in use.
    }
    
    override var prefersStatusBarHidden : Bool {
        return true
    }
}
