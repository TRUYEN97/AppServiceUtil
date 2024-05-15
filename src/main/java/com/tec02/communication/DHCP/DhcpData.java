/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tec02.communication.DHCP;

import com.tec02.Time.TimeBase;
import com.tec02.mylogger.MyLogger;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class DhcpData {

    private static volatile DhcpData instance;

    static {
        instance = new DhcpData();
    }
    private final Map<String, String> macIps;
    private final Map<String, String> ipMacs;
    private String netIP;
    private int macLength;
    private File logdir;
    private final MyLogger loger;
    private final TimeBase timeBase;

    private DhcpData() {
        this.macLength = 17;
        this.macIps = new HashMap<>();
        this.ipMacs = new HashMap<>();
        this.loger = new MyLogger();
        this.loger.setSaveMemory(true);
        this.timeBase = new TimeBase();
    }

    public String getNetIP() {
        return netIP;
    }
    
    public void setLogdir(File logdir) {
        this.logdir = logdir;
    }

    public boolean setMacLength(int macLength) {
        if (macLength < 1) {
            return false;
        }
        this.macLength = macLength;
        return true;
    }

    public static DhcpData getInstance() {
        return instance;
    }

    public synchronized boolean put(String mac, int id) {
        try {
            String logPath = String.format("%s/DHCP_DATA/%s.txt", logdir.getPath(), this.timeBase.getDate());
            this.loger.setFile(new File(logPath));
            if (id < 0 || id > 255 || mac == null) {
                return false;
            }
            mac = MacUtil.macFormat(mac, macLength);
            if (mac == null) {
                return false;
            }
            String ip = createIp(id);
            if (ip == null) {
                return false;
            }
            resetMACInfo(mac, ip);
            addMacIp(mac, ip);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            this.loger.addLog("ERROR", e.getLocalizedMessage());
            return false;
        }
    }

    private boolean addMacIp(String mac, String ip) {
        if (mac == null || ip == null) {
            return false;
        }
        this.loger.addLog("ADD", "%s - %s",mac, ip);
        String macOld = this.ipMacs.put(ip, mac);
        if (macOld != null) {
            this.macIps.remove(macOld);
        }
        this.macIps.put(mac, ip);
        return true;
    }

    private void resetMACInfo(String mac, String ip) {
        if (this.ipMacs.containsKey(ip)) {
            this.loger.addLog("REMOVE", ip);
            this.ipMacs.remove(ip);
        } else if (this.macIps.containsKey(mac)) {
            this.loger.addLog("REMOVE", mac);
            this.ipMacs.remove(mac);
        }
    }

    public synchronized String getIP(String mac) throws Exception {
        if (mac == null || (mac = MacUtil.macFormat(mac, macLength)) == null) {
            throw new Exception(String.format("invalid MAC format. MAC is %s", mac));
        }
        return this.macIps.get(mac);
    }
    
    public synchronized String getMAC(String ip){
        if (ip == null) {
            return null;
        }
        return this.ipMacs.get(ip);
    }

    public boolean setNetIP(String netIp) {
        try {
            this.netIP = netIp.substring(0, netIp.lastIndexOf("."));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getMACLength() {
        return macLength;
    }

    private String createIp(Integer id) {
        if (id == null || id < 0 || id > 255) {
            return null;
        }
        return String.format("%s.%s", this.netIP, id);
    }
}
