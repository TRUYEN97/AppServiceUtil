/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qt.Time.WaitTime.Class;

import com.qt.Time.WaitTime.AbsTime;


/**
 *
 * @author Administrator
 */
public class TimeMs extends AbsTime {

    public TimeMs() {
    }
    

    public TimeMs(double time) {
        super(time);
    }

    @Override
    public double getTimeCurrent() {
        return System.currentTimeMillis();
    }
}
