package com.stock.putaostock.element.strategy;


import com.stock.putaostock.element.IncrementRatio;
import com.stock.putaostock.element.QuarterData;

import java.util.List;

/**
 * Description 季度选取策略
 * Created by putao on  2019/9/16 21:03
 **/
public interface QuarterStrategy {

    //计算股价匹配季度
    List<QuarterData> matchQuarterData ( List<QuarterData> datas , int year , IncrementRatio incrementRatio );

    //计算报告期同比增长率匹配季度
    List<QuarterData> calReportIncrement ( List<QuarterData> datas , int year );
}
