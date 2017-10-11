//
//  KelpFunctionality .swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/11/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import SpriteKit

extension GameScene{
    
    func spawnKelp(){
        
        //create SKSpriteNode with kelp image
        let kelpSprite = KelpSprite(imageNamed: "kelp")
        
        //set kelpFlag to true
        kelpFlag = true
        
        //set instance of kelpClass to use globally
        kelpClass = kelpSprite
        
        if UIDevice.current.model == "iPad"{
            //scale sprite to proper size
            kelpSprite.setScale(0.6)
        }else{
            //scale sprite to proper size
            kelpSprite.setScale(0.38)
        }
        
        //set height of the node relative to its parent
        kelpSprite.zPosition = 3
        
        //spawn kelp randonizing on y axis
        let minY = kelpSprite.size.height/2
        let maxY = self.frame.size.height - minY
        let rangeY = maxY - minY
        
        //random value usong duration values I created above
        let kelpY = (CGFloat(arc4random()).truncatingRemainder(dividingBy: rangeY)) + minY
        
        //set poision for kelpSprite
        kelpSprite.position = CGPoint(x: self.frame.size.width + kelpSprite.size.width / 2, y: kelpY)
        
        //add kelpSprite to parent
        self.addChild(kelpSprite)
        
        //physics body
        kelpSprite.physicsBody = SKPhysicsBody(rectangleOf: kelpSprite.size)
        
        //body velocity reduces over time in the system
        kelpSprite.physicsBody?.linearDamping = 0.8
        
        //sets the categoryBitMask to be the enemyCategory I defined earlier
        kelpSprite.physicsBody?.categoryBitMask = physicsCategories.kelpCategory
        
        //contactTestBitMask means what categories of objects this object should notify the contact listener when they intersect
        kelpSprite.physicsBody?.contactTestBitMask = physicsCategories.turtleCategory
        
        //a mask that defines which categories of physics bodies can collide with this physics body.
        kelpSprite.physicsBody?.collisionBitMask = 0
        
        //time interval for range Duration
        let minDuration = 2.0
        let maxDuration = 4.0
        let rangeDuration = maxDuration - minDuration
        
        //random range passing the duration values created above
        let actualDuration = (Double(arc4random()).truncatingRemainder(dividingBy: rangeDuration)) + minDuration
        
        //SKactions
        //move kelp across entire screen
        let actionMove = SKAction.move(to: CGPoint(x: -kelpSprite.size.width, y: kelpY), duration: actualDuration)
        
        //animate kelp
        let kelpAction = SKAction.animate(with: [SKTexture(imageNamed: "kelp"), SKTexture(imageNamed: "kelp2"), SKTexture(imageNamed: "kelp"), SKTexture(imageNamed: "kelp2"), SKTexture(imageNamed: "kelp")], timePerFrame: 0.5)
        
        //group actions together
        let groupActions = SKAction.group([actionMove, kelpAction])
        
        //run actions
        kelpSprite.run(SKAction.repeatForever(SKAction.sequence([groupActions, done])))
        
    }//end of spawnKelp
    
    func kelpCollison(_ coin: SKNode){
        
        //instance of SKSpriteNode
        let coinSprite: SKSpriteNode = coin as! SKSpriteNode
        
        //add one to score
        kelpScore += 1
        
        //add kelpScore to coinsCollected achievement
        achievements.coinsCollected = kelpScore
        
        //add kelpScore to leaderboard highScore
        score.highScore = kelpScore
        
        //scale image when collison with turtle
        coinSprite.setScale(0.28)
        
        //move kelpSprite to kelpLable position
        let kelpCollection = SKAction.move(to: kelpLabel.position, duration: 0.8)
        
        //animate kelp while moving
        let kelpAnimation = SKAction.animate(with: [SKTexture(imageNamed: "kelp"), SKTexture(imageNamed: "kelp2"), SKTexture(imageNamed: "kelp"), SKTexture(imageNamed: "kelp2"), SKTexture(imageNamed: "kelp")], timePerFrame: 0.2)
        
        //group actions
        let groupActions = SKAction.group([kelpCollection, kelpAnimation])
        
        //run actions
        coinSprite.run(SKAction.repeatForever(SKAction.sequence([groupActions,done])))
        
    }//end of kelpCollision
}
