/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tec02.opencv;

import org.opencv.core.Mat;

/**
 *
 * @author Administrator
 */
public class FindObjectResult {

    public final int[] xywh;
    private int[] xywh1;
    public final Mat mat;
    public final double maxVal;

    public FindObjectResult(int[] xywh, Mat mat, double maxVal) {
        this.xywh = xywh;
        this.mat = mat;
        this.maxVal = maxVal;
    }

    public void setWindowsLocation(int[] xywh1) {
        this.xywh1 = xywh1;
    }

    public int[] getRootLocation() {
        return xywh1;
    }

}
