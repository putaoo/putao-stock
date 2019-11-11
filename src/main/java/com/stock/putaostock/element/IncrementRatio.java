package com.stock.putaostock.element;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Description 增长率
 * Created by putao on  2019/9/16 11:54
 **/
@Data
@AllArgsConstructor
public class IncrementRatio implements Serializable {
    //季度净利润增长率
    private double profitQuarterRatio;
    //季度营收增长率
    private double revenueQuarterRatio;

    public static IncrementRatio build(double profitQuarterRatio,double revenueQuarterRatio){
        return new IncrementRatio(profitQuarterRatio,revenueQuarterRatio);
    }
}
