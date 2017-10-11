//
//  Credits.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/18/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import SpriteKit

class Credits: SKScene{
    
    //MARK: Stroed Properites
    let mainMenu = MainMenu()
    
    //labels
    let creditsLabel = SKLabelNode(fontNamed: "Noteworthy")
    let movingCreditsLabel = SKLabelNode(fontNamed: "Noteworthy")
    let mainMenuButton = SKLabelNode(fontNamed: "Noteworthy")
    
    //update time of credits added to scene
    var lastCreditAdded: CFTimeInterval = 0.0
    
    //count for credits
    var creditCounter = 0
    
    //Array of info to scoll threw
    let creditsArray = ["Game by Scott O'Hara", "Splash Screen Image by Daniel Borg", "Music & Sound Effects by Natalie Jurosky", "Background Image by Simirk (graphicriver.net)", "Sea Life Images by AhNinniah (graphicriver.net)", "Special Thanks to Joseph Skeckels & Hoyt Dingus..."]
    
    //MARK: didMoveToView
    override func didMove(to view: SKView) {
        
        //background properties
        let background = SKSpriteNode(imageNamed: "BG")
        background.zPosition = -1
        background.alpha = 0.8
        background.size.height = self.frame.size.height
        background.size.width = self.frame.size.width
        background.position = CGPoint(x: self.size.width/2, y: self.size.height/2)
        background.name = "background"
        self.addChild(background)
        
        if UIDevice.current.model == "iPad"{
            //set font size iPad
            creditsLabel.fontSize = 60
            movingCreditsLabel.fontSize = 50
            mainMenuButton.fontSize = 45
            //set position iPad
            creditsLabel.position = CGPoint(x: self.frame.size.width/2, y: self.frame.height/2 + 280)
            mainMenuButton.position = CGPoint(x: self.frame.size.width/2 - 360, y: self.frame.size.height/2 - 350)
        }else{
            //set font iPhone
            creditsLabel.fontSize = 30
            movingCreditsLabel.fontSize = 30
            mainMenuButton.fontSize = 25
            //set position iPhone
            creditsLabel.position = CGPoint(x: self.frame.size.width/2, y: self.frame.height/2 + 140)
            mainMenuButton.position = CGPoint(x: self.frame.size.width/2 - 260, y: self.frame.size.height/2 - 170)
        }
        
        //creditsLabel properties
        creditsLabel.zPosition = 1
        creditsLabel.text = "Credits"
        creditsLabel.fontColor = SKColor.white
        self.addChild(creditsLabel)
        
        //movingCredits properties
        movingCreditsLabel.zPosition = 3
        movingCreditsLabel.position = CGPoint(x: self.frame.size.width/2, y: self.frame.size.height/2)
        movingCreditsLabel.fontColor = SKColor.white
        movingCreditsLabel.text = "Finger Flow Development"
        self.addChild(movingCreditsLabel)
        
        //mainMenuButton properties
        mainMenuButton.zPosition = 1
        mainMenuButton.text = " Main Menu"
        mainMenuButton.fontColor = SKColor.white
        self.addChild(mainMenuButton)
    }//end of didMoveToView
}

//MARK: Touchs Began
extension Credits{
    
    //touchesBegan
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        for touch in touches{
            
            let location = touch.location(in: self)
            
            let nodeSelected = self.atPoint(location)
            
            if nodeSelected.name == mainMenuButton.name{
                
                let showMainMenu = SKTransition.doorsCloseHorizontal(withDuration: 0.5)
                let mainMenu = MainMenu(size: self.view!.bounds.size)
                
                mainMenu.scaleMode = .fill
                
                self.view?.presentScene(mainMenu, transition: showMainMenu)
            }
            
        }//end of touch loop
    }
}

//MARK: Update Time
extension Credits{
    
    override func update(_ currentTime: TimeInterval) {
        
        
        if currentTime - lastCreditAdded > 2{
            self.lastCreditAdded = currentTime + 1
        }
        
        //second update System
        updateTimeSinceLast(currentTime)
        
    }
    
    func updateTimeSinceLast(_ timeSinceLast: CFTimeInterval){
        
        if timeSinceLast - lastCreditAdded > 1.5{
            
            lastCreditAdded = timeSinceLast + 1
            
            //set credits label to creditaArray passing the count to increment
            movingCreditsLabel.text = creditsArray[creditCounter]
            
            //reset time
            self.lastCreditAdded = 0
            
            //add one to creditCounter
            creditCounter += 1
        }
        
        //check if array hits length
        if creditCounter == 6{
            //reset creditCount to zero so credits replay
            creditCounter = 0
        }
        
    }
    
    
}
