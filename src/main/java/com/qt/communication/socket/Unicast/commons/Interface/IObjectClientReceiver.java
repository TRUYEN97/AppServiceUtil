/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qt.communication.socket.Unicast.commons.Interface;

import com.qt.communication.socket.Unicast.Client.SocketClient;


/**
 *
 * @author Administrator
 */
public interface IObjectClientReceiver {
    
    void receiver(SocketClient client, String data);
}
