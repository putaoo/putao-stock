package com.trade.system.calculate.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Description 传参
 * Created by putao on  2019/11/4 14:07
 **/
@Data
public class ParamsDTO implements Serializable {
    private String prefix;
    private String code;
    private double profitInc;
    private double revenueInc;

    public String buildRealCode(){
        return prefix + code;
    }
}
