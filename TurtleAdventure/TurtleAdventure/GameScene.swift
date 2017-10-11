//
//  GameScene.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/5/16.
//  Copyright (c) 2016 Scott O'Hara. All rights reserved.
//
import SpriteKit

//import AVfoundation for AVMusicPlayer
import AVFoundation

//import CoreMotion for accelerometer
//import CoreMotion

//Add AvAudioDelegate for AVMusicPlayer
class GameScene: SKScene, AVAudioPlayerDelegate{
    
    //MARK: Stored Properties
    // set background and enemy velocity
    let bgVelocity: CGFloat = 3.0
    let jellyVelocity: CGFloat = 5.0
    
    //init Turtle Sprite nodes
    let turtleSprite = SKSpriteNode(imageNamed: "turtle")
    let deadTurtle = SKSpriteNode(imageNamed: "dead1")
    let spinningShellSprite = SKSpriteNode(imageNamed: "shell")
    
    //init  SKSpriteNode class's
    var jellyClass: JellySprite!
    var kelpClass: KelpSprite!
    var sharkClass: SharkSprite!
    var shellClass: ShellSprite!
    
    //flag to check values for sprites on and off screen
    var sharkFlag: Bool = false
    var kelpFlag: Bool = false
    
    //flag for turtle to be invisible
    var turtleInvisibleFlag = false
    
    //bool for gameover - set to true then present gameOverScene
    var lostGame: Bool = false
    
    //check if game is paused
    var pauseFlag: Bool = false
    
    //check for extraLife
    var extraLifeFlag: Bool = false
    
    //CFTimeInterval used for timing on spawning enemies
    var lastSpawnAdded: CFTimeInterval = 0.0
    
    //timer for how long turtle alpha is lighter
    var turtleAlphaTimer: CFTimeInterval = 0.0
    
    //time of how long turtle is invisible
    let turtleInvisibleTime = 3.0
    
    //create counter variable to spawn enimies at certain times
    var count = 0
    
    //check how many enemies spawned
    var numberEnemiesSpawned = 0
    
    //Score
    var kelpScore = 0
    let kelpLabel = SKLabelNode(fontNamed: "Noteworthy")
    
    //Pause Button
    let pauseButton = SKSpriteNode(imageNamed: "pause")
    let pauseButtonPressed = SKTexture(imageNamed: "paused")
    
    //Extra Life
    let emptyExtraLife = SKSpriteNode(imageNamed: "emptyLife")
    let fullExtraLife = SKSpriteNode(imageNamed: "fullLife")
    let animatedLifeSprite = SKSpriteNode(imageNamed: "fullLife")
    
    //achievments instance
    let achievements = Achievements()
    
    //leaderboard score instance
    let score = Score()
    
    //Testing Accelerometer
    //    let motionManager: CMMotionManager = CMMotionManager()
    
    //MARK: SKActions
    //init actions to move turtle node
    //action to move the turtle up by 30 points
    var moveTurtleUp: SKAction = SKAction.moveBy(x: 0, y: 35, duration: 0.1)
    
    //action to move the turtke down by 30 points
    var moveTurtleDown: SKAction = SKAction.moveBy(x: 0, y: -35, duration: 0.1)
    
    //action to remove sprite from parent
    let done: SKAction = SKAction.removeFromParent()
    
    //action for animating turtle dying
    let deadAction = SKAction.animate(with: [SKTexture(imageNamed: "dead1"),SKTexture(imageNamed: "dead2"),SKTexture(imageNamed: "dead3"), SKTexture(imageNamed: "dead4"),SKTexture(imageNamed: "dead5"), SKTexture(imageNamed: "dead6"), SKTexture(imageNamed: "dead7")], timePerFrame: 0.15)
    
    //MARK: Sound - SKActions for sound effects/ AVMusicPlayer
    //load mp3 once and only once
    //SKActions for sound effects
    var jellySound: SKAction = SKAction.playSoundFileNamed("jelly.mp3", waitForCompletion: false)
    var sharkSound: SKAction = SKAction.playSoundFileNamed("Chomp.wav", waitForCompletion: false)
    var kelpSound: SKAction = SKAction.playSoundFileNamed("Reward.wav", waitForCompletion: false)
    let gameOver: SKAction = SKAction.playSoundFileNamed("gameOver.mp3", waitForCompletion: false)
    
    //AV Audio Player
    var gameMusicPlayer: AVAudioPlayer?
    
    //stop audio playback
    let stopSound =  SKAction.stop()
    
    //MARK: Physics Categories
    struct physicsCategories{
        
        //two categories for collision detection
        static let turtleCategory: UInt32 = 0x1 << 0
        static let jellyCategory: UInt32 = 0x1 << 1
        static let sharkCategory: UInt32 = 0x1 << 2
        static let kelpCategory: UInt32 = 0x1 << 3
        static let shellCategory: UInt32 = 0x1 << 4
        static let spinningShellCategory: UInt32 = 0x1 << 5
        static let extraLifeCategory: UInt32 = 0x1 << 6
    }
    
