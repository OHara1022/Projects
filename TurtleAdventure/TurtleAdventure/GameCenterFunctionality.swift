//
//  GameCenterFunctionality.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/24/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import Foundation
import SpriteKit
import GameKit

extension GameScene{
    
    //Save to Leaderboard
    func saveScoreToLeaderboard(){
        
        //check if highscore is greater then previous score
        if self.score.highScore > self.score.previousHighScore{
            
            //save highScore to leaderboard
            self.score.saveHighScore("grp.turtleadventure", score: self.score.highScore)
            
            //set previous score to highscore
            self.score.previousHighScore = self.score.highScore
            
            //save highScore to NSDefaults
            self.score.scoreDefaults.set(self.score.highScore, forKey: "previousHighScore")
        }
    }//end of save highScore
    
    //Increment Achievement
    func incrementAchievement(){
        
        if self.achievements.coinsCollected > 0 {
            
            print("!!! - \(achievements.coinsCollected)")
            
            //save # of coins collected
            self.achievements.scoreDefaults.set(self.achievements.coinsCollected, forKey:  "coinsCollected")
            
            if self.achievements.coinsCollected == 5{
                //add percent of achievement completed
                self.achievements.incrementCurrentPercent("grp.10kelpCoins", amount: 30)
            }
            
            if self.achievements.coinsCollected == 10{
                //add percent of achievement completed
                self.achievements.incrementCurrentPercent("grp.10kelpCoins", amount: 30)
            }
            
            if self.achievements.coinsCollected == 15{
                //add percent of achievement completed
                self.achievements.incrementCurrentPercent("grp.10kelpCoins", amount: 40)
            }
        }//end of coinCollection
        
    }//end of increment func
    
    //Measurement Achievement
    func measurementAchievement(){
        
        //set number of enemies spawnws to number of enemies avoided to save to game center
        self.achievements.enemiesAvoided = self.numberEnemiesSpawned
        
        if self.achievements.enemiesAvoided > 0 {
            
            //save number of enemies avoided
            self.achievements.scoreDefaults.set(self.achievements.enemiesAvoided, forKey:  "enemiesAvoided")
            
            //if 25 enemies avoided complete achievement
            if self.achievements.enemiesAvoided == 25{
                
                //complete achievement
                self.achievements.incrementCurrentPercent("grp.avoid25Enemies", amount: 100)
            }
        }//end of enemiesAvoided
        
    }//end of measurement func
    
    //Negitive Achievement
    func negitiveAchievement(){
        
        if self.achievements.timesDied > 0 {
            
            //save numver of times died
            self.achievements.scoreDefaults.set(self.achievements.timesDied, forKey: "timesDied")
            
            //check for times dies and coinsCollected
            if self.achievements.timesDied == 3 && self.achievements.coinsCollected == 0{
                print("You Suck")
                
                //complete achievement if conditional is met
                self.achievements.incrementCurrentPercent("grp.timesDies", amount: 100)
            }
        }//end of negitive achievement
        
    }//end of negitiveAchievement
}
//MARK: References:
/*-https://www.youtube.com/watch?v=LS_kGRp7A1E&index=3&list=PLMrKlNcXRwua4X2Unrq2Md1L_TgtnQtle
- http://www.appcoda.com/ios-game-kit-framework/
- https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/GameKit_Guide/GameCenterOverview/GameCenterOverview.html
- https://developer.apple.com/library/ios/documentation/LanguagesUtilities/Conceptual/iTunesConnectGameCenter_Guide/Leaderboards/Leaderboards.html#//apple_ref/doc/uid/TP40013726-CH2-SW1
- https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/GameKit_Guide/Introduction/Introduction.html
- https://itunespartner.apple.com/en/apps/faq/Managing%20Your%20Apps_Game%20Center#26180451

*/
