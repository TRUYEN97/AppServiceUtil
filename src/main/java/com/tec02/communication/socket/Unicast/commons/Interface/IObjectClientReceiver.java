/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tec02.communication.socket.Unicast.commons.Interface;

import com.tec02.communication.socket.Unicast.Client.Client;


/**
 *
 * @author Administrator
 */
public interface IObjectClientReceiver {
    
    void receiver(Client client, String data);
}
