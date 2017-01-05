package com.jzfq.fms.common.common;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by juzi on 2016/11/25.
 */
public class StrapTable<T> implements Serializable{
    private static final long serialVersionUID = 1L;

    private int total;


    private List<T> rows = Lists.newArrayList();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
