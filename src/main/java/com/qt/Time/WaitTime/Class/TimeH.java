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
public class TimeH extends AbsTime {

    public TimeH() {
    }

    public TimeH(double time) {
        super(time);
    }

    @Override
    public double getTimeCurrent() {
        return (double) (System.currentTimeMillis() / (double) 36e6);
    }
}
