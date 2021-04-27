package com.trade.system.calculate.element.strategy;



import com.trade.system.calculate.element.IncrementRatio;
import com.trade.system.calculate.element.Quarter;
import com.trade.system.calculate.element.QuarterData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description 四季度策略
 * Created by putao on  2019/9/16 21:07
 *
 * 本年前三季度的真实数据 + 本年第四季度的计算数据
 **/
public class FourthQuarterStrategy implements QuarterStrategy {

    private static final String FOURTH_MONTH = "-12-31";

    @Override
    public List<QuarterData> matchQuarterData ( List<QuarterData> datas  , int year, IncrementRatio incrementRatio) {
        List<QuarterData> list = datas.stream().filter(v -> v.getYear() == year).collect(Collectors.toList());
        QuarterData lastFourth = datas.stream().filter(v -> v.getYear() == year -1 && v.getQuarter().getQuarter() == 4).findAny().get();

        QuarterData currentFourth = QuarterData.buildAndCal(year , Quarter.FOURTH , year + FOURTH_MONTH , lastFourth.getRevenue() ,
                String.valueOf(incrementRatio.getRevenueQuarterRatio()) , lastFourth.getPureProfit() , String.valueOf(incrementRatio.getProfitQuarterRatio()) , 0);
        currentFourth.setLastQuarterData(lastFourth);

        list.add(currentFourth);

        return list;
    }

    @Override
    public List<QuarterData> calReportIncrement ( List<QuarterData> datas , int year ) {
        List<Integer> integers = Arrays.asList(Quarter.FIRST.getQuarter() , Quarter.SECOND.getQuarter() , Quarter.THIRD.getQuarter(),Quarter.FOURTH.getQuarter());
        return datas.stream().filter(v -> v.getYear() == year - 1 && integers.contains(v.getQuarter().getQuarter())).collect(Collectors.toList());
    }
}
