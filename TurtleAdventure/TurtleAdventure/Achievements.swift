//
//  Achievements.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/24/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import Foundation
import GameKit
import SpriteKit

class Achievements{
    
    //MARK: Stored Properties
    //Array to hold Achievents in game center dictorary for identifer and achievement
    var gameAchievements = [String: GKAchievement]()
    
    //kelpCoins collected
    var coinsCollected = 0
    
    //number of enemies avoided
    var enemiesAvoided = 0
    
    //check if player won game
    var gameWon: Bool = false
    
    //number of times died
    var timesDied = 0
    
    //Save achievment score w/ NSUserDefaults
    var scoreDefaults: UserDefaults = UserDefaults.standard
    
    //MARK:Achievement Percent
    //func for achievement percent
    func achievementPercentage(){
        
        //Loads previously submitted achievement progress for the local player from Game Center.
        GKAchievement.loadAchievements { (allAchievements, error) -> Void in
            
            if error != nil{
                
                print("gamecenter did not load achievements error: \(String(describing: error))")
                
            }else{
                
                //this will bee nil if there was no progress on any achievements
                if allAchievements != nil{
                    
                    //loop through achievements
                    for achievement in allAchievements!{
                        
                        //optional bind to insure it is an achievement being sent to game center
                        if let singleAchievement: GKAchievement = achievement{
                            
                            //store identifier and achievement
                            self.gameAchievements[achievement.identifier!] = singleAchievement
                        }
                        
                    }//end of loop
                    
                    //iderate through dictionary
                    for (id, achievement) in self.gameAchievements{
                        
                        print("\(id) - \(achievement.percentComplete)")
                    }//end of dictionary loop
                    
                }//end of allAchievements isn't nil
            }
        }//end of completion handeler
        
    }//end of % func
    
    //MARK: Increment Current %
    //add current percentage to achievement
    func incrementCurrentPercent(_ identifier:String, amount: Double){
        
        //check if player is signed in
        if GKLocalPlayer.localPlayer().isAuthenticated{
            
            //bool to check if percent was found for achievement
            var currentPercentFound: Bool = false
            
            //check if there are gameAchievements
            if gameAchievements.count != 0{
                
                //loop through identifier and achievements
                for (id, achievement) in gameAchievements{
                    
                    if id == identifier{
                        
                        //set percent found to true
                        currentPercentFound = true
                        
                        //var for percent complete of achievement
                        var currentPercent: Double = achievement.percentComplete
                        
                        //set the current percent + the amount added from collecting kelp coins
                        currentPercent = currentPercent + amount
                        
                        //report achievement to game center
                        reportAchievement(identifier, percentComplete: currentPercent)
                        
                        //break
                        break
                    }
                }
            }
            
            //if no percent is found
            if currentPercentFound == false {
                
                //report achievement to gameCenter
                reportAchievement(identifier, percentComplete: amount)
            }
            
        }//end of authenticated
    }
    
    //MARK: Report Current Achievement
    //report achievement to game center
    func reportAchievement(_ identifier:String, percentComplete: Double){
        
        //create achievement with identifier
        let achievement = GKAchievement(identifier: identifier)
        
        //store completed percentage
        achievement.percentComplete = percentComplete
        
        //achievement array to hold created achievements
        let achievementArray : [GKAchievement] = [achievement]
        
        //report achievement to game center
        GKAchievement.report(achievementArray, withCompletionHandler: {
            
            //completion
            error -> Void in
            
            if error != nil {
                
                print(error!)
            }else{
                
                print("reports with percent complete \(percentComplete)")
                //remove last loaded achievements from dictionary
                self.gameAchievements.removeAll()
                
                //load new percetages to dictionary
                self.achievementPercentage()
            }
        })//end of completion
        
    }//end of report func
}

//MARK: References
/*-https://www.youtube.com/watch?v=LS_kGRp7A1E&index=3&list=PLMrKlNcXRwua4X2Unrq2Md1L_TgtnQtle
- http://www.appcoda.com/ios-game-kit-framework/
- https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/GameKit_Guide/GameCenterOverview/GameCenterOverview.html
- https://developer.apple.com/library/ios/documentation/LanguagesUtilities/Conceptual/iTunesConnectGameCenter_Guide/Leaderboards/Leaderboards.html#//apple_ref/doc/uid/TP40013726-CH2-SW1
- https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/GameKit_Guide/Introduction/Introduction.html
- https://itunespartner.apple.com/en/apps/faq/Managing%20Your%20Apps_Game%20Center#26180451
*/
