package com.blast.springDemo.easyExcel.annotation;

import com.blast.springDemo.easyExcel.config.ExcelExportAdvice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PoiSupport {
    //导出文件名（不包含文件格式）
    String value() default "";

    //导出实体映射类
    Class entity() default Void.class;

    //导出列表属性值，如：list或者data.list
    String key() default "";

    //导出类型
    ExcelExportAdvice.ExportType type() default ExcelExportAdvice.ExportType.Excel;

    //文件后缀
    String suffix() default ".xlsx";

    //强制此请求输出文件
    boolean force() default false;

}
