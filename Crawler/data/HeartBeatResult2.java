1
https://raw.githubusercontent.com/pengfeigao/AgoraCallApi/master/call-plugin-api/src/main/java/com/basetools/model/HeartBeatResult2.java
package com.basetools.model;

import java.io.Serializable;

/**
 * 心跳响应数据
 */
public class HeartBeatResult2 implements Serializable {

    private String msg;
    private int code;
    private Data data;
    private Object ext;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }

    public static class Data {
        private int diamondNum;

        public int getDiamondNum() {
            return diamondNum;
        }

        public void setDiamondNum(int diamondNum) {
            this.diamondNum = diamondNum;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "diamondNum=" + diamondNum +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HeartBeatResult2{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                ", ext=" + ext +
                '}';
    }
}
