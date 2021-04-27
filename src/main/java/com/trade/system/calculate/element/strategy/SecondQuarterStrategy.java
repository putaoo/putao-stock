package com.trade.system.calculate.element.strategy;



import com.trade.system.calculate.element.IncrementRatio;
import com.trade.system.calculate.element.Quarter;
import com.trade.system.calculate.element.QuarterData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description 二季度策略
 * Created by putao on  2019/9/16 21:06
 *
 * 上一年后两个季度 + 本年第一季度 + 本年第二季度计算获得
 **/
public class SecondQuarterStrategy implements QuarterStrategy {

    private static final String SECOND_MONTH = "-06-30";

    @Override
    public List<QuarterData> matchQuarterData ( List<QuarterData> datas  , int year, IncrementRatio incrementRatio) {
        List<QuarterData> list = datas.stream().filter(v -> v.getYear() == year).collect(Collectors.toList());;
        QuarterData lastSecond = datas.stream().filter(v -> v.getYear() == year -1 && v.getQuarter().getQuarter() == Quarter.SECOND.getQuarter()).findAny().get();
        QuarterData lastThird = datas.stream().filter(v -> v.getYear() == year -1 && v.getQuarter().getQuarter() == Quarter.THIRD.getQuarter()).findAny().get();
        QuarterData lastFourth = datas.stream().filter(v -> v.getYear() == year -1 && v.getQuarter().getQuarter() == Quarter.FOURTH.getQuarter()).findAny().get();
        list.add(lastThird);
        list.add(lastFourth);

        QuarterData currentSecond = QuarterData.buildAndCal(year , Quarter.SECOND , year + SECOND_MONTH , lastFourth.getRevenue() ,
                String.valueOf(incrementRatio.getRevenueQuarterRatio()) , lastFourth.getPureProfit() , String.valueOf(incrementRatio.getProfitQuarterRatio()) , 0);
        currentSecond.setLastQuarterData(lastSecond);

        list.add(currentSecond);

        return list;
    }

    @Override
    public List<QuarterData> calReportIncrement ( List<QuarterData> datas , int year ) {
        List<Integer> integers = Arrays.asList(Quarter.FIRST.getQuarter() , Quarter.SECOND.getQuarter());
        return datas.stream().filter(v -> v.getYear() == year - 1 && integers.contains(v.getQuarter().getQuarter())).collect(Collectors.toList());
    }
}