    //MARK: Did Move To View
    override func didMove(to view: SKView) {
        /* Setup your scene here */
        
        //Test Accelerometer
        //motionManager.startAccelerometerUpdates()
        
        //endlessly scrolling BG
        self.scrollingBackground()
        
        //add turtle
        self.spawnTurtle()
        
        //Score Label
        kelpCollectionLabel()
        
        //pause game
        pauseGameButton()
        
        //extra lifes
        extraLifePlacement()
        
        //play background music
        playGameMusic()
        
        //save previous high score
        score.previousHighScore = score.scoreDefaults.integer(forKey: "previousHighScore")
        
        //max a one pixel line collison wall around the screen size
        let border = SKPhysicsBody(edgeLoopFrom: self.frame)
        self.physicsBody = border
        
        //set up the physics world to have no gravity, and sets the scene as the delegate to be notified when two physics bodies collide
        // Making self delegate of physics world
        self.physicsWorld.gravity = CGVector(dx: 0.0, dy: 0.0)
        self.physicsWorld.contactDelegate = self
    }
    
    //MARK: Touches Began
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        /* Called when a touch begins */
        
        for touch in touches {
            
            //tracks the location of touches on the screen
            let location = touch.location(in: self)
            let pauseLocation = touch.location(in: self)
            let nodeSelected = self.atPoint(pauseLocation)
            
            //check pause flag
            if pauseFlag == false{
                
                //check if pause node was selected
                if nodeSelected.name == pauseButton.name{
                    print("paused:")
                    
                    //set flag to true
                    pauseFlag = true
                    
                }else{
                    
                    //if location is higher than the turtles location, then move turtle up
                    if location.y > turtleSprite.position.y{
                        
                        //setting offset from the edge, so the turtle completely stays in bounds of the scene
                        if turtleSprite.position.y < self.frame.size.height - self.turtleSprite.size.height{
                            
                            if UIDevice.current.model == "iPad"{
                                
                                //action to move the turtle up by 60 points
                                let moveTurtleUp60: SKAction = SKAction.moveBy(x: 0, y: 60, duration: 0.1)
                                turtleSprite.run(moveTurtleUp60)
                                
                            }else{
                                
                                //move the turtle up by 30 points
                                turtleSprite.run(moveTurtleUp)
                            }
                        }
                        
                    }else{
                        
                        if turtleSprite.position.y > self.turtleSprite.size.height/2{
                            
                            if UIDevice.current.model == "iPad"{
                                
                                //action to move the turtke down by 60 points
                                let moveTurtleDown60: SKAction = SKAction.moveBy(x: 0, y: -60, duration: 0.1)
                                turtleSprite.run(moveTurtleDown60)
                                
                            }else{
                                
                                //move down when user touch location is below the turtle
                                turtleSprite.run(moveTurtleDown)
                            }
                        }
                    }//end of moveTurtle check
                    
                }//end of nodeSelected
                
            }else{//pauseFlag else
                
                if nodeSelected.name == pauseButton.name{
                    print("play")
                    //set pause button texture
                    pauseButton.texture = SKTexture(imageNamed: "pause")
                    //set flag to false
                    pauseFlag = false
                    //unpause game
                    self.scene?.view?.isPaused = false
                    //start music
                    gameMusicPlayer?.play()
                }
            }//end of pause flag check
            
        }//end of loop
    }
    
    //MARK: Update Time
    override func update(_ currentTime: TimeInterval) {
        /* Called before each frame is rendered */
        
        //Testing Accelerometer
        //        print(motionManager.accelerometerData?.acceleration.y)
        //        motionManager.accelerometerData?.acceleration.y
        
        //set turtle to be invisible after respawn from powerup turtleInvisibleTime = 3.0
        if currentTime - self.turtleAlphaTimer > turtleInvisibleTime{
            
            //set timer to current
            self.turtleAlphaTimer = currentTime
            
            //reset alpha of turtle to 1
            self.turtleSprite.alpha = 1
            
            //set turtleInvisibleFlag to false
            self.turtleInvisibleFlag = false
        }
        
        //pause scene if true in update bc it will rendener once before game pauses
        if pauseFlag == true{
            
            //set paused texture to pauseButton
            pauseButton.texture = pauseButtonPressed
            
            //pause game
            self.scene?.view?.isPaused = true
            
            //pause game music
            gameMusicPlayer?.pause()
            
        }
        
        //add enemies randomly at a different y positions every 3 seconds
        if currentTime - self.lastSpawnAdded > 3{
            
            self.lastSpawnAdded = currentTime + 1
            
            //spawn new enemy
            self.addJelly()
            
            //add count to spawn enemies at diffent times
            count += 1
            print(count)
        }
        
        //move the background
        self.moveBackground()
        
        //spawn jelly
        self.spawnJelly()
        
        //update time to spawn enemeies
        self.updateWithTimeSinceLast(currentTime)
        
        //set score label
        self.kelpLabel.text = "Kelp Collected: " + String(kelpScore)
    }
}

