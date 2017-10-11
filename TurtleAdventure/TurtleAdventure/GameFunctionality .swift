/*
//  GameFunctionality .swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/11/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
*/

import SpriteKit
import AVFoundation

extension GameScene{
    
    //MARK: Parallax Scolling Background
    /* endlessly scrolling background, make two background images instead of one and lay them side-by-side. Then, as you scroll both images from right to left and once one of the images goes off-screen, you simply put it back to the right. */
    func scrollingBackground(){
        
        //loop through BG twice to keep scrolling
        for i in 0 ..< 2{
            
            //get BG image
            let bg = SKSpriteNode(imageNamed: "BG")
            
            //background properties
            bg.zPosition = -5
            bg.size.height = self.scene!.size.height
            bg.size.width = self.scene!.size.width
            bg.position = CGPoint(x: CGFloat(i) * bg.size.width, y: 0)
            bg.anchorPoint = CGPoint.zero
            bg.name = "background"
            
            //add
            self.addChild(bg)
        }
    }
    
    func moveBackground(){
        
        //finds any child with the name "background" and moves it to the left according to the velocity
        self.enumerateChildNodes(withName: "background", using: { (node, stop) -> Void in
            
            if let bg = node as? SKSpriteNode {
                
                bg.position = CGPoint(x: bg.position.x - self.bgVelocity, y: bg.position.y)
                
                // Checks if bg node is completely scrolled off the screen, if yes, then puts it at the end of the other node.
                if bg.position.x < -bg.size.width {
                    
                    bg.position = CGPoint(x: bg.position.x + bg.size.width * 2, y: bg.position.y)
                }
            }
        })
    }
    
    //MARK: Spawn Enemies
    func spawnSprites(){
        
        //spawn enemies
        self.addJelly()
        self.spawnShark()
        
        //spawn collection item
        self.spawnKelp()
    }
    
    //MARK: Remove Sprites from parent & stop sprite sounds
    func removeSprites(){
        
        turtleSprite.removeFromParent()
        jellyClass.removeFromParent()
        
        //if shark is in view then remove
        if sharkFlag == true{
            sharkClass.removeFromParent()
        }
        
        //if kelp is in view the remove
        if kelpFlag == true{
            kelpClass.removeFromParent()
        }
        
        //stop spriteSpawn sounds
        jellySound = stopSound
        sharkSound = stopSound
    }
    
    //MARK: Update Kelp Collection Label
    func kelpCollectionLabel(){
        
        //add kelp score label properties
        kelpLabel.text = "Kelp Collected: " + String(kelpScore)
        if UIDevice.current.model == "iPad"{
            kelpLabel.fontSize = 50
            kelpLabel.position = CGPoint(x: self.frame.size.width/2 , y: self.frame.size.height - 60)
        }else{
            kelpLabel.fontSize = 30
            kelpLabel.position = CGPoint(x: self.frame.size.width/2 , y: self.frame.size.height - 50)
        }
        kelpLabel.fontColor = SKColor.green
        kelpLabel.zPosition = 4
        self.addChild(kelpLabel)
    }
    
    //MARK: Pause Game
    func pauseGameButton(){
        
        //check if iPad
        if UIDevice.current.model == "iPad"{
            pauseButton.setScale(0.3)
            //set position for iPad
            pauseButton.position = CGPoint(x: self.frame.size.width/2 + 460, y: self.frame.size.height - 45)
        }else{
            pauseButton.setScale(0.2)
            //set position for iPhone
            pauseButton.position = CGPoint(x: self.frame.size.width/2 + 300, y: self.frame.size.height - 35)
            
        }
        //add pause button properties
        pauseButton.zPosition = 3
        pauseButton.name = "pause"
        self.addChild(pauseButton)
    }
    
    //MARK: Extra Life Properties
    func extraLifePlacement(){
        
        if UIDevice.current.model == "iPad"{
            //set position
            fullExtraLife.position = CGPoint(x: self.frame.size.width/2 - 460, y: self.frame.size.height - 45)
            emptyExtraLife.position = CGPoint(x: self.frame.size.width/2 - 390, y: self.frame.size.height - 45)
            //set scale
            fullExtraLife.setScale(0.25)
            emptyExtraLife.setScale(0.25)
            
        }else{
            //set position
            fullExtraLife.position = CGPoint(x: self.frame.size.width/2 - 300, y: self.frame.size.height - 35)
            emptyExtraLife.position = CGPoint(x: self.frame.size.width/2 - 260, y: self.frame.size.height - 35)
            //set scale
            fullExtraLife.setScale(0.15)
            emptyExtraLife.setScale(0.15)
        }
        //add fullExtaLife properities
        fullExtraLife.zPosition = 3
        fullExtraLife.name = "fullExtraLife"
        self.addChild(fullExtraLife)
        
        //add emptyExtraLife properites
        emptyExtraLife.zPosition = 3
        emptyExtraLife.name = "extraLife"
        self.addChild(emptyExtraLife)
    }
    
