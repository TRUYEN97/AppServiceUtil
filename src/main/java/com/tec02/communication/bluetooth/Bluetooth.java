/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tec02.communication.bluetooth;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

/**
 *
 * @author Administrator
 */
public class Bluetooth {

    public static void main(String[] args) {
        try {
            // Tìm kiếm các thiết bị Bluetooth
//            DiscoveryAgent agent = LocalDevice.getLocalDevice().getDiscoveryAgent();
//            UUID[] uuids = new UUID[]{new UUID(0x1101)}; // UUID cho dịch vụ Serial Port Profile (SPP)
//            int inquiryMode = DiscoveryAgent.GIAC; // General Inquiry Access Code
//            agent.startInquiry(inquiryMode, new DiscoveryListener() {
//                @Override
//                public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
//                    try {
//                        System.out.println("Thiết bị Bluetooth: " + btDevice.getBluetoothAddress() + " - " + btDevice.getFriendlyName(false));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void inquiryCompleted(int discType) {
//                    System.out.println("Hoàn thành tìm kiếm thiết bị Bluetooth!");
//                    synchronized (Bluetooth.class) {
//                        Bluetooth.class.notify();
//                    }
//                }
//
//                @Override
//                public void serviceSearchCompleted(int transID, int respCode) {
//                }
//
//                @Override
//                public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
//                }
//            });
//            synchronized (Bluetooth.class) {
//                Bluetooth.class.wait();
//            }
//            BluetoothManager manager = BluetoothManager.getBluetoothManager();
//
//            // Quét các thiết bị BLE trong 10 giây
//            manager.startDiscovery();
//
//            // Đợi 10 giây để quét thiết bị
//            Thread.sleep(10000);
//
//            // Lấy danh sách các thiết bị đã quét được
//            java.util.List<BluetoothDevice> devices = manager.getDevices();
//
//            // In thông tin về các thiết bị đã quét được
//            for (BluetoothDevice device : devices) {
//                System.out.println("Đã tìm thấy thiết bị BLE: " + device.getAddress() + " - " + device.getName());
//            }
//
//            // Dừng quét
//            manager.stopDiscovery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
