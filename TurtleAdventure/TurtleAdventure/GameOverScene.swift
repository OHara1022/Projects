//
//  GameOverScene.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/5/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

//import UIKit
import SpriteKit

class GameOverScene: SKScene {

    //set game over message properties
    fileprivate func gameOverMessage(){
        
        self.backgroundColor = SKColor.blue
        
        let message = "Game Over"
        
        let gameOverLabel = SKLabelNode(fontNamed: "Noteworthy")
        
        gameOverLabel.text = message
        
        gameOverLabel.fontSize = 60
        
        gameOverLabel.fontColor = SKColor.white
        
        gameOverLabel.position = CGPoint(x: self.size.width/2, y: self.size.height/2 + 110)
        
        self.addChild(gameOverLabel)
        
    }
    
    //still testing to get kelp score to store and present when gameOverScene is presented
      fileprivate func kelpCollectedMessage(){
        
        let kelpScore = "Kelp Collected: \(achievements.coinsCollected)"
        
        let kelpScoreLabel = SKLabelNode(fontNamed: "Noteworthy")
        
        kelpScoreLabel.text = kelpScore
        
        kelpScoreLabel.fontSize = 40
        
        kelpScoreLabel.fontColor = SKColor.white
        
        kelpScoreLabel.position = CGPoint(x: self.size.width/2, y: self.size.height/2)
        
        self.addChild(kelpScoreLabel)
        
        print(kelpScore)
    }
    
    //set replay game properties
    fileprivate func replayMesssage(){
        
        let replayMessage = "Play Again?"
        
        let replayButton = SKLabelNode(fontNamed: "Noteworthy")
        
        replayButton.text = replayMessage
        
        replayButton.fontColor = SKColor.white
        
        replayButton.position = CGPoint(x: self.size.width/2, y: 90)
        
        replayButton.name = "replay"
        
        self.addChild(replayButton)
        
    }
    
    let achievements = Achievements()
    
    override init(size: CGSize) {
        super.init(size: size)
        
   
        //present messages
        gameOverMessage()
        replayMesssage()
        
//        kelpCollectedMessage()

    }
    
    //set up touches begin on replay message and present gameScene
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        for  touch in touches {
            
            let location = touch.location(in: self)
            
            let replayNode = self.atPoint(location)
            
            if replayNode.name == "replay" {
                
                let showGame = SKTransition.doorsOpenHorizontal(withDuration: 0.5)
                
                let playScene = GameScene(size: self.view!.bounds.size)
                
                playScene.scaleMode = .fill
                
                self.view?.presentScene(playScene, transition: showGame)
              
            }
        }
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

}
