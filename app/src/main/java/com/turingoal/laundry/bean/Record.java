package com.turingoal.laundry.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 交接单
 */
@Data
public class Record implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // 交接单
    private String codeNum; // 交接单号
    private String sendUnit; // 发送单位--医院
    private String sendUnitCode; // 发送单位--医院
    private String receiveUnit; // 接收单位
    private String userId; // 用户id
    private String userName; // 用户真实姓名
    private String userPhone; // 用户电话
    private String total; // 总数
    private String gpsLongitute; // 经度
    private String gpsLatitude; // 纬度
    private String deviceId; // 扫描枪设备ID
    private String devicePower; // 设备功率
    private String deviceScanRadius; // 扫描半径
    private Date starTime; // 开始扫描时间
    private Date endTime; // 结束扫描时间
    private Date createTime; // 创建时间
    private String status; // 状态：1：待确认  2： 已确认  :3：已取消
    private String type; // 1：收污 2：发净
    private List<ScanTotal> scanTotals;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Record record = (Record) o;

        return id.equals(record.id);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
