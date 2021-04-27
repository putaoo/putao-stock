package com.trade.system.calculate.element.strategy;



import com.trade.system.calculate.element.IncrementRatio;
import com.trade.system.calculate.element.Quarter;
import com.trade.system.calculate.element.QuarterData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description 一季度策略
 * Created by putao on  2019/9/16 21:05
 *
 * 上一年后三个季度数据 + 本年第一季度计算获取数据
 **/
public class FirstQuarterStrategy implements QuarterStrategy {

    private static final String FIRST_MONTH = "-01-31";

    @Override
    public List<QuarterData> matchQuarterData ( List<QuarterData> datas  , int year, IncrementRatio incrementRatio) {
        List<QuarterData> list = new ArrayList<>();
        QuarterData lastFirst = datas.stream().filter(v -> v.getYear() == year -1 && v.getQuarter().getQuarter() == Quarter.FIRST.getQuarter()).findAny().get();
        QuarterData lastSecond = datas.stream().filter(v -> v.getYear() == year -1 && v.getQuarter().getQuarter() == Quarter.SECOND.getQuarter()).findAny().get();
        QuarterData lastThird = datas.stream().filter(v -> v.getYear() == year -1 && v.getQuarter().getQuarter() == Quarter.THIRD.getQuarter()).findAny().get();
        QuarterData lastFourth = datas.stream().filter(v -> v.getYear() == year -1 && v.getQuarter().getQuarter() == Quarter.FOURTH.getQuarter()).findAny().get();
        list.add(lastSecond);
        list.add(lastThird);
        list.add(lastFourth);

        QuarterData currentFirst = QuarterData.buildAndCal(year , Quarter.FIRST , year + FIRST_MONTH , lastFourth.getRevenue() ,
                String.valueOf(incrementRatio.getRevenueQuarterRatio()) , lastFourth.getPureProfit() , String.valueOf(incrementRatio.getProfitQuarterRatio()) , 0);
        currentFirst.setLastQuarterData(lastFirst);

        list.add(currentFirst);

        return list;
    }

    @Override
    public List<QuarterData> calReportIncrement ( List<QuarterData> datas , int year ) {
        List<Integer> integers = Arrays.asList(Quarter.FIRST.getQuarter());
        return datas.stream().filter(v -> v.getYear() == year - 1 && integers.contains(v.getQuarter().getQuarter())).collect(Collectors.toList());
    }
}
