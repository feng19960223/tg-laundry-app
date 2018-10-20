package com.turingoal.laundry.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 交接总项
 */

@Data
public class ScanTotal implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // 扫描总数
    private String recordId; // 交接单id
    private String type; // 1：白大褂 2：护士服.....
    private Integer num; // 数量
    private List<Scan> scans; //扫描细项

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ScanTotal scanTotal = (ScanTotal) o;

        return id.equals(scanTotal.id);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
