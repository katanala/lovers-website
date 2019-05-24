package cn.fengyunxiao.nest.config;

/**
 *  json 返回的结构
 */
public class JsonResult<T> {
    // code=0表示成功，返回数据data。code!=0，表示错误，返回错误信息msg
    private int code;
    private T data;
    private String msg;

    public JsonResult() {
        code = 0;
    }

    public JsonResult<T> ok(T data, String msg) {
        this.code = 0;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public JsonResult<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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
}
