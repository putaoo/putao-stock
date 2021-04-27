package com.trade.system.calculate.element;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description 报告期增长率
 * Created by putao on  2019/9/16 22:27
 **/
@Data
@NoArgsConstructor
public class ReportIncrement implements Serializable {
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

    public static ReportIncrement buildProfit ( double lastReportProfit, double currentReportProfit, double reportIncrementRatio){
        ReportIncrement reportIncrement = new ReportIncrement();
        reportIncrement.setCurrentReportProfit(currentReportProfit);
        reportIncrement.setLastReportProfit(lastReportProfit);
        reportIncrement.setReportProfitIncrementRatio(reportIncrementRatio);
        return reportIncrement;
    }

    public ReportIncrement buildRevenue (double lastRevenue,double currentRevenue,double reportRevenueIncrementRatio){
        this.lastRevenue = lastRevenue;
        this.currentRevenue = currentRevenue;
        this.reportRevenueIncrementRatio = reportRevenueIncrementRatio;
        return this;
    }
}
