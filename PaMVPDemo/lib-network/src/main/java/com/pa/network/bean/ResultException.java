package com.pa.network.bean;

/**
 * Created by wen on 2018/5/14.
 */
public class ResultException extends RuntimeException {

    private String errCode = "";

    public ResultException(String errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }
}
