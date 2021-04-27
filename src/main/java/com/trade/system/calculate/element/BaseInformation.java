package com.trade.system.calculate.element;

import lombok.Data;

import java.io.Serializable;

/**
 * Description 市盈率
 * Created by putao on  2019/9/16 11:56
 **/
@Data
public class BaseInformation implements Serializable {

    //静态市盈率（上年年报归属净利润）
    private double staticPE;
    //动态市盈率
    private double dynamicPE;
    //净利润同比增还是减
    private String increment;
    //当前股价
    private double currentStockPrice;
    //股票代码
    private String stockCode;
    //股票名称
    private String stockName;
    //所属板块
    private String industry;

     private BaseInformation ( double staticPE, double dynamicPE, double currentStockPrice,
                               String stockCode, String stockName, String industry){
        this.staticPE = staticPE;
        this.dynamicPE = dynamicPE;
        this.increment = staticPE > dynamicPE ? "yes" : "no";
        this.currentStockPrice = currentStockPrice;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.industry = industry;
    }

    public static BaseInformation build( double staticPE, double dynamicPE, double currentStockPrice,
                                         String stockCode, String stockName, String industry){
        return new BaseInformation(staticPE,dynamicPE,currentStockPrice,stockCode,stockName,industry);
    }
}
