package com.trade.system.calculate.vo;

import lombok.*;

/**
 * Description
 * Created by putao on  2021/4/27 21:57
 **/
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> apiResult = new Result<>();
        apiResult.code = "0";
        apiResult.data = data;
        apiResult.msg = "success";
        return apiResult;
    }
}
