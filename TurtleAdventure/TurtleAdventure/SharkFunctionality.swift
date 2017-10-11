//
//  SharkFunctionality.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/11/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import SpriteKit
import UIKit

extension GameScene{

    
    func spawnShark(){
        
        //SKSpriteNode for shark
        let sharkSprite = SharkSprite(imageNamed: "shark1")
        
        //set flag to true
        sharkFlag = true
        
        //set instance of sharkClass to use globally
        sharkClass = sharkSprite
        
        //set height of the node relative to its parent
        sharkSprite.zPosition = 2
        
        //spawn shark randonizing on y axis
        let minY = sharkSprite.size.height/2
        let maxY = self.frame.size.height - minY
        
        let rangeY = maxY - minY
        
        let sharkY = (CGFloat(arc4random()).truncatingRemainder(dividingBy: rangeY)) + minY
        
        //set sprite position
        sharkSprite.position = CGPoint(x: self.frame.size.width + sharkSprite.size.width/2, y: sharkY)
        
        if UIDevice.current.model == "iPad"{
            //scale scrite to proper size
            sharkSprite.setScale(0.5)
        }else{
            //scale scrite to proper size
            sharkSprite.setScale(0.3)
        }
        //add sprite
        self.addChild(sharkSprite)
        
        //physics body
        //size for collision
        sharkSprite.physicsBody = SKPhysicsBody(circleOfRadius: sharkSprite.size.height/2)
        
        //body velocity reduces over time
        sharkSprite.physicsBody?.linearDamping = 0.8
        
        //sets the categoryBitMask to be the enemyCategory I defined earlier
        sharkSprite.physicsBody?.categoryBitMask = physicsCategories.sharkCategory
        
        //contactTestBitMask means what categories of objects this object should notify the contact listener when they intersect
        sharkSprite.physicsBody?.contactTestBitMask = physicsCategories.turtleCategory
        
        //A mask that defines which categories of physics bodies can collide with this physics body.
        sharkSprite.physicsBody?.collisionBitMask = 0
        
        //time interval for range Duration
        let minDuration = 2.0
        let maxDuration = 4.0
        let rangeDuration = maxDuration - minDuration
        let actualDuration = (Double(arc4random()).truncatingRemainder(dividingBy: rangeDuration)) + minDuration
        
        //skactions
        //move shark across enitre screen
        let actionMove = SKAction.move(to: CGPoint(x: -sharkSprite.size.width, y: sharkY), duration:  actualDuration)
        
        //anaimate with textures so shark swims and bites
        let sharkAction = SKAction.animate(with: [SKTexture(imageNamed: "shark1"),SKTexture(imageNamed: "shark2"), SKTexture(imageNamed: "shark3"),SKTexture(imageNamed: "shark4"), SKTexture(imageNamed: "shark5"), SKTexture(imageNamed: "shark6"),SKTexture(imageNamed: "shark1"),SKTexture(imageNamed: "shark2")], timePerFrame: 0.3)
        
        //group actions together
        let groupActions = SKAction.group([actionMove, sharkAction])
        
        //action sequence runs each action in order waits to finish unles specified.
        sharkSprite.run(SKAction.repeatForever(SKAction.sequence([groupActions, done])))
        
        sharkSprite.run(sharkSound)
        
        numberEnemiesSpawned += 1
        
    }
}
