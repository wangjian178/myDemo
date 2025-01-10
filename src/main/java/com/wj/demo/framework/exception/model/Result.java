package com.wj.demo.framework.exception.model;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/18 10:23
 */
public class Result<T> {

    private static final String SUCCESS_CODE = "0";

    private static final String SUCCESS_MSG = "操作成功";

    private static final String FAIL_CODE = "-1";

    private static final String FAIL_MSG = "操作失败";

    private static final Integer ALERT = 1;

    private static final Integer NOT_ALERT = 0;

    private String code;

    private String msg;

    private T data;

    private Integer alert = NOT_ALERT;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> build() {
        return new Result<>();
    }

    public static <T> Result<T> ofSuccess() {
        Result<T> result = build();
        result.setCode(SUCCESS_CODE);
        result.setMsg(SUCCESS_MSG);
        return result;
    }

    public static <T> Result<T> ofSuccess(T data) {
        Result<T> result = build();
        result.setCode(SUCCESS_CODE);
        result.setMsg(SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> ofSuccess(String msg) {
        Result<T> result = build();
        result.setCode(SUCCESS_CODE);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> ofFail() {
        Result<T> result = build();
        result.setCode(FAIL_CODE);
        result.setMsg(FAIL_MSG);
        return result;
    }

    public static <T> Result<T> ofFail(String msg) {
        Result<T> result = build();
        result.setCode(FAIL_CODE);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> ofFail(String code, String msg) {
        Result<T> result = build();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> boolean isSuccess(Result<T> result) {
        return SUCCESS_CODE.equals(result.code);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("code='").append(code).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", data=").append(data);
        sb.append(", alert=").append(alert);
        sb.append('}');
        return sb.toString();
    }
}
