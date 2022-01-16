package com.blast.springDemo.core.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.LongCodec;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Author: L.my
 * Email: wonailmy@163.com
 * Date: 16/8/31
 * Time: 下午5:50
 * Describe: 封装Json返回信息
 */
@Data
public class ApiResult<T> {

    @JSONField(serializeUsing = LongCodec.class)
    private Long time;
    private boolean success;
    private Integer status;
    private String message;
    private T data;

    public ApiResult(){
        this.time = System.currentTimeMillis();
    }

    public ApiResult(Boolean status){
        this.time = System.currentTimeMillis();
        this.status = status ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.success = status;
    }

    public ApiResult(Boolean status, String message){
        this.time = System.currentTimeMillis();
        this.status = status ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = message;
        this.success = status;
    }

    public ApiResult(Boolean status, T data){
        this.time = System.currentTimeMillis();
        this.status = status ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.data = data;
        this.success = status;
    }

    public ApiResult(Boolean status, String message, T data){
        this.time = System.currentTimeMillis();
        this.status = status ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = message;
        this.data = data;
        this.success = status;
    }

    public static ApiResult success(){
        return new ApiResult(Boolean.TRUE);
    }

    public static ApiResult success(String message){
        return new ApiResult(Boolean.TRUE,message);
    }

    public static ApiResult success(String message, Object data){
        return new ApiResult(Boolean.TRUE,message,data);
    }

    public static ApiResult success(Object data){
        return new ApiResult(Boolean.TRUE,data);
    }

    public static ApiResult fail(){
        return new ApiResult(Boolean.FALSE);
    }

    public static ApiResult fail(String message){
        return new ApiResult(Boolean.FALSE,message);
    }

    public static ApiResult fail(Object data){
        return new ApiResult(Boolean.FALSE,data);
    }

    public static ApiResult fail(Integer status, String message){
        ApiResult apiResult = new ApiResult(Boolean.FALSE,message);
        apiResult.setStatus(status);
        return apiResult;
    }
}
