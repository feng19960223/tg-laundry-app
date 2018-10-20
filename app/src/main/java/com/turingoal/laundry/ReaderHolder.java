package com.turingoal.laundry;

import com.invengo.lib.system.device.type.DeviceType;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.protocol.IRP1.Reader;

/**
 * 设备
 */
public class ReaderHolder {
    private static ReaderHolder instance = null;
    private Reader currentReader;
    private DeviceType deviceType = DeviceType.XC2600;

    public static ReaderHolder getInstance() {
        if (instance == null) {
            instance = new ReaderHolder();
        }
        ReaderHolder localReaderHolder = instance;
        return localReaderHolder;
    }

    public Reader createBluetoothReader(String readerName, String connStr) {
        this.currentReader = new Reader(readerName, "Bluetooth", connStr);
        return this.currentReader;
    }

    /**
     * 设备是否链接
     */
    public boolean isConnected() {
        if (currentReader != null) {
            return currentReader.isConnected();
        }
        return false;
    }

    /**
     * 连接设备
     */
    public boolean connect() {
        if (currentReader != null) {
            return currentReader.connect();
        }
        return isConnected();
    }

    /**
     * 取消连接
     */
    public void disConnect() {
        if (this.currentReader != null) {
            this.currentReader.disConnect();
        }
    }

    /**
     * 销毁设备
     */
    public void disposeReader() {
        if (this.currentReader != null) {
            disConnect();
            this.currentReader = null;
        }
    }

    /**
     * 得到当前设备
     */
    public Reader getCurrentReader() {
        return this.currentReader;
    }

    public IMessage sendMessage(IMessage paramIMessage) {
        if ((isConnected()) && (this.currentReader != null) && (this.currentReader.send(paramIMessage))) {
            return paramIMessage;
        }
        return null;
    }

    public void sendNodificationMessage(IMessage paramIMessage) {
        if (isConnected() && (this.currentReader != null)) {
            this.currentReader.send(paramIMessage);
        }
    }
}
