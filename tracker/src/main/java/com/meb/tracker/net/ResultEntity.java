package com.meb.tracker.net;

public class ResultEntity {

    /**
     * success : true
     * code : 0
     * msg :
     * desc :
     * data : null
     */

    private boolean success;
    private int code;
    private String msg;
    private String desc;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                '}';
    }
}
