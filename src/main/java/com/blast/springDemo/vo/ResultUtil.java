package com.blast.springDemo.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Array;
import java.util.*;

public class ResultUtil {

    public ResultUtil(ApiResult apiResult){
        this.apiResult = apiResult;
    }

    private ApiResult apiResult;

    /**
     * 将data转换为array数组
     * @return
     */
    public Object[] getArray(){
        return getArray(Object.class);
    }

    /**
     * 强行进行一次序列化，并返回集合
     * @param tClass
     * @param <S>
     * @return
     */
    public <S> List<S> serializeList(Class<S> tClass){
        List<Object> array = getList();
        if (array.isEmpty())return new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        for (Object obj : array){
            jsonArray.add(JSONObject.parseObject(JSONObject.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat)));
        }
        return jsonArray.toJavaList(tClass);
    }

    public JSONObject serializeMap(){
        if (Objects.isNull(apiResult.getData()))return new JSONObject();
        return JSONObject.parseObject(JSONObject.toJSONString(apiResult.getData()));
    }

    /**
     * 将data转换为array数组
     * @param tClass
     * @param <E>
     * @return
     */
    public <E> E[] getArray(Class<E> tClass){
        if (Objects.isNull(apiResult.getData()))return null;
        if (apiResult.getData() instanceof Collection){
            Collection collection = (Collection) apiResult.getData();
            E[] array = (E[]) Array.newInstance(tClass,collection.size());
            Iterator<E> iterator = collection.iterator();
            Arrays.setAll(array,i -> iterator.next());
            return array;
        }else if (apiResult.getData().getClass().isArray()){
            return (E[]) apiResult.getData();
        }
        return null;
    }

    /**
     * 判断data是否是集合或者数组
     * @return
     */
    public boolean isArray(){
        if (Objects.isNull(apiResult.getData()))return false;
        return apiResult.getData().getClass().isArray() || apiResult.getData() instanceof Collection;
    }

    /**
     * 将data强转成List
     * @return
     */
    public List<Object> getList(){
        return getList(Object.class);
    }

    /**
     * 将data强转成List
     * @param tClass
     * @param <M>
     * @return
     */
    public <M> List<M> getList(Class<M> tClass){
        if (Objects.isNull(apiResult.getData()))return new ArrayList<>();
        if (apiResult.getData() instanceof Collection){
            Collection collection = (Collection) apiResult.getData();
            List<M> list = new ArrayList<>();
            list.addAll(collection);
            return list;
        }else if (apiResult.getData().getClass().isArray()){
            return (List<M>) Arrays.asList(apiResult.getData());
        }
        return null;
    }

}
