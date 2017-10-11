//
//  MainMenuAssetProperties.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/25/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import Foundation
import SpriteKit
import GameKit

//Properties for all assets are set to account for both iPhone and iPad, position and sizing set to accommodate which device the game is on
extension MainMenu{
    
    //MARK: BG Properties
    func backgroundProperties(){
        background.zPosition = -1
        background.alpha = 0.8
        background.size.height = self.frame.size.height
        background.size.width = self.frame.size.width
        background.position = CGPoint(x: self.size.width/2, y: self.size.height/2)
        background.name = "background"
        self.addChild(background)
    }
    
    //MARK: GameLabelProperties
    func gameNameLabelProperties(){
        //gameNameLabel properties
        gameNameLabel.zPosition = 3
        gameNameLabel.text = "Turtle Adventure"
        gameNameLabel.fontColor = UIColor.white
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            gameNameLabel.fontSize = 70
            gameNameLabel.position = CGPoint(x: self.frame.size.width/2, y: self.frame.size.height/2 + 300)
        }else{
            //set propeties for iPhone
            gameNameLabel.fontSize = 50
            gameNameLabel.position = CGPoint(x: self.frame.size.width/2, y: self.frame.size.height/2 + 135)
        }
        //add to child
        self.addChild(gameNameLabel)
    }
    
    //MARK:: Play Button Properties
    func playButtonProperties(){
        //playButton properties
        playButton.zPosition = 3
        playButton.position = CGPoint(x: self.frame.size.width/2, y: self.frame.size.height/2)
        playButton.name = "playButton"
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set scale for playButton
            playButton.setScale(1)
        }else{
            //set scale for playButton
            playButton.setScale(0.7)
        }
        //add to child
        self.addChild(playButton)
    }
    
    //MARK: Credits Button Properties
    func creditButtonProperties(){
        //creditButton properties
        creditButton.zPosition = 2
        creditButton.text = "Credits"
        creditButton.name = "creditButton"
        creditButton.fontColor = UIColor.white
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            creditButton.fontSize = 40
            creditButton.position = CGPoint(x: self.scene!.size.width/2 , y: self.scene!.size.height/2 - 370)
        }else{
            //set propeties for iPhone
            creditButton.fontSize = 25
            creditButton.position = CGPoint(x: self.scene!.size.width/2 , y: self.scene!.size.height/2 - 180)
        }
        //add
        self.addChild(creditButton)
    }
    
    //MARK: MoveTurtleUpLabel
    func moveTurtleUpLabelProperties(){
        //moveTurtleUpLabel properties
        moveTurtleUpLabel.zPosition = 3
        moveTurtleUpLabel.text = "Tap above turtle to float up"
        moveTurtleUpLabel.fontColor = UIColor.blue
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            moveTurtleUpLabel.fontSize = 40
            moveTurtleUpLabel.position = CGPoint(x: self.frame.size.width/2 - 250, y: self.frame.size.height/2 + 200)
        }else{
            //set propeties for iPhone
            moveTurtleUpLabel.fontSize = 25
            moveTurtleUpLabel.position = CGPoint(x: self.frame.size.width/2 - 200, y: self.frame.size.height/2 + 80)
        }
        //add
        self.addChild(moveTurtleUpLabel)
    }
    
    //MARK: MoveTurtleDownLabel
    func moveTurtleDownLabelProerties(){
        //moveTurtleDownLabel properties
        moveTurtleDownLabel.zPosition = 3
        moveTurtleDownLabel.text = " Tap below turtle to dive down"
        moveTurtleDownLabel.fontColor = UIColor.blue
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            moveTurtleDownLabel.position = CGPoint(x: self.frame.size.width/2 - 250, y: self.frame.size.height/2 - 250)
            moveTurtleDownLabel.fontSize = 40
        }else{
            //set propeties for iPhone
            moveTurtleDownLabel.position = CGPoint(x: self.frame.size.width/2 - 190, y: self.frame.size.height/2 - 130)
            moveTurtleDownLabel.fontSize = 25
        }
        //add
        self.addChild(moveTurtleDownLabel)
    }
    
    //MARK: Move Arrow Up Properites
    func arrowUpProperties(){
        //set arrowUp properties
        arrowUp.zPosition = 3
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            arrowUp.setScale(0.6)
            arrowUp.position = CGPoint(x: self.frame.size.width/2 - 280, y: self.frame.size.height/2 + 120)
        }else{
            //set propeties for iPhone
            arrowUp.setScale(0.3)
            arrowUp.position = CGPoint(x: self.frame.size.width/2 - 240, y: self.frame.size.height/2 + 50)
        }
        //add
        self.addChild(arrowUp)
    }
    
    //MARK: Turtle Sprite Properties
    func turtleSpriteProperties(){
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set position of turtle on screen
            gameScene.turtleSprite.position = CGPoint(x: self.frame.size.width/2 - 280, y: self.frame.size.height/2 - 20)
            gameScene.turtleSprite.setScale(0.5)
            
        }else{
            //set position of turtle on screen
            gameScene.turtleSprite.position = CGPoint(x: self.frame.size.width/2 - 230, y: self.frame.size.height/2 - 20)
            gameScene.turtleSprite.setScale(0.3)
        }
        //add turtle as child node to the scene
        self.addChild(gameScene.turtleSprite)
    }
    
    //MARK: Move Arrow Down Properties
    func arrowDownProperties(){
        //set arrowDown properties
        arrowDown.zPosition = 3
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            arrowDown.setScale(0.6)
            arrowDown.position = CGPoint(x: self.frame.size.width/2 - 300, y: self.frame.size.height/2 - 150)
        }else{
            //set propeties for iPhone
            arrowDown.setScale(0.3)
            arrowDown.position = CGPoint(x: self.frame.size.width/2 - 240, y: self.frame.size.height/2 - 80)
        }
        //add
        self.addChild(arrowDown)
    }
    
    //MARK: Game Center Label Properties
    func gameCenterLabelProperties(){
        //set gameCenterLabel properites
        gameCenterLabel.zPosition = 3
        gameCenterLabel.text = "Game Center"
        gameCenterLabel.name = "gameCenterLabel"
        gameCenterLabel.fontColor = UIColor.white
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            gameCenterLabel.fontSize = 40
            gameCenterLabel.position = CGPoint(x: self.frame.size.width/2 - 360, y: self.frame.size.height/2 - 370)
        }else{
            //set propeties for iPhone
            gameCenterLabel.fontSize = 25
            gameCenterLabel.position = CGPoint(x: self.frame.size.width/2 - 260, y: self.frame.size.height/2 - 180)
        }
        //add
        self.addChild(gameCenterLabel)
    }
    
    //MARK: Enemy Text Label Properties
    func enemyTextLabelProperties(){
        //set enemy text label properties
        enemyTextLabel.zPosition = 3
        enemyTextLabel.text = "Avoid Enemies!!!"
        enemyTextLabel.fontColor = UIColor.blue
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            enemyTextLabel.fontSize = 40
            enemyTextLabel.position = CGPoint(x: self.frame.size.width/2 + 320, y: self.frame.size.height/2 + 200)
        }else{
            //set propeties for iPhone
            enemyTextLabel.fontSize = 25
            enemyTextLabel.position = CGPoint(x: self.frame.size.width/2 + 200, y: self.frame.size.height/2 + 80)
        }
        //add
        self.addChild(enemyTextLabel)
    }
    
    //MARK: Jelly Sprite Properties
    func jellySpriteProperties(){
        //set position of jelly
        jellySprite.zPosition = 3
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            jellySprite.setScale(0.6)
            jellySprite.position = CGPoint(x: self.frame.size.width/2 + 260, y: self.frame.size.height/2 + 100)
        }else{
            //set propeties for iPhone
            jellySprite.setScale(0.3)
            jellySprite.position = CGPoint(x: self.frame.size.width/2 + 165, y: self.frame.size.height/2 + 40)
        }
        //add
        self.addChild(jellySprite)
    }
    
    //MARK: Shark Sprite Properties
    func sharkSpriteProperties(){
        //set position of shark
        sharkSprite.zPosition = 3
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            sharkSprite.setScale(0.5)
            sharkSprite.position = CGPoint(x: self.frame.size.width/2 + 375, y: self.frame.size.height/2 - 25)
        }else{
            //set propeties for iPhone
            sharkSprite.setScale(0.3)
            sharkSprite.position = CGPoint(x: self.frame.size.width/2 + 255, y: self.frame.size.height/2)
        }
        //add
        self.addChild(sharkSprite)
    }
    
    //MARK: Kelp Collection Label Properties
    func kelpCollectionLabelProperties(){
        //set kelpCollectionLabel properties
        kelpCollectionLabel.zPosition = 3
        kelpCollectionLabel.text = "Collect Kelp Coins"
        kelpCollectionLabel.fontColor = UIColor.blue
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            kelpCollectionLabel.fontSize = 40
            kelpCollectionLabel.position = CGPoint(x: self.frame.size.width/2 + 320, y: self.frame.size.height/2 - 150)
        }else{
            //set propeties for iPhone
            kelpCollectionLabel.fontSize = 25
            kelpCollectionLabel.position = CGPoint(x: self.frame.size.width/2 + 200, y: self.frame.size.height/2 - 75)
        }
        //add
        self.addChild(kelpCollectionLabel)
    }
    
    //MARK: Kelp Coin Properties
    func kelpCoinProperties(){
        //set position of kelp coin
        kelpSprite.zPosition = 3
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            kelpSprite.setScale(0.8)
            kelpSprite.position = CGPoint(x: self.frame.size.width/2 + 250, y: self.frame.size.height/2 - 240)
        }else{
            //set propeties for iPhone
            kelpSprite.setScale(0.4)
            kelpSprite.position = CGPoint(x: self.frame.size.width/2 + 175, y: self.frame.size.height/2 - 120)
        }
        //add
        self.addChild(kelpSprite)
    }
    
    //MARK: Kelp Coin 2 Properties
    func kelpCoin2Properties(){
        //set position of kelp2 coin
        kelpSprite2.zPosition = 3
        //check if iPad
        if UIDevice.current.model == "iPad"{
            //set properties for iPad
            kelpSprite2.setScale(0.8)
            kelpSprite2.position = CGPoint(x: self.frame.size.width/2 + 320, y: self.frame.size.height/2 - 240)
        }else{
            //set propeties for iPhone
            kelpSprite2.setScale(0.4)
            kelpSprite2.position = CGPoint(x: self.frame.size.width/2 + 225, y: self.frame.size.height/2 - 120)
        }
        //add
        self.addChild(kelpSprite2)
        
    }
    
}
