package com.turingoal.common.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/1/6.
 */

public class BluetoothUtils {
    /**
     * 打开蓝牙
     */
    public static void openBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //获取本地蓝牙实例
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) { // 关闭状态
                bluetoothAdapter.enable();  //打开蓝牙, 再次执行则关闭
            }
        }
    }

    /**
     * 关闭蓝牙
     */
    public static void closeBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //获取本地蓝牙实例
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled()) { // 打开状态
                bluetoothAdapter.enable();  //关闭蓝牙, 再次执行则打开
            }
        }
    }

    /**
     * 是否打开了蓝牙
     */
    public static boolean isOpen() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //获取本地蓝牙实例
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.isEnabled(); // ture 打开
        }
        return false;
    }

    /**
     * 是否打开了蓝牙,如果否会打开蓝牙
     */
    public static boolean isOpenBluetooth() {
        openBluetooth();
        return isOpen();
    }

    /**
     * 得到蓝牙列表
     */
    public static List<BluetoothDevice> getBluetoothDeviceList() {
        if (isOpenBluetooth()) {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //获取本地蓝牙实例
            if (bluetoothAdapter != null) {
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();  //打开蓝牙, 再次执行则关闭
                }
                List<BluetoothDevice> list = new ArrayList<>();
                Iterator<BluetoothDevice> deviceIterator = bluetoothAdapter.getBondedDevices().iterator(); // 得到配对的设备
                while (deviceIterator.hasNext()) {
                    BluetoothDevice bluetoothDevice = deviceIterator.next();
                    if ((!TextUtils.isEmpty(bluetoothDevice.getName())) || // 名字不空
                            (!TextUtils.isEmpty(bluetoothDevice.getAddress()))) { // 地址不空
                        list.add(bluetoothDevice);
                    }
                }
                return list;
            }
        }
        return new ArrayList<>();
    }
}
