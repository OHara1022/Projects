//
//  Score.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/24/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import Foundation
import GameKit

class Score{
    
    //MARK: Stored Properties
    var highScore = 0
    var previousHighScore = 0
    
    //Save high score w/ NSUserDefaults
    var scoreDefaults: UserDefaults = UserDefaults.standard
    
    //save scores to game center
    func saveHighScore(_ identifier: String, score: Int){
        
        //check if user if authenticated
        if GKLocalPlayer.localPlayer().isAuthenticated{
            
            //score to report to Game center
            let scoreReport = GKScore(leaderboardIdentifier: identifier)
            
            //The score earned by the player
            scoreReport.value = Int64(score)
            
            //array to hold reported scores
            let scoreArray: [GKScore] = [scoreReport]
            
            //Reports a list of scores to Game Center
            GKScore.report(scoreArray, withCompletionHandler: {
                error -> Void in
                
                //error handeling
                if error != nil{
                    
                    print("\(String(describing: error))")
                    
                }else{
                    
                    print("posted score: \(score)")
                }
            })//end of completion
        }
        
    }//end of save high score
}
//MARK: References:
/* -https://www.youtube.com/watch?v=LS_kGRp7A1E&index=3&list=PLMrKlNcXRwua4X2Unrq2Md1L_TgtnQtle
- http://www.appcoda.com/ios-game-kit-framework/
- https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/GameKit_Guide/GameCenterOverview/GameCenterOverview.html
- https://developer.apple.com/library/ios/documentation/LanguagesUtilities/Conceptual/iTunesConnectGameCenter_Guide/Leaderboards/Leaderboards.html#//apple_ref/doc/uid/TP40013726-CH2-SW1
- https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/GameKit_Guide/Introduction/Introduction.html
- https://itunespartner.apple.com/en/apps/faq/Managing%20Your%20Apps_Game%20Center#26180451
*/
