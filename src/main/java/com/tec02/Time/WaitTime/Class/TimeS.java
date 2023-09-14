package com.tec02.Time.WaitTime.Class;

import com.tec02.Time.WaitTime.AbsTime;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class TimeS extends AbsTime {

    public TimeS() {
    }

    public TimeS(long spec) {
        super(spec);
    }
    
    @Override
    public double getTimeCurrent() {
        return System.currentTimeMillis() / 1000.0;
    }
}
