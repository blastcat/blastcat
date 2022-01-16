package com.blast.springDemo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable {
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

}
