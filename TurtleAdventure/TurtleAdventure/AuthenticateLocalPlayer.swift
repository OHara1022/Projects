//
//  AuthenticateLocalPlayer.swift
//  TurtleAdventure
//
//  Created by Scott O'Hara on 3/25/16.
//  Copyright © 2016 Scott O'Hara. All rights reserved.
//

import Foundation
import SpriteKit
import GameKit

//MARK: Game Center
extension MainMenu{
    
    //authenticate player in game center
    func authenticateLocalPlayer(){
        
        //shared instance of local player
        let localPlayer = GKLocalPlayer.localPlayer()
        
        localPlayer.authenticateHandler = { (viewController, error) -> Void in
            
            //if user is not authenticated present vc
            if viewController != nil{
                
                //present VC
                let VC: UIViewController = self.view!.window!.rootViewController!
                VC.present(viewController!, animated: true, completion: nil)
                
            }else{
                
                //check if player is authenticated
                if localPlayer.isAuthenticated == false{
                    
                    //if not authenticated presentGamecenter VC
                    self.presentGameCenterVC()
                }
                //load achievement percentages
                self.achievements.achievementPercentage()
            }
            
        }
    }//end of authentiateLocalPlayer
    
    //show game center
    func presentGameCenterVC(){
        
        //instance of gamecenter vc
        let gameCenterVC = GKGameCenterViewController()
        
        //set delegate to self
        gameCenterVC.gameCenterDelegate = self
        
        //present gamecenter vc
        let VC: UIViewController = self.view!.window!.rootViewController!
        VC.present(gameCenterVC, animated: true, completion: nil)
        
    }//end of presentGameCenterVC
    
    //dismiss game center VC
    func gameCenterViewControllerDidFinish(_ gameCenterViewController: GKGameCenterViewController) {
        
        gameCenterViewController.dismiss(animated: true, completion: nil)
        
        //remove last loaded achievements from dictionary
        achievements.gameAchievements.removeAll()
        
        //load new percetages to dictionary
        achievements.achievementPercentage()
        
    }//end of did finish
}
//MARK: References:
/*-https://www.youtube.com/watch?v=LS_kGRp7A1E&index=3&list=PLMrKlNcXRwua4X2Unrq2Md1L_TgtnQtle
- http://www.appcoda.com/ios-game-kit-framework/
- https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/GameKit_Guide/GameCenterOverview/GameCenterOverview.html
- https://developer.apple.com/library/ios/documentation/LanguagesUtilities/Conceptual/iTunesConnectGameCenter_Guide/Leaderboards/Leaderboards.html#//apple_ref/doc/uid/TP40013726-CH2-SW1
- https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/GameKit_Guide/Introduction/Introduction.html
- https://itunespartner.apple.com/en/apps/faq/Managing%20Your%20Apps_Game%20Center#26180451
*/