//MARK: SKPhysicsContactDelegate
extension GameScene: SKPhysicsContactDelegate{
    
    func didBegin(_ contact: SKPhysicsContact) {
        
        var firstBody = SKPhysicsBody()
        var secondBody = SKPhysicsBody()
        
        if contact.bodyA.categoryBitMask < contact.bodyB.categoryBitMask {
            
            firstBody = contact.bodyA
            
            secondBody = contact.bodyB
            
        } else {
            
            firstBody = contact.bodyB
            
            secondBody = contact.bodyA
        }//end contactBitMask check
        
        //check if player has extra life
        if extraLifeFlag == true{
            
            //compare physicsCategories
            if firstBody.categoryBitMask == physicsCategories.turtleCategory && secondBody.categoryBitMask == physicsCategories.jellyCategory || secondBody.categoryBitMask == physicsCategories.sharkCategory{
                
                //set exLifeFlag to false
                extraLifeFlag = false
                
                //remove gained exLife
                animatedLifeSprite.removeFromParent()
                
                //reset emptyExLife to animatedLife.position
                self.emptyExtraLife.position = self.animatedLifeSprite.position
                
                //add
                self.addChild(emptyExtraLife)
                
                //set turtleInvisivle to true so turtle isnt harmed by enemey
                self.turtleInvisibleFlag = true
                
                //fade alpha so user knows they were hit
                self.turtleSprite.alpha = 0.8
                
            }//end of physicsCategory check
            
            
        }else{
            
            //compare physicsCategories
            if firstBody.categoryBitMask == physicsCategories.turtleCategory && secondBody.categoryBitMask == physicsCategories.jellyCategory || secondBody.categoryBitMask == physicsCategories.sharkCategory{
                
                // if turtle alpha is one then turtle dies on collision
                if turtleInvisibleFlag == false{
                    
                    //increment number of timesDied
                    achievements.timesDied += 1
                    
                    //stop music player
                    gameMusicPlayer!.stop()
                    
                    //remove sprites
                    removeSprites()
                    
                    //set postion of dead turtle to turtle position
                    deadTurtle.position = turtleSprite.position
                    
                    //add dead turth to parent
                    self.addChild(deadTurtle)
                    
                    //run action
                    deadTurtle.run(SKAction.sequence([gameOver, deadAction, done]), completion: { () -> Void in
                        
                        //set lost game to true to present game over scene
                        self.lostGame = true
                        
                        if self.lostGame == true{
                            
                            //present game over function
                            self.presentGameOver()
                            
                            //MARK: Leaderboards/Achievements
                            //save score to leaderboard
                            self.saveScoreToLeaderboard()
                            
                            //increment schievement
                            self.incrementAchievement()
                            
                            //measurement achievement
                            self.measurementAchievement()
                            
                            //negitive achievement
                            self.negitiveAchievement()
                            
                        }//end of lost game check
                        
                    })//end of completion handler for deadTurtle action
                }
                
            }//end  enemy comparisons of bit masks
        }
        
        //compare physicsCategory for turtle and kelp collision
        if firstBody.categoryBitMask == physicsCategories.turtleCategory && secondBody.categoryBitMask == physicsCategories.kelpCategory{
            
            //play sound when collision happens
            kelpClass.run(kelpSound)
            
            //function to animate kelp
            kelpCollison(secondBody.node!)
            
        }//end kelp comparison
        
        //compare physicsCategory for turtle and shell collision
        if firstBody.categoryBitMask == physicsCategories.turtleCategory && secondBody.categoryBitMask == physicsCategories.shellCategory{
            
            //function to bounce shell around screen (is not affected by enemies)
            shellCollison()
            
        }//end of shell compare
        
        //compare physicsCategory for kelp and spinningShell
        if firstBody.categoryBitMask == physicsCategories.kelpCategory && secondBody.categoryBitMask == physicsCategories.spinningShellCategory{
            
            //play sound when collision happens
            kelpClass.run(kelpSound)
            
            //function to animate kelp
            kelpCollison(firstBody.node!)
            
        }//end of kelp & shell compare
        
        //compare physicsCategory for turtle and extraLife
        if firstBody.categoryBitMask == physicsCategories.turtleCategory && secondBody.categoryBitMask == physicsCategories.extraLifeCategory{
            
            //function to animate extraLife
            extraLifeCollison(secondBody.node!)
            
        }//end of turtle and exLife compare
        
    }//end didBeginContact
}

//link used as a refernece for AVAudioPlayer
//https://developer.apple.com/library/ios/documentation/AVFoundation/Reference/AVAudioPlayerClassReference/
//https://www.hackingwithswift.com/example-code/media/how-to-play-sounds-using-avaudioplayer
