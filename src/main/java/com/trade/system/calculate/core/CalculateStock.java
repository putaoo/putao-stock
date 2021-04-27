package com.trade.system.calculate.core;


import com.trade.system.calculate.element.*;
import com.trade.system.calculate.element.strategy.QuarterStrategy;
import com.trade.system.calculate.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description 计算股价类
 * Created by putao on  2019/9/16 11:57
 **/
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CalculateStock implements Serializable {
    //计算日期
    private String date;
    //总股本
    private CapitalStock capitalStock;
    //市盈率
    private BaseInformation baseInformation;
    //增长率
    private IncrementRatio incrementRatio;
    //本年季度模拟数据
    private List<QuarterData> quarterData;
    //股价
    private StockPrice stockPrice;
    //报告期季度数据
    private List<QuarterData> reportData;
    //报告期同比增长
    private ReportIncrement reportIncrement;

    //1,找到目标月份
    //2，分开已有月份和没有的月份
    //3，根据没有的月份增长率计算净利润
    //4，加总净利润
    //5，计算股价
    private void setDate(String date){
        this.date = date;
    }
    private void setStockPrice(StockPrice stockPrice){
        this.stockPrice = stockPrice;
    }

    public void setQuarterData ( List<QuarterData> quarterDatas ){
        try{
            this.date = DateUtils.longToDateString(DateUtils.currentTimeMillis(),DateUtils.ZH_PATTERN_DAY);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            Quarter quarter = Quarter.findQuarterByMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
            QuarterStrategy quarterStrategy = quarter.matchStrategy();
            //计算股价
            calStockPrice(quarterDatas , year , quarterStrategy);
            //计算报告期增长情况
            calReportIncrement(quarterDatas , year , quarterStrategy);
        }catch ( Exception e ){
            System.out.println("Error data from stock code" + baseInformation.getStockName());
        }
    }

    private void calReportIncrement ( List<QuarterData> quarterDatas , int year , QuarterStrategy quarterStrategy ) {
        List<QuarterData> lastReportData = quarterStrategy.calReportIncrement(quarterDatas,year);
        List<QuarterData> currentReportData = this.quarterData.stream().filter(s -> s.getYear() == year).collect(Collectors.toList());

        currentReportData.forEach(m -> m.setLastQuarterData(lastReportData.stream().filter(l -> l.getQuarter().getQuarter() == m.getQuarter().getQuarter()).findFirst().get()));
        this.reportData = currentReportData;

        double lastReportSumProfit = lastReportData.stream().mapToDouble(k -> Double.valueOf(k.getPureProfit())).sum();
        double currentReportSumProfit = currentReportData.stream().filter(n -> n.getYear() == year).mapToDouble(k -> Double.valueOf(k.getPureProfit())).sum();
        double profitIncRatio = BigDecimal.valueOf(currentReportSumProfit).subtract(BigDecimal.valueOf(lastReportSumProfit)).divide(BigDecimal.valueOf(lastReportSumProfit) , 4 , BigDecimal.ROUND_HALF_UP).doubleValue();

        double lastRevenueSum = lastReportData.stream().mapToDouble(v -> Double.valueOf(v.getRevenue())).sum();
        double currentRevenueSum = currentReportData.stream().filter(b -> b.getYear() == year).mapToDouble(c -> Double.valueOf(c.getRevenue())).sum();
        double revenueIncRatio = BigDecimal.valueOf(currentRevenueSum).subtract(BigDecimal.valueOf(lastRevenueSum)).divide(BigDecimal.valueOf(lastRevenueSum) , 4 , BigDecimal.ROUND_HALF_UP).doubleValue();

        this.reportIncrement = ReportIncrement.buildProfit(lastReportSumProfit,currentReportSumProfit,profitIncRatio).buildRevenue(lastRevenueSum,currentRevenueSum,revenueIncRatio);
    }

    private void calStockPrice ( List<QuarterData> quarterDatas , int year , QuarterStrategy quarterStrategy ) {
        this.quarterData = quarterStrategy.matchQuarterData(quarterDatas,year,incrementRatio);

        double sumProfit = this.quarterData.stream().mapToDouble(v -> Double.valueOf(v.getPureProfit())).sum();
        double v = BigDecimal.valueOf(this.baseInformation.getDynamicPE()).multiply(BigDecimal.valueOf(sumProfit)).divide(BigDecimal.valueOf(capitalStock.getCapitalStock()) , 2 , BigDecimal.ROUND_HALF_UP).doubleValue();
        this.stockPrice = new StockPrice(v);
    }

    public ExcelExport export(){
        String isLower = stockPrice.getStockPrice() > baseInformation.getCurrentStockPrice() ? "yes" : "no";
        double ratio = BigDecimal.valueOf(stockPrice.getStockPrice()).subtract(BigDecimal.valueOf(baseInformation.getCurrentStockPrice())).divide(BigDecimal.valueOf(baseInformation.getCurrentStockPrice()) , 4 , BigDecimal.ROUND_HALF_UP).doubleValue();

        this.stockPrice.setIsLower(isLower);
        this.stockPrice.setLowerRatio(ratio);

        return ExcelExport.build(date,incrementRatio, baseInformation ,stockPrice,reportIncrement);
    }
}
