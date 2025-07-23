package com.wj.demo.framework.exception.model;

import com.wj.demo.framework.exception.enums.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/18 10:23
 */
@Getter
@Setter
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

    public static <T> Result<T> build() {
        return new Result<>();
    }

    public static <T> Result<T> ofSuccess() {
        return ofFail(SUCCESS_CODE, SUCCESS_MSG);
    }

    public static <T> Result<T> ofSuccess(T data) {
        Result<T> result = build();
        result.setCode(SUCCESS_CODE);
        result.setMsg(SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> ofSuccess(String msg) {
        return ofFail(SUCCESS_CODE, msg);
    }

    public static <T> Result<T> ofSuccess(T data, String msg) {
        Result<T> result = build();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> ofFail() {
        return ofFail(FAIL_MSG);
    }

    public static <T> Result<T> ofFail(String msg) {
        return ofFail(FAIL_CODE, msg);
    }

    public static <T> Result<T> ofFail(ResultCodeEnum resultCodeEnum) {
        Result<T> result = build();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(result.getMsg());
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
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", alert=" + alert +
                '}';
    }
}
