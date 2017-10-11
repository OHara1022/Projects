//
//  JellySprite.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/5/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import SpriteKit

class JellySprite: SKSpriteNode {
    
    override init(texture: SKTexture?, color: UIColor, size: CGSize) {
        
        super.init(texture: texture, color: color, size: size)
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}