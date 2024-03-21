/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tec02.opencv;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author Administrator
 */
public class Common {
    
    public static Mat BufferedImageToMat(BufferedImage bi) {
        BufferedImage bi2 = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        bi2.getGraphics().drawImage(bi, 0, 0, null);
        Mat mat = new Mat((int)bi2.getHeight(), (int)bi2.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi2.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    public static Mat BufImg2Mat(BufferedImage original, int imgType, int matType) {
        if (original == null) {
            throw new IllegalArgumentException("original == null");
        }
        if (original.getType() != imgType) {

            // Create a buffered image
            BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), imgType);

            // Draw the image onto the new buffer
            Graphics2D g = image.createGraphics();
            try {
                g.setComposite(AlphaComposite.Src);
                g.drawImage(original, 0, 0, null);
            } finally {
                g.dispose();
            }
        }
        DataBufferByte dbi = (DataBufferByte) original.getRaster().getDataBuffer();
        byte[] pixels = dbi.getData();
        Mat mat = Mat.eye(original.getHeight(), original.getWidth(), matType);
        mat.put(0, 0, pixels);
        return mat;
    }

    public static BufferedImage Mat2BufferedImage(Mat mat) throws IOException {
        byte[] byteArray = Mat2Array(mat);
        //Preparing the Buffered Image
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage bufImage = ImageIO.read(in);
        return bufImage;
    }

    public static byte[] Mat2Array(Mat mat) {
        //Encoding the image
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, matOfByte);
        //Storing the encoded Mat in a byte array
        byte[] byteArray = matOfByte.toArray();
        return byteArray;
    }
}
