package com.pa.core.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wen on 2018/5/14.
 */
public class TopResponse<Data> {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String msg;
    @SerializedName("data")
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String code) {
        this.status = code;
    }

    public String getInfo() {
        return msg;
    }

    public void setInfo(String info) {
        this.msg = info;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
