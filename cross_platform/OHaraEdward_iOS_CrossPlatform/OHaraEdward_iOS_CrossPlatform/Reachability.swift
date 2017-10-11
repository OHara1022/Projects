//
//  Reachability.swift
//  OHaraEdward_iOS_CrossPlatform
//
//  Created by Scott O'Hara on 4/27/17.
//  Copyright © 2017 Scott O'Hara. All rights reserved.
//

import Foundation
import SystemConfiguration

public class Reachability{
    
    //func to check network status
    class func isConnectedToNetwork() -> Bool {
        
        var zeroAddress = sockaddr_in(sin_len: 0, sin_family: 0, sin_port: 0, sin_addr: in_addr(s_addr: 0), sin_zero: (0, 0, 0, 0, 0, 0, 0, 0))
        zeroAddress.sin_len = UInt8(MemoryLayout.size(ofValue: zeroAddress))
        zeroAddress.sin_family = sa_family_t(AF_INET)
        
        //guard
        guard let defaultRouteReachability = withUnsafePointer(to: &zeroAddress, {
            
            $0.withMemoryRebound(to: sockaddr.self, capacity: 1) {
                
                //create reachability address to network
                SCNetworkReachabilityCreateWithAddress(nil, $0)
            }
            
        }) else {
            
            return false
        }
        
        //check flag variables for reachability
        var flags: SCNetworkReachabilityFlags = SCNetworkReachabilityFlags(rawValue: 0)
        if SCNetworkReachabilityGetFlags(defaultRouteReachability, &flags) == false {
            return false
        }
        
        let isReachable = flags == .reachable
        let needsConnection = flags == .connectionRequired
        
        return isReachable && !needsConnection
    }
}

//referenced from:
/* http://stackoverflow.com/questions/38726100/best-approach-for-checking-internet-connection-in-ios */
