package com.blast.springDemo.core.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    public PageResult(){}

    public PageResult(List<T> list, Long tt){
        records = list;
        total = tt;
    }
    private List<T> records;

    private Long total;

    public static <E> PageResult build(List<E> list,Long tt){
        return new PageResult(list,tt);
    }

}
