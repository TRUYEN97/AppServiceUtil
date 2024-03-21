/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tec02.Robot;

import com.sun.jna.platform.win32.WinDef;
import com.tec02.user32.User32;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Administrator
 */
public class MyRobot {

    public static String PNG = "png";
    public static String JPG = "jpg";
    private final Robot robot;
    private final User32 user32;

    public MyRobot() throws AWTException {
        this.robot = new Robot();
        this.user32 = User32.INSTANCE;
    }

    public int[] getWindowLocation(String windowsName) {
        return getWindowLocation(windowsName, true);
    }

    public int[] getWindowLocation(String windowsName, boolean showWindows) {
        WinDef.HWND hwnd = user32.FindWindow(0, windowsName);
        if (hwnd == null) {
            return null;
        }
        if (showWindows) {
            showWindows(hwnd);
        }
        int[] xywh = new int[4];
        user32.GetWindowRect(hwnd, xywh);
        double scale = Toolkit.getDefaultToolkit().getScreenResolution() / 96.0;
        xywh[0] = (int) (xywh[0] * scale);
        xywh[1] = (int) (xywh[1] * scale);
        xywh[2] = (int) (xywh[2] * scale);
        xywh[3] = (int) (xywh[3] * scale);
        return xywh;
    }

    public int[] scale(int[] xywh, int size) {
        if (xywh == null) {
            return null;
        }
        if (size < 1) {
            return xywh;
        }
        xywh[0] = xywh[0] * size / 100;
        xywh[1] = xywh[1] * size / 100;
        xywh[2] = xywh[2] * size / 100;
        xywh[3] = xywh[3] * size / 100;
        return xywh;
    }

    public boolean showWindows(String windowName) {
        WinDef.HWND hwnd = user32.FindWindow(0, windowName);
        return showWindows(hwnd);
    }

    public boolean showWindows(WinDef.HWND hwnd) {
        if (hwnd == null) {
            return false;
        }
        return user32.ShowWindow(hwnd, 1)
                && user32.SetForegroundWindow(hwnd);
    }
    
    public boolean moveMouseOnWindow(String title) {
        if (title == null) {
            return false;
        }
        WinDef.HWND thisWindows = user32.FindWindow(0, title);
        showWindows(thisWindows);
        return moveMouseOnWindow(thisWindows);
    }

    public boolean showAndMoveMouse(String title, int x, int y) {
        if (title == null || x < 0 || y < 0) {
            return false;
        }
        WinDef.HWND thisWindows = user32.FindWindow(0, title);
        showWindows(thisWindows);
        moveMouseOnWindow(thisWindows, x, y);
        return true;
    }

    public boolean moveMouseOnWindow(WinDef.HWND hwnd, int x, int y) {
        int[] location = new int[4];
        if (!user32.GetWindowRect(hwnd, location)) {
            return false;
        }
        this.mouseMove(location[0] + x, location[1] + y);
        return true;
    }

    public boolean moveMouseOnWindow(WinDef.HWND hwnd) {
        int[] location = new int[4];
        if (!user32.GetWindowRect(hwnd, location)) {
            return false;
        }
        this.mouseMove(location[0] + location[2] / 2, location[1] + location[3] / 2);
        return true;
    }

    public void clearAtTextMouse() {
        this.deleteAtMouse();
    }

    public void enter() {
        this.sendKey(KeyEvent.VK_ENTER);
    }

    public boolean closeWindows(WinDef.HWND hwnd) {
        return user32.CloseWindow(hwnd);
    }

    public boolean saveScreenCapture(int[] xywh, String imageName) {
        if (!imageName.contains(".")) {
            return false;
        }
        String formatName = imageName.substring(imageName.lastIndexOf(".") + 1);
        return saveScreenCapture(xywh, formatName, imageName);
    }

    public BufferedImage createScreenCaptureBufferedImage(String windowName) {
        return createScreenCaptureBufferedImage(windowName, true);
    }

    public BufferedImage createScreenCaptureBufferedImage(String windowName, boolean showWindows) {
        int[] xywh = this.getWindowLocation(windowName, showWindows);
        if (xywh == null) {
            return null;
        }
        return createScreenCaptureBufferedImage(xywh);
    }

    public boolean saveScreenCapture(int[] xywh, String imageName, String imageType) {
        try {
            BufferedImage screenshot = createScreenCaptureBufferedImage(xywh);
            ImageIO.write(screenshot, imageType, new File(imageName));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public BufferedImage createScreenCaptureBufferedImage(int[] xywh) {
        try {
            Rectangle windowRect = new Rectangle(xywh[0], xywh[1], xywh[2] - xywh[0], xywh[3] - xywh[1]);
            BufferedImage screenshot = robot.createScreenCapture(windowRect);
            return screenshot;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean robotMoveAndClickMouse(int[] xywh, int[] xywh1) {
        try {
            mouseMove(xywh[0] + xywh1[0] + xywh1[2] / 2, xywh[1] + xywh1[1] + xywh1[3] / 2);
            click();
            mouseMove(xywh[0] + xywh1[0] + xywh1[2] + 2, xywh[1] + xywh1[1] + xywh1[3] + 2);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean sendMessager(String mess) {
        try {
            for (char key : mess.toUpperCase().toCharArray()) {
                sendKey(key);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void mouseMove(int x, int y) {
        this.robot.mouseMove(x, y);
    }

    public void deleteAtMouse() {
        click();
        click();
        sendKey(KeyEvent.VK_DELETE);
    }

    public void sendKey(int kiTu) {
        this.robot.keyPress(kiTu);
        this.robot.delay(10);
        this.robot.keyRelease(kiTu);
        this.robot.delay(10);
    }

    /* The following standard button masks are also accepted:
     * <ul>
     * <li>{@code InputEvent.BUTTON1_MASK}
     * <li>{@code InputEvent.BUTTON2_MASK}
     * <li>{@code InputEvent.BUTTON3_MASK}
     * </ul>
     * However, it is recommended to use {@code InputEvent.BUTTON1_DOWN_MASK},
     * {@code InputEvent.BUTTON2_DOWN_MASK},  {@code InputEvent.BUTTON3_DOWN_MASK} instead.
     * Either extended {@code _DOWN_MASK} or old {@code _MASK} values
     * should be used, but both those models should not be mixed.
     * @throws IllegalArgumentException if the {@code buttons} mask contains the mask for extra mouse button
     *         and support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() disabled} by Java
     * @throws IllegalArgumentException if the {@code buttons} mask contains the mask for extra mouse button
     *         that does not exist on the mouse and support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() enabled} by Java
     * @see #mouseRelease(int)
     * @see InputEvent#getMaskForButton(int)
     * @see Toolkit#areExtraMouseButtonsEnabled()
     * @see java.awt.MouseInfo#getNumberOfButtons()
     * @see java.awt.event.MouseEvent
     */
    public void click(int buttons) {
        this.robot.mousePress(buttons);
        this.robot.delay(200);
        this.robot.mouseRelease(buttons);
        this.robot.delay(200);
    }

    public void click() {
        click(InputEvent.BUTTON1_DOWN_MASK);
    }
}
