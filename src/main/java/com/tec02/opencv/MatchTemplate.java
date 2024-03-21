/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tec02.opencv;

import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class MatchTemplate {
    
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public FindObjectResult findObject(Mat src, Mat tpl, double sepcImageMax) {
        Mat result;
        Core.MinMaxLocResult matResult;
        double imageMax;
        int h = src.rows() - tpl.rows() + 1;
        int w = src.cols() - tpl.cols() + 1;
        result = new Mat(new Size(w, h), CvType.CV_32FC1);
        Imgproc.matchTemplate(src, tpl, result, Imgproc.TM_CCOEFF_NORMED);
        matResult = Core.minMaxLoc(result);
        imageMax = matResult.maxVal;
        if (imageMax > sepcImageMax) {
            return new FindObjectResult(matchTemplate(result, tpl, imageMax),
                    src, imageMax);
        }
        return null;
    }

    public Mat rectangle(Mat img, int[] xywh) {
        Imgproc.rectangle(img, new Point(xywh[0], xywh[1]), new Point(xywh[0] + xywh[2], xywh[1] + xywh[3]), new Scalar(0, 0, 255), 2, 8, 0);
        return img;
    }

    public int[] matchTemplate(Mat result, Mat tpl, double imageMax) {
        int xywhv[] = new int[4];
        for (int row = 0; row < result.rows(); row++) {
            for (int col = 0; col < result.cols(); col++) {
                double val = result.get(row, col)[0];
                if (val >= imageMax) {
                    xywhv[0] = col;
                    xywhv[1] = row;
                    xywhv[2] = tpl.cols();
                    xywhv[3] = tpl.rows();
                    return xywhv;
                }
            }
        }
        return xywhv;
    }

    public double similarity(Mat image1, Mat image2) {
        Mat result = new Mat(new Size(image2.rows(), image2.rows()), CvType.CV_32FC1);
        Imgproc.matchTemplate(image1, image2, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult matResult = Core.minMaxLoc(result);
        return matResult.maxVal;
    }

    /**
     */
    public void testMean() {
        Mat src = Imgcodecs.imread("test.jpg");
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Scalar scalar = Core.mean(src);

        System.out.println(scalar);
    }

    public FindObjectResult findObject(BufferedImage src, BufferedImage tpl, double specImageMax) {
        return findObject(Common.BufferedImageToMat(src), Common.BufferedImageToMat(tpl), specImageMax);
    }
}
