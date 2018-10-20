package com.turingoal.laundry;

import com.turingoal.laundry.bean.Scan;
import com.turingoal.laundry.bean.ScanTotal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import invengo.javaapi.core.Util;
import invengo.javaapi.protocol.IRP1.RXD_TagData;

/**
 * 扫描数据帮助类
 */

public class DataUtil {
    /**
     * 将扫描结果变成adapter需要的数据
     */
    public static List<ScanTotal> rxdTagData2ScanTotal(final List<RXD_TagData> mRxdTagData) {
        List<ScanTotal> scanTotalList = new ArrayList<>(); // 返回的数据结果
        Map<String, Integer> transitionMap = new HashMap<>();
        for (RXD_TagData rxdTagData : mRxdTagData) { // 遍历扫描的标签结果
            String userData = Util.convertByteArrayToHexString(rxdTagData.getReceivedMessage().getUserData()); // 用户数据，类型就在这里
            String type = getTypeByUserData(userData);
            if (transitionMap.containsKey(type)) { // 如果已经有了这个类型
                transitionMap.put(type, transitionMap.get(type) + 1);
            } else {
                transitionMap.put(type, 1); // 写入新的数据
            }
        }
        for (String key : transitionMap.keySet()) {
            ScanTotal scanTotal = new ScanTotal();
            scanTotal.setType(key);
            scanTotal.setNum(transitionMap.get(key));
            scanTotalList.add(scanTotal);
        }
        return scanTotalList;
    }

    /**
     * 将扫描结果变成服务器需要的数据
     */
    public static List<ScanTotal> rxdTagData2ScanTotalAndScan(final List<RXD_TagData> mRxdTagData) {
        List<ScanTotal> scanTotalList = new ArrayList<>(); // 返回的数据结果
        Map<String, List<Scan>> transitionMap = new HashMap<>();
        for (RXD_TagData rxdTagData : mRxdTagData) { // 遍历扫描的标签结果
            String userData = Util.convertByteArrayToHexString(rxdTagData.getReceivedMessage().getUserData()); // 用户数据，类型就在这里
            String type = getTypeByUserData(userData);
            if (transitionMap.containsKey(type)) { // 如果已经有了这个类型
                transitionMap.get(type).add(getScanByRxdTagData(rxdTagData));
            } else {
                List<Scan> scanList = new ArrayList<>();
                scanList.add(getScanByRxdTagData(rxdTagData));
                transitionMap.put(type, scanList); // 写入新的数据
            }
        }
        for (String key : transitionMap.keySet()) {
            ScanTotal scanTotal = new ScanTotal();
            scanTotal.setType(key);
            scanTotal.setNum(transitionMap.get(key).size());
            scanTotal.setScans(transitionMap.get(key));
            scanTotalList.add(scanTotal);
        }
        return scanTotalList;
    }

    /**
     * 从标签数据得到数据库需要的数据
     */
    private static Scan getScanByRxdTagData(final RXD_TagData rxdTagData) {
        Scan scan = new Scan();
        String epc = Util.convertByteArrayToHexString(rxdTagData.getReceivedMessage().getEPC()); //epc,唯一标识码
        scan.setEpc(epc);
        String userDate = Util.convertByteArrayToHexString(rxdTagData.getReceivedMessage().getUserData()); // 用户数据，类型就在这里
        scan.setUserData(userDate);
        scan.setCreateTime(new Date());
        return scan;
    }

    /**
     * userData --》数据库123
     */
    public static String getTypeByUserData(String userData) {
        // userData --》 白大褂，护士服，护士裤，病号服上衣，病号服裤子，床单，枕巾，被套，手术衣，其他
        int i = new Random().nextInt(20);
        return i % 2 == 0 ? "其他" :"上衣";
    }
}
