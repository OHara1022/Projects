//
//  YouWonScene.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/11/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import SpriteKit

class YouWonScene: SKScene {
    
    let achievements = Achievements()
    
    fileprivate func youWinMessage(){
        
        self.backgroundColor = SKColor.blue
        
        let message = "You Won!!!"
        
        let youWonLabel = SKLabelNode(fontNamed: "Noteworthy")
        
        youWonLabel.text = message
        
        youWonLabel.fontSize = 60
        
        youWonLabel.fontColor = SKColor.white
        
        youWonLabel.position = CGPoint(x: self.size.width/2, y: self.size.height/2 + 110)
        
        self.addChild(youWonLabel)
    }
    
    fileprivate func kelpCollectedMessage(){
        
        let kelpScore = "Kelp Collected: " + String(achievements.coinsCollected)
        
        let kelpScoreLabel = SKLabelNode(fontNamed: "Noteworthy")
        
        kelpScoreLabel.text = kelpScore
        
        kelpScoreLabel.fontSize = 40
        
        kelpScoreLabel.fontColor = SKColor.white
        
        kelpScoreLabel.position = CGPoint(x: self.size.width/2, y: self.size.height/2)
        
        self.addChild(kelpScoreLabel)
    }
    
    fileprivate func replayMesssage(){
        
        let replayMessage = "Play Again?"
        
        let replayButton = SKLabelNode(fontNamed: "Noteworthy")
        
        replayButton.text = replayMessage
        
        replayButton.fontColor = SKColor.white
        
        replayButton.position = CGPoint(x: self.size.width/2, y: 90)
        
        replayButton.name = "replay"
        
        self.addChild(replayButton)
    }
    
    override init(size: CGSize) {
        super.init(size: size)
        
        youWinMessage()
        
        replayMesssage()
        
//        kelpCollectedMessage()
    }
    
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
