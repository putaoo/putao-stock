package com.stock.putaostock.element;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description 股价
 * Created by putao on  2019/9/16 12:08
 **/
@Data
@NoArgsConstructor
public class StockPrice implements Serializable {
    //计算所得股价
    private double stockPrice;
    //高估（计算所得比时机的低）/低估(计算所得比实际的高)
    private String isLower;
    //高估/低估的百分比
    private double lowerRatio;

    public StockPrice(double stockPrice){
        this.stockPrice = stockPrice;
    }
}
