package com.stock.putaostock.element.strategy;



import com.stock.putaostock.element.IncrementRatio;
import com.stock.putaostock.element.Quarter;
import com.stock.putaostock.element.QuarterData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description 三季度策略
 * Created by putao on  2019/9/16 21:06
 *
 * 上一年第四季度数据 + 本年前两个季度真实数据  + 本年第三季度计算获得
 **/
public class ThirdQuarterStrategy implements QuarterStrategy {

    private static final String THIRD_MONTH = "-09-30";

    @Override
    public List<QuarterData> matchQuarterData ( List<QuarterData> datas , int year, IncrementRatio incrementRatio) {
        List<QuarterData> list = datas.stream().filter(v -> v.getYear() == year).collect(Collectors.toList());
        QuarterData lastThird = datas.stream().filter(v -> v.getYear() == year -1 && v.getQuarter().getQuarter() == 3).findAny().get();
        QuarterData lastFourth = datas.stream().filter(v -> v.getYear() == year -1 && v.getQuarter().getQuarter() == 4).findAny().get();
        list.add(lastFourth);

        QuarterData currentThird = QuarterData.buildAndCal(year , Quarter.THIRD , year + THIRD_MONTH , lastThird.getRevenue() ,
                String.valueOf(incrementRatio.getRevenueQuarterRatio()) , lastThird.getPureProfit() , String.valueOf(incrementRatio.getProfitQuarterRatio()) , 0);
        currentThird.setLastQuarterData(lastThird);

        list.add(currentThird);
        return list;
    }

    @Override
    public List<QuarterData> calReportIncrement ( List<QuarterData> datas , int year ) {
        List<Integer> integers = Arrays.asList(Quarter.FIRST.getQuarter() , Quarter.SECOND.getQuarter() , Quarter.THIRD.getQuarter());
        return datas.stream().filter(v -> v.getYear() == year - 1 && integers.contains(v.getQuarter().getQuarter())).collect(Collectors.toList());
    }
}
