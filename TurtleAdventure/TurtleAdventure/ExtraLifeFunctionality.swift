//
//  ExtraLifeFunctionality.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/19/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import SpriteKit

extension GameScene{
    
    func spawnExtraLife(){
        
        //create SKSpriteNode with extraLife image
        let extraLifeSprite = SKSpriteNode(imageNamed: "fullLife")
        
        //set height of the node relative to its parent
        extraLifeSprite.zPosition = 3
        
        if UIDevice.current.model == "iPad"{
            extraLifeSprite.setScale(0.26)
        }else{
            //scale sprite to proper size
            extraLifeSprite.setScale(0.18)
        }
        //spawn kelp randonizing on y axis
        let minY = extraLifeSprite.size.height/2
        let maxY = self.frame.size.height - minY
        let rangeY = maxY - minY
        
        //random value usong duration values I created above
        let extraLifeY = (CGFloat(arc4random()).truncatingRemainder(dividingBy: rangeY)) + minY
        
        //set poision for kelpSprite
        extraLifeSprite.position = CGPoint(x: self.frame.size.width + extraLifeSprite.size.width / 2, y: extraLifeY)
        
        //add kelpSprite to parent
        self.addChild(extraLifeSprite)
        
        //physics body
        extraLifeSprite.physicsBody = SKPhysicsBody(circleOfRadius: extraLifeSprite.size.height/2)
        extraLifeSprite.physicsBody?.categoryBitMask = physicsCategories.extraLifeCategory
        extraLifeSprite.physicsBody?.contactTestBitMask = physicsCategories.turtleCategory
        extraLifeSprite.physicsBody?.collisionBitMask = 0
        
        
        //time interval for range Duration
        let minDuration = 2.0
        let maxDuration = 4.0
        let rangeDuration = maxDuration - minDuration
        
        //random range passing the duration values created above
        let actualDuration = (Double(arc4random()).truncatingRemainder(dividingBy: rangeDuration)) + minDuration
        
        //SKactions
        //move extra life across entire screen
        let actionMove = SKAction.move(to: CGPoint(x: -extraLifeSprite.size.width, y: extraLifeY), duration: actualDuration)
        
        //run actions
        extraLifeSprite.run(SKAction.sequence([actionMove, done]))
    }
    
    func extraLifeCollison(_ life: SKNode){
        
        //instance of SKSpriteNode
        let lifeSprite: SKSpriteNode = life as! SKSpriteNode
        
        //scale image when collison with turtle
        life.setScale(0.1)
        
        //move kelpSprite to kelpLable position
        let lifeCollection = SKAction.move(to: emptyExtraLife.position, duration: 0.4)
        
        //run actions
        lifeSprite.run(SKAction.sequence([lifeCollection, done]), completion: { () -> Void in
            
            self.extraLifeFlag = true
            
            if UIDevice.current.model == "iPad"{
                self.animatedLifeSprite.setScale(0.26)
                
            }else{
            self.animatedLifeSprite.setScale(0.15)            }
            self.animatedLifeSprite.position = self.emptyExtraLife.position
            
            self.addChild(self.animatedLifeSprite)
            
            self.emptyExtraLife.removeFromParent()
            
        }) 
    }//end of extraLifeCollision
}
