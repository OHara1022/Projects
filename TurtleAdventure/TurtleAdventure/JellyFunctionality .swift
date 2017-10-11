//
//  JellyFunctionality .swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/11/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.

import SpriteKit

extension GameScene{
    
    func addJelly(){
        
        //create SKSprite Node for jelly
        let jellySprite = JellySprite(imageNamed: "jelly")
        
        if UIDevice.current.model == "iPad"{
            jellySprite.setScale(0.6)
        }else{
            //set the xScale and yScale properties of the node.
            jellySprite.setScale(0.4)
            
        }
        //set instance of jellyClass to use globally
        jellyClass = jellySprite
        
        //selecting a random y position to spawn jelly
        let randomJelly = CGFloat(arc4random_uniform(UInt32(self.frame.size.height)))
        
        //set height of the node relative to its parent
        jellySprite.zPosition = 1
        
        //set jellySprite y position to spawn randomly
        jellySprite.position = CGPoint(x: self.frame.size.width + jellySprite.size.width/2, y: randomJelly)
        
        //name node
        jellySprite.name = "jellyFish"
        
        //add jelly node to scene
        self.addChild(jellySprite)
        
        // adding spriteKit physics body for collision detection
        //create a circleOfRadius physics body for the jelly
        jellySprite.physicsBody = SKPhysicsBody(circleOfRadius: jellySprite.size.height/3)
        
        //body velocity reduces over time in the system
        //simiualate moving through water .6 or .7 for water
        jellySprite.physicsBody?.linearDamping = 0.8
        
        //sets the categoryBitMask to be the enemyCategory I defined earlier
        jellySprite.physicsBody?.categoryBitMask = physicsCategories.jellyCategory
        
        //contactTestBitMask means what categories of objects this object should notify the contact listener when they intersect
        jellySprite.physicsBody?.contactTestBitMask = physicsCategories.turtleCategory
        
        //A mask that defines which categories of physics bodies can collide with this physics body.
        jellySprite.physicsBody?.collisionBitMask = 0
        
        //play sound when jelly is added
        jellySprite.run(jellySound)
        
        numberEnemiesSpawned += 1
        
        
    }//end of addJelly
    
    func spawnJelly(){
        
        //finds any child with the name "jellyFish" and moves it to the left according to the velocity
        self.enumerateChildNodes(withName: "jellyFish", using: { (node, stop) -> Void in
            
            if let enemyJelly = node as? SKSpriteNode {
                
                //position to move jelly left according to its velocity
                //enemyJelly.position = CGPoint(x: enemyJelly.position.x - self.enemyVelocity, y: enemyJelly.position.y)
                enemyJelly.position = CGPoint(x: enemyJelly.position.x - self.jellyVelocity, y: enemyJelly.position.y)
                
                //if sprite x postiton is less then 0 remove sprite from parent
                if enemyJelly.position.x < 0 {
                    
                    //remove from parent
                    enemyJelly.removeFromParent()
                }
            }
        })
        
    }//end of spawnJelly
    
}
