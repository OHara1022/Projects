//
//  ShellFunctionality.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/19/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import SpriteKit

extension GameScene{
    
    func spawnShell(){
        
        //create Sprite Node with shell image
        let shellSprite = ShellSprite(imageNamed: "shell")
        
        //set instance of shellClass to use globally
        shellClass = shellSprite
        
        //set height of the node relative to its parent
        shellSprite.zPosition = 4
        
        if UIDevice.current.model == "iPad"{
            shellSprite.setScale(0.15)
        }else{
            //scale sprite to proper size
            shellSprite.setScale(0.1)
        }
        //spawn shell randonizing on y axis
        let minY = shellSprite.size.height/2
        let maxY = self.frame.size.height - minY
        let rangeY = maxY - minY
        
        //random value usong duration values I created above
        let shellYPosition = (CGFloat(arc4random()).truncatingRemainder(dividingBy: rangeY)) + minY
        
        //set poision for shellSprite
        shellSprite.position = CGPoint(x: self.frame.size.width + shellSprite.size.width / 2, y: shellYPosition)
        
        //add shellSprite to parent
        self.addChild(shellSprite)
        
        //physics body
        shellSprite.physicsBody = SKPhysicsBody(circleOfRadius: shellSprite.size.height)
        shellSprite.physicsBody?.categoryBitMask = physicsCategories.shellCategory
        shellSprite.physicsBody?.contactTestBitMask = physicsCategories.turtleCategory
        shellSprite.physicsBody?.collisionBitMask = 0
        
        //time interval for range Duration
        let minDuration = 2.0
        let maxDuration = 4.0
        let rangeDuration = maxDuration - minDuration
        
        //random range passing the duration values created above
        let actualDuration = (Double(arc4random()).truncatingRemainder(dividingBy: rangeDuration)) + minDuration
        
        //SKactions
        //move kelp across entire screen
        let actionMove = SKAction.move(to: CGPoint(x: -shellSprite.size.width, y: shellYPosition), duration: actualDuration)
        
        //run actions
        shellSprite.run(SKAction.sequence([actionMove, done]))
    }//end of spawnShell
    
    func shellCollison(){
        
        //remove shell that spawn from screen
        shellClass.removeFromParent()
        
        //remove turtleSprite from screen
        turtleSprite.removeFromParent()
        
        //set spinningShell postion to turtles position
        spinningShellSprite.position = turtleSprite.position
        
        //scale image
        spinningShellSprite.setScale(0.1)
        
        //add spinningShell to parent
        self.addChild(spinningShellSprite)
        
        //physics body
        spinningShellSprite.physicsBody = SKPhysicsBody(circleOfRadius: spinningShellSprite.size.height/4)
        
        //sets the categoryBitMask to be the enemyCategory I defined earlier
        spinningShellSprite.physicsBody?.categoryBitMask = physicsCategories.spinningShellCategory
        
        //contactTestBitMask means what categories of objects this object should notify the contact listener when they intersect
        spinningShellSprite.physicsBody?.contactTestBitMask = physicsCategories.kelpCategory
        
        //The roughness of the surface of the physics body.
        spinningShellSprite.physicsBody?.friction = 0.0
        
        //The bounciness of the physics body.
        spinningShellSprite.physicsBody?.restitution = 1.0
        
        //Applies an impulse to the center of gravity of a physics body.
        spinningShellSprite.physicsBody?.applyImpulse(CGVector(dx: 10.0, dy: 10.0))
        
        let shellAction = SKAction.animate(with: [SKTexture(imageNamed: "shell")], timePerFrame: 2)
        
        spinningShellSprite.run(SKAction.sequence([shellAction, done]), completion: { () -> Void in
            
            //turn turtle flag to true
            self.turtleInvisibleFlag = true
            
            if UIDevice.current.model == "iPad"{
                //set position of turtle on screen
                self.turtleSprite.position = CGPoint(x: self.scene!.size.width/2 - 380 , y: self.scene!.size.height/2)
            }else{
                //reset turtles position
                self.turtleSprite.position = CGPoint(x: self.frame.size.width/2 - 250, y: self.frame.size.height - 200)
            }
            //lower alpha so user knows he is still invinisible
            self.turtleSprite.alpha = 0.5
            
            //re-add turtleSprite to parent
            self.addChild(self.turtleSprite)
            
        })//end of completion for spinningShell action
        
    }//end of shellCollison
    
}
