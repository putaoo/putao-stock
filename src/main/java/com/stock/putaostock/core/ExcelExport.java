package com.stock.putaostock.core;

import com.stock.putaostock.element.BaseInformation;
import com.stock.putaostock.element.IncrementRatio;
import com.stock.putaostock.element.ReportIncrement;
import com.stock.putaostock.element.StockPrice;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Description 导出
 * Created by putao on  2019/9/18 22:13
 **/
@Data
public class ExcelExport implements Serializable{

    public static String[] titles = {"日期" , "股票名称" , "股票代码","所属板块","当前股价","计算股价","静态市盈率" ,"动态市盈率","业绩同比预增", "股价是否低估","低估百分比","目标营收增速", "目标净利润增速","报告期营收增速","报告期净利润增速"};

    //计算日期
    private String date;
    //市盈率
    private BaseInformation baseInformation;
    //报告期同比增长
    private ReportIncrement reportIncrement;
    //股价
    private StockPrice stockPrice;
    //预设增长率
    private IncrementRatio incrementRatio;


    public static ExcelExport build( String date, IncrementRatio incrementRatio, BaseInformation baseInformation , StockPrice stockPrice, ReportIncrement reportIncrement){
        ExcelExport excelExport = new ExcelExport();
        excelExport.setDate(date);
        excelExport.setIncrementRatio(incrementRatio);
        excelExport.setBaseInformation(baseInformation);
        excelExport.setStockPrice(stockPrice);
        excelExport.setReportIncrement(reportIncrement);
        return excelExport;
    }

    public String[] toStringArray(){
        return new String[]{date,baseInformation.getStockName(),baseInformation.getStockCode(),baseInformation.getIndustry(), String.valueOf(baseInformation.getCurrentStockPrice()),
                String.valueOf(stockPrice.getStockPrice()),String.valueOf(baseInformation.getStaticPE()),String.valueOf(baseInformation.getDynamicPE()),baseInformation.getIncrement(), stockPrice.getIsLower(),
                String.valueOf(BigDecimal.valueOf(stockPrice.getLowerRatio()).multiply(BigDecimal.valueOf(100)).doubleValue()) + "%",
                String.valueOf(BigDecimal.valueOf(incrementRatio.getRevenueQuarterRatio()).multiply(BigDecimal.valueOf(100)).doubleValue()) + "%",
                String.valueOf(BigDecimal.valueOf(incrementRatio.getProfitQuarterRatio()).multiply(BigDecimal.valueOf(100)).doubleValue()) + "%",
                String.valueOf(BigDecimal.valueOf(reportIncrement.getReportRevenueIncrementRatio()).multiply(BigDecimal.valueOf(100)).doubleValue()) + "%",
                String.valueOf(BigDecimal.valueOf(reportIncrement.getReportProfitIncrementRatio()).multiply(BigDecimal.valueOf(100)).doubleValue()) + "%"};
    }

    public static Response buildResponse(ExcelExport export){
        Response response = new Response();
        response.setCurrentReportProfit(export.getReportIncrement().getCurrentReportProfit());
        response.setCurrentRevenue(export.getReportIncrement().getCurrentRevenue());
        response.setCurrentStockPrice(export.getStockPrice().getStockPrice());
        response.setDate(export.getDate());
        response.setDynamicPE(export.getBaseInformation().getDynamicPE());
        response.setIndustry(export.getBaseInformation().getIndustry());
        response.setIsLower(export.getStockPrice().getIsLower());
        response.setIncrement(export.getBaseInformation().getIncrement());
        return response;
    }
}
