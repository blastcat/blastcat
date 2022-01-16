package com.blast.springDemo.core.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blast.springDemo.core.exception.ParamsException;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
public class ApiRequets extends JSONObject{

    public ApiRequets(){
        super();
    }

    public ApiRequets(Map<String, Object> map){
        super(map);
    }

    public ApiRequets(Object object){
        super((JSONObject)JSONObject.toJSON(object));
    }

    public static ApiRequets build(Map<String, Object> map){
        return new ApiRequets(map);
    }

    public static ApiRequets build(Object object){
        return new ApiRequets(object);
    }

    public Object iGet(String key,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        return this.get(key);
    }

    public JSONObject iGetJSON(String key,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getJSONObject(key);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public JSONArray iGetJSONArray(String key, String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getJSONArray(key);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public String iGetString(String key,String message){
        if (!this.containsKey(key) || StringUtils.isEmpty(this.getString(key)))throw new ParamsException(message);
        return this.getString(key);
    }

    public Integer iGetInteger(String key,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getInteger(key);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public int iGetIntValue(String key,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getIntValue(key);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public Long iGetLong(String key,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getLong(key);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public long iGetLongValue(String key,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getLongValue(key);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public Double iGetDouble(String key,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getDouble(key);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public double iGetDoubleValue(String key,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getDoubleValue(key);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public Date iGetDate(String key, String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getDate(key);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public PageRequest getPage(){
        return this.getObject("page", PageRequest.class);
    }

    public PageRequest iGetPage(String message){
        return this.iGetJava("page", PageRequest.class,message);
    }

    public <T> T iGetJava(String key,Class<T> clazz,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getObject(key,clazz);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public <T> T iGetObject(String key,Class<T> clazz,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getObject(key,clazz);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public <E> List<E> iGetList(String key, Class<E> clazz, String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getJSONArray(key).toJavaList(clazz);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public List<Object> iGetList(String key, String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getJSONArray(key).toJavaList(Object.class);
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public Object [] iGetArrays(String key,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            return this.getJSONArray(key).toArray();
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public <T> T [] iGetArrays(String key,Class<T> a,String message){
        if (!this.containsKey(key) || Objects.isNull(this.get(key)))throw new ParamsException(message);
        try {
            T [] array = this.getArrays(key,a);
            if (Objects.isNull(array))throw new ParamsException(message);
            return array;
        }catch (Exception e){
            throw new ParamsException(message);
        }
    }

    public <T> T [] getArrays(String key,Class<T> a){
        if (!this.containsKey(key)) return (T[]) Array.newInstance(a,0);
        JSONArray ja = this.getJSONArray(key);
        if (Objects.isNull(ja) || ja.isEmpty())return (T[]) Array.newInstance(a,0);
        T [] array = (T[]) Array.newInstance(a,ja.size());
        for (int i=0;i<ja.size();i++){
            Array.set(array,i,ja.getObject(i,a));
        }
        return array;
    }

    public <T> List<T> getList(String key,Class<T> a){
        return this.getJSONArray(key).toJavaList(a);
    }

}