    //MARK: Update Time to spawn enimies
    func updateWithTimeSinceLast(_ timeSinceLast: CFTimeInterval){
        
        //check count to spawn certain enemies
        if count > 1{
            
            //every 1.5 seconds spawn
            if timeSinceLast - self.lastSpawnAdded > 2.5{
                
                self.lastSpawnAdded = timeSinceLast
                
                //spawn enemies
                self.addJelly()
                //spawn collection item
                self.spawnKelp()
                
                //add one to count
                count += 1
                print(count)
            }
        }
        //check count to spawn certain enemies
        if count > 3{
            
            //every 1 second spawn
            if timeSinceLast - self.lastSpawnAdded > 2{
                print("hit")
                
                self.lastSpawnAdded = timeSinceLast
                
                //spawn enemies
                self.spawnSprites()
                
                //add one to count
                count += 1
            }
        }
        //check count to spawn certain enemies
        if count > 5{
            
            if timeSinceLast - self.lastSpawnAdded > 1.8{
                
                self.lastSpawnAdded = timeSinceLast
                
                //spawn enemies
                spawnSprites()
                
                //spawn shell powerup
                self.spawnShell()
                
                //add one to count
                count += 1
            }
        }
        //check count to spawn certain enemies
        if count > 7{
            
            //every half second spawn enemy
            if timeSinceLast - self.lastSpawnAdded > 1.6{
                print("Fast Time Hit")
                
                self.lastSpawnAdded = timeSinceLast
                
                //spawn enemies
                spawnSprites()
                
                //add one to count
                count += 1
            }
        }
        //check count to spawn certain enemies
        if count > 9{
            
            if timeSinceLast - self.lastSpawnAdded > 1.4{
                
                self.lastSpawnAdded = timeSinceLast + 1
                
                //spawn enemy
                self.addJelly()
                
                //spawn extraLife powerUP
                self.spawnExtraLife()
                
                //add one to count
                count += 1
            }
        }
        //check count to spawn certain enemies
        if count > 10{
            
            if timeSinceLast - self.lastSpawnAdded > 1{
                
                self.lastSpawnAdded = timeSinceLast
                
                //spawn shell powerUp
                self.spawnShell()
                
                //spawn enemies
                self.spawnSprites()
                
                //add one to count
                count += 1
            }
        }
        
        //check count see if user won game
        if count > 16{
            print("You Win:")
            
            //remove sprites
            self.removeSprites()
            
            //stop music player
            gameMusicPlayer?.stop()
            
            //present win screen and kelp collected
            self.presentYouWon()
            
            //MARK:Completion Achievement
            //set gameWon to true
            achievements.gameWon = true
            
            //check if game won = true
            if achievements.gameWon == true{
                print("You Won Achievement")
                
                //complete achievement
                self.achievements.incrementCurrentPercent("grp.youWin", amount: 100)
            }
            
            //check if achievements were completed
            saveScoreToLeaderboard()
            measurementAchievement()
            incrementAchievement()
        }//end of you win check
    }
    
    //MARK: Game Music
    func playGameMusic(){
        
        //path to background music
        let musicPath = Bundle.main.path(forResource: "Background", ofType: "wav")
        //NSURL passing the path so music player can play music
        let musicURL = URL(fileURLWithPath: musicPath!)
        
        //set up audio player
        do{
            
            gameMusicPlayer = try AVAudioPlayer(contentsOf: musicURL)
            
        }catch{
            
            print("Failed with Error: Problem loading music file")
        }
        
        //check if music player is available
        if gameMusicPlayer != nil{
            
            //set delegate to self
            gameMusicPlayer?.delegate = self
            //load music into memeory
            gameMusicPlayer?.prepareToPlay()
        }
        //if music player isnt playing (play music)
        if gameMusicPlayer?.isPlaying == false{
            
            //play music
            gameMusicPlayer?.play()
        }
    }
    
    //MARK: Present Game Over Scene
    func presentGameOver(){
        
        //size of game over scene
        let gameOverScene = GameOverScene(size: self.frame.size)
        
        //transition to game over scene w/ doors closing
        let gameOverOption = SKTransition.doorsCloseHorizontal(withDuration: 1)
        
        //present gameOverScene
        self.view?.presentScene(gameOverScene, transition: gameOverOption)
        
        //Testing
        //gameOverScene = kelpScore
        //print("Final: " + String(gameOverScene.finalScore))
        //func instanceFromNib() -> UIView {
        //
        //return UINib(nibName: "GameOverView", bundle: nil).instantiateWithOwner(nil, options: nil)[0] as! UIView
        //
        //}
        //let view = instanceFromNib()
        //
        //self.view!.addSubview(view)
    }
    
    //MARK: Present You Won Scene
    func presentYouWon(){
        
        //size of yout won scene
        let youWonScene = YouWonScene(size: self.frame.size)
        
        //transition of doors closing when player wins
        let youWonOption = SKTransition.doorsCloseHorizontal(withDuration: 1)
        
        //present youWonScene to player
        self.view?.presentScene(youWonScene, transition: youWonOption)
        
    }
}
