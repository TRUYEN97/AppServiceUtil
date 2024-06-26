/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.communication.socket.Unicast.commons;

import com.qt.Time.TimeBase;
import com.qt.mylogger.MyLogger;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class SocketLogger {

    private final MyLogger loger;
    private final TimeBase timeBase;
    private final String path;

    public SocketLogger(String path) {
        this.loger = new MyLogger();
        this.loger.setSaveMemory(true);
        this.timeBase = new TimeBase();
        this.path = path;
    }

    public synchronized boolean addlog(String key, String str, Object... params) {
        String logPath = String.format("%s/%s.txt", this.path, this.timeBase.getDate());
        this.loger.setFile(new File(logPath));
        try {
            this.loger.addLog(key, str, params);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
