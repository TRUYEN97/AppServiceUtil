/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tec02.Robot;

import com.tec02.Time.WaitTime.AbsTime;
import com.tec02.Time.WaitTime.Class.TimeMs;
import com.tec02.Time.WaitTime.Class.TimeS;
import com.tec02.opencv.FindObjectResult;
import com.tec02.opencv.MatchTemplate;
import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Administrator
 */
public class AutoClickOnScreen {

    private final List<String> steps;
    private final MyRobot robot;
    private final MatchTemplate matchTemplate;
    private String rootPath;
    private String screenName;

    public AutoClickOnScreen() throws AWTException {
        this.steps = new ArrayList<>();
        this.robot = new MyRobot();
        this.matchTemplate = new MatchTemplate();
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public static void main(String[] args) throws AWTException {
        String windowName = "Anlink";
        AutoClickOnScreen acos = new AutoClickOnScreen();
        acos.setRootPath("C:\\Users\\Administrator\\Desktop\\youxiwaigua");
        acos.screenName = windowName;
        acos.addStep("show");
        acos.addStep("click2");
        acos.addStep("0.png-1-1000");
//        acos.addStep("1.png");
//        acos.addStep("2.png-1-100");
//        acos.addStep("3.png-1-10000");
//        acos.addStep("1.png-1-10000");
        System.out.println(acos.run());
    }

    public void addStep(String command) {
        if (command == null) {
            return;
        }
        this.steps.add(command.trim().toLowerCase());
    }

    public boolean run() {
        for (String step : steps) {
            if (step == null) {
                continue;
            }
            if (step.equals("show")) {
                this.robot.moveMouseOnWindow(screenName);
            } else if (step.startsWith("click")) {
                if (step.endsWith("1")) {
                    this.robot.click();
                } else if (step.endsWith("2")) {
                    this.robot.click(InputEvent.BUTTON2_DOWN_MASK);
                } else if (step.endsWith("3")) {
                    this.robot.click(InputEvent.BUTTON3_DOWN_MASK);
                }
            } else {
                String[] elems = step.split("-");
                int times = 1;
                String imgPath = null;
                TimeMs timer = null;
                switch (elems.length) {
                    case 3:
                        timer = new TimeMs(Integer.parseInt(elems[2].trim()));
                    case 2:
                        times = Integer.parseInt(elems[1].trim());
                    case 1:
                        imgPath = elems[0].trim();
                        break;

                }
                if (!action(imgPath, times, timer)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean action(String imgPathTarget, int clickTime, TimeMs timeMs) {
        try {
            if (screenName == null || screenName.isBlank()
                    || imgPathTarget == null || imgPathTarget.isBlank()) {
                return false;
            }
            File fileTarget = new File(rootPath, imgPathTarget);
            if (!fileTarget.exists()) {
                return false;
            }
            FindObjectResult objectResult = findImageOnWindowForm(screenName,
                    fileTarget, timeMs);
            if (objectResult == null || objectResult.getRootLocation() == null) {
                return false;
            }
            clickTime = clickTime < 1 ? 1 : clickTime;
            for (int i = 0; i < clickTime; i++) {
                robot.robotMoveAndClickMouse(objectResult.getRootLocation(), objectResult.xywh);
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public FindObjectResult findImageOnWindowForm(String windowName, String imgPathTarget, AbsTime timer) throws IOException {
        return findImageOnWindowForm(windowName, imgPathTarget, timer, 0.85);
    }

    public FindObjectResult findImageOnWindowForm(String windowName, String imgPathTarget, AbsTime timer, double specImageMax) throws IOException {
        return findImageOnWindowForm(windowName, new File(imgPathTarget), timer, specImageMax);
    }

    public FindObjectResult findImageOnWindowForm(String windowName, File imgFileTarget, AbsTime timer) throws IOException {
        return findImageOnWindowForm(windowName, imgFileTarget, timer, 0.85);
    }

    public FindObjectResult findImageOnWindowForm(String windowName, File imgFileTarget, AbsTime timer, double specImageMax) throws IOException {
        return findImageOnWindowForm(windowName, ImageIO.read(imgFileTarget), timer, specImageMax);
    }

    public FindObjectResult findImageOnWindowForm(String windowName, BufferedImage tpl, AbsTime timer) {
        return findImageOnWindowForm(windowName, tpl, timer, 0.85);
    }

    public FindObjectResult findImageOnWindowForm(String windowName, BufferedImage tpl, AbsTime timer, double specImageMax) {
        if (timer == null) {
            timer = new TimeS(Integer.MAX_VALUE);
        }
        BufferedImage src;
        FindObjectResult result;
        int[] xywh;
        while (timer.onTime()) {
            xywh = this.robot.getWindowLocation(windowName, true);
            if (xywh == null) {
                return null;
            }
            src = this.robot.createScreenCaptureBufferedImage(xywh);
            result = matchTemplate.findObject(src, tpl, specImageMax);
            if (result != null) {
                result.setWindowsLocation(xywh);
                return result;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
            }
        }
        return null;
    }

}
