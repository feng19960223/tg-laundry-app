package com.turingoal.laundry.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 标签
 */
@Data
public class Scan implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String recordId; // 交接单id
    private String scanTotalId; // 交接总项id
    private String epc; // epc
    private String userData; // 用户数据
    private Date createTime; // 扫描时间

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Scan scan = (Scan) o;

        return id.equals(scan.id);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
