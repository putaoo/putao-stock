package com.trade.system.calculate.core;

import lombok.Data;

import java.io.Serializable;

/**
 * Description
 * Created by putao on  2019/11/7 22:15
 **/
@Data
public class Response implements Serializable {

    private String date;
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
    //报告期上年净利润
    private double lastReportProfit;
    //报告期本年净利润
    private double currentReportProfit;
    //报告期净利润同比增长率
    private double reportProfitIncrementRatio;
    //报告期上年营收
    private double lastRevenue;
    //报告期本年营收
    private double currentRevenue;
    //报告期营收增速
    private double reportRevenueIncrementRatio;
    //计算所得股价
    private double stockPrice;
    //高估（计算所得比时机的低）/低估(计算所得比实际的高)
    private String isLower;
    //高估/低估的百分比
    private double lowerRatio;
    //季度净利润增长率
    private double profitQuarterRatio;
    //季度营收增长率
    private double revenueQuarterRatio;


}
