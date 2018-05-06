package cn.tomy.domain;

import java.io.Serializable;

/**
 * Created by tomy on 18-1-28.
 */
public class JsonResponse<T> implements Serializable {
    private String code;
    private String mes;
    private T data;

    public JsonResponse(){}
    public JsonResponse(String code,String mes,T data){
        this.code=code;
        this.mes=mes;
        this.data=data;
    }

    public void setCode(String code){
        this.code=code;
    }
    public void setMes(String mes){
        this.mes=mes;
    }
    public void setData(T data){
        this.data=data;
    }
    public String getCode(){
        return code;
    }
    public String getMes(){
        return mes;
    }
    public T getData(){
        return data;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "code='" + code + '\'' +
                ", mes='" + mes + '\'' +
                ", data=" + data +
                '}';
    }

}
