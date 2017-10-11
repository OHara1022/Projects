//
//  TurtleFunctionality.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/12/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import SpriteKit

extension GameScene{
    
    func spawnTurtle() {
        
        if UIDevice.current.model == "iPad"{
            print("iPad")
            //set position of turtle on screen
            turtleSprite.position = CGPoint(x: self.scene!.size.width/2 - 380 , y: self.scene!.size.height/2)
            
            //set scale of turtle sprite
            turtleSprite.setScale(0.4)
            
        }else{
            
            //set position of turtle on screen
            turtleSprite.position = CGPoint(x: self.scene!.size.width/2 - 250, y: self.scene!.size.height - 200)
            
            //set scale of turtle sprite
            turtleSprite.setScale(0.3)
            
        }
        //add turtle as child node to the scene
        self.addChild(turtleSprite)
        
        //adding spriteKit physics body for collision detection
        turtleSprite.physicsBody = SKPhysicsBody(rectangleOf:  turtleSprite.size)
        
        //setting dynamic to true means that the physics engine will not control the movement of the turtle
        turtleSprite.physicsBody?.isDynamic = false
        
        //sets the categoryBitMask to be the turtleCategory I defined earlier
        turtleSprite.physicsBody?.categoryBitMask = physicsCategories.turtleCategory
    }
}
