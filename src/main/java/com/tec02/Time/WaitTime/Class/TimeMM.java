/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tec02.Time.WaitTime.Class;

import com.tec02.Time.WaitTime.AbsTime;


/**
 *
 * @author Administrator
 */
public class TimeMM extends AbsTime {

    public TimeMM() {
    }
    

    public TimeMM(double time) {
        super(time);
    }

    @Override
    public double getTimeCurrent() {
        return (double)(System.currentTimeMillis() /(double) 6e4);
    }
}
