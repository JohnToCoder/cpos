package com.cpos.classes;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by john on 2018/1/1.
 * code:0,成功,1,失败
 * count:返回数据数量
 * data:返回结果集或者异常信息
 */
public class ResultInfo<T> {
    private int code;
    private int count;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <R>ResultInfo<R> creatResult(int resultcode,int total,R list){
        ResultInfo<R> resultlist = new ResultInfo<R>();
        resultlist.setCode(resultcode);
        resultlist.setCount(total);
        resultlist.setData(list);
        return resultlist;
    }
    public static <R>ResultInfo<R> creatError(int resultcode,int total,R excString) {
        Map mapexc = new HashMap();
        mapexc.put("errException",excString);
        JSONObject jolist = JSONObject.fromObject(mapexc);
        JSONArray jalist = JSONArray.fromObject(jolist);
        ResultInfo<R> resultlist = new ResultInfo<R>();
        resultlist.setCode(resultcode);
        resultlist.setCount(total);
        resultlist.setData((R)jalist.toString());
        return resultlist;
    }
}
