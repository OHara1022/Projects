//
//  MainMenu.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/17/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.

import SpriteKit
import GameKit

//This class properties only work on iPhone
class MainMenu: SKScene, GKGameCenterControllerDelegate{
    
    //MARK: Instances of GameScene/Achievements
    //instance of GameScene
    let gameScene = GameScene()
    
    //instance of Achievements
    let achievements = Achievements()
    
    //MARK: Sprite Nodes
    //background image
    let background = SKSpriteNode(imageNamed: "BG")
    
    //move turtle arrows
    let arrowDown = SKSpriteNode(imageNamed: "down")
    let arrowUp = SKSpriteNode(imageNamed: "up")
    
    //create SKSprite Nodes for enemies and collection item
    let jellySprite = SKSpriteNode(imageNamed: "jelly")
    let sharkSprite = SKSpriteNode(imageNamed: "shark3")
    let kelpSprite = SKSpriteNode(imageNamed: "kelp")
    let kelpSprite2 = SKSpriteNode(imageNamed: "kelp2")
    
    //MARK: Sprite Labels
    //play button
    let playButton = SKSpriteNode(imageNamed: "play")
    
    //game name label
    let gameNameLabel = SKLabelNode(fontNamed: "Noteworthy")
    
    //credits button
    let creditButton = SKLabelNode(fontNamed: "Noteworthy")
    
    //move turtle text label
    let moveTurtleUpLabel = SKLabelNode(fontNamed: "Noteworthy")
    let moveTurtleDownLabel = SKLabelNode(fontNamed: "Noteworthy")
    
    //enemy text label
    let enemyTextLabel = SKLabelNode(fontNamed: "Noteworthy")
    
    //kelp collection label
    let kelpCollectionLabel = SKLabelNode(fontNamed: "Noteworthy")
    
    //gameCenter label
    let gameCenterLabel = SKLabelNode(fontNamed: "Noteworthy")
    
    //MARK: didMoveToView
    override func didMove(to view: SKView) {
        
        //MARK: Authenicate Local Player in game center
        authenticateLocalPlayer()
        
        //MARK: Set Game Asset Properties for Main Menu
        backgroundProperties()
        gameNameLabelProperties()
        creditButtonProperties()
        moveTurtleUpLabelProperties()
        moveTurtleDownLabelProerties()
        playButtonProperties()
        arrowUpProperties()
        turtleSpriteProperties()
        arrowDownProperties()
        gameCenterLabelProperties()
        enemyTextLabelProperties()
        jellySpriteProperties()
        sharkSpriteProperties()
        kelpCollectionLabelProperties()
        kelpCoinProperties()
        kelpCoin2Properties()
    }//end didMoveToView
}

//MARK: Touches Began
extension MainMenu{
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        for  touch in touches {
            
            //location that is touched
            let location = touch.location(in: self)
            
            //the point where node is touched
            let playNode = self.atPoint(location)
            
            //transition see with doorOpening
            let showGame = SKTransition.doorsOpenHorizontal(withDuration: 0.5)
            
            //create instance of GameScene
            let playScene = GameScene(size: self.view!.bounds.size)
            
            //create instance of Credits Scene
            let creditsScene = Credits(size: self.view!.bounds.size)
            
            //check if playButton is pressed
            if playNode.name == playButton.name{
                
                //set scene scale mode to .Fill
                playScene.scaleMode = .fill
                
                //present GameScene
                self.view?.presentScene(playScene, transition: showGame)
                
            }//end of playButton check
            
            //check if credits button was pressed
            if playNode.name == creditButton.name{
                
                //set scene scaleMode to .Fill
                creditsScene.scaleMode = .fill
                
                //present Credits Scene
                self.view?.presentScene(creditsScene, transition: showGame)
                
            }//end of credit scene check
            
            //check if game label was pressed
            if playNode.name == gameCenterLabel.name{
                
                //present GameCenterVC
                presentGameCenterVC()
            }
        }//end of touch looop
        
    }//end touchesBegan
}//end of externsion

