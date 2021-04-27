package com.trade.system.calculate.element;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Description =季度数据
 * Created by putao on  2019/9/16 9:32
 **/
@Data
public class QuarterData implements Serializable {
    //年份
    private int year;
    //季度
    private Quarter quarter;
    //报告期
    private String date;
    //营收
    private String revenue;
    //营收增速
    private String revenueIncrementRatio;
    //归属净利润
    private String pureProfit;
    //归属净利润同比增长
    private String pureProfitIncrementRatio;
    //真实数据还是计算所得
    private int isTrue;
    //上年同期真实数据
    private QuarterData lastQuarterData;

    private QuarterData (){

    }

    public static QuarterData buildByDownloadData(QuarterDownloadData downloadData){
        QuarterData quarterData = new QuarterData();
        quarterData.setDate(downloadData.getDate());
        quarterData.setIsTrue(1);
        quarterData.setRevenue(downloadData.getRevenue());
        quarterData.setRevenueIncrementRatio(downloadData.getRevenueIncrementRatio());
        quarterData.setPureProfit(downloadData.getPureProfit());
        quarterData.setPureProfitIncrementRatio(downloadData.getPureProfitIncrementRatio());
        return quarterData;
    }

    public static QuarterData buildAndCal( int year,Quarter quarter,String date,String revenue,
                                           String revenueIncrementRatio,String pureProfit,String pureProfitIncrementRatio,int isTrue){
        QuarterData quarterData = new QuarterData();
        quarterData.setYear(year);
        quarterData.setQuarter(quarter);
        quarterData.setDate(date);
        quarterData.setIsTrue(isTrue);
        quarterData.revenueIncrementRatio = revenueIncrementRatio;
        quarterData.pureProfitIncrementRatio = pureProfitIncrementRatio;
        //根据营收增速计算营收
        BigDecimal incRevenue = new BigDecimal(revenue).multiply(new BigDecimal(revenueIncrementRatio));
        quarterData.revenue = new BigDecimal(revenue).add(incRevenue).toString();
        //根据净利润增速计算净利润
        BigDecimal incProfit = new BigDecimal(pureProfit).multiply(new BigDecimal(pureProfitIncrementRatio));
        quarterData.pureProfit = new BigDecimal(pureProfit).add(incProfit).toString();
        return quarterData;
    }

    public void setRevenue(String revenue){
        if ( revenue.indexOf("万") > 0 ){
            revenue = revenue.substring(0,revenue.indexOf("万"));
            this.revenue = new BigDecimal(revenue).divide(BigDecimal.valueOf(10000),4,BigDecimal.ROUND_HALF_UP).toString();
            return;
        }
        if ( revenue.indexOf("亿") > 0 ){
            this.revenue = revenue.substring(0,revenue.indexOf("亿"));
            return;
        }
        this.revenue = revenue;
    }

    public void setPureProfit(String pureProfit){
        if ( pureProfit.indexOf("万") > 0 ){
            pureProfit = pureProfit.substring(0,pureProfit.indexOf("万"));
            this.pureProfit = new BigDecimal(pureProfit).divide(BigDecimal.valueOf(10000),4,BigDecimal.ROUND_HALF_UP).toString();
            return;
        }
        if ( pureProfit.indexOf("亿") > 0 ){
            this.pureProfit = pureProfit.substring(0,pureProfit.indexOf("亿"));
            return;
        }
        this.pureProfit = pureProfit;
    }

    public void setPureProfitIncrementRatio(String pureProfitIncrementRatio){
        if ( "--".equals(pureProfitIncrementRatio) ){
            this.pureProfitIncrementRatio = "0";
            return;
        }
        if ( pureProfitIncrementRatio.indexOf("-") > 0){
            pureProfitIncrementRatio = pureProfitIncrementRatio.substring(pureProfitIncrementRatio.indexOf("-"),pureProfitIncrementRatio.length());
            this.pureProfitIncrementRatio = "-" + new BigDecimal(pureProfitIncrementRatio).divide(BigDecimal.valueOf(100),10,BigDecimal.ROUND_HALF_UP).toString();
            return;
        }
        this.pureProfitIncrementRatio = new BigDecimal(pureProfitIncrementRatio).divide(BigDecimal.valueOf(100),4,BigDecimal.ROUND_HALF_UP).toString();
    }

    public void setRevenueIncrementRatio(String revenueIncrementRatio){
        if ( "--".equals(revenueIncrementRatio) ){
            this.revenueIncrementRatio = "0";
            return;
        }
        if ( revenueIncrementRatio.indexOf("-") > 0){
            revenueIncrementRatio = revenueIncrementRatio.substring(revenueIncrementRatio.indexOf("-"),revenueIncrementRatio.length());
            this.revenueIncrementRatio = "-" + new BigDecimal(revenueIncrementRatio).divide(BigDecimal.valueOf(100),4,BigDecimal.ROUND_HALF_UP).toString();
            return;
        }
        this.revenueIncrementRatio = new BigDecimal(revenueIncrementRatio).divide(BigDecimal.valueOf(100),4,BigDecimal.ROUND_HALF_UP).toString();
    }

    public void setDate(String date){
        this.date = date;
        this.setQuarter();
        this.setYear();
    }

    private void setYear(){
        this.year = Integer.parseInt(this.date.substring(0,4));
    }

    private void setQuarter(){
        String month = this.date.split("-")[1];
        if ( "03".equals(month) ){
            this.quarter = Quarter.FIRST;
        }else if ( "06".equals(month) ){
            this.quarter = Quarter.SECOND;
        }else if ( "09".equals(month) ){
            this.quarter = Quarter.THIRD;
        }else if ( "12".equals(month) ){
            this.quarter = Quarter.FOURTH;
        }
    }
}
