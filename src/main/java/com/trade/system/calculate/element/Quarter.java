package com.trade.system.calculate.element;


import com.trade.system.calculate.element.strategy.*;

import java.util.Arrays;
import java.util.List;

/**
 * Description 季度
 * Created by putao on  2019/9/16 12:27
 **/
public enum  Quarter {

    FIRST(1, Arrays.asList(1,2,3)){
        @Override
        public QuarterStrategy matchStrategy () {
            return new FirstQuarterStrategy();
        }
    },
    SECOND(2,Arrays.asList(4,5,6)){
        @Override
        public QuarterStrategy matchStrategy () {
            return new SecondQuarterStrategy();
        }
    },
    THIRD(3,Arrays.asList(7,8,9)){
        @Override
        public QuarterStrategy matchStrategy () {
            return new ThirdQuarterStrategy();
        }
    },
    FOURTH(4,Arrays.asList(10,11,12)) {
        @Override
        public QuarterStrategy matchStrategy () {
            return new FourthQuarterStrategy();
        }
    };

    private int quarter;
    private List<Integer> months;

     Quarter(int quarter,List<Integer> months){
        this.quarter = quarter;
        this.months = months;
    }

    public int getQuarter(){
         return quarter;
    }
    public List<Integer> getMonths(){
         return months;
    }

    public static Quarter findQuarterByMonth(int month){
        for ( Quarter quarter : Quarter.values() ){
            if ( quarter.getMonths().stream().anyMatch(v -> v == month)){
                return quarter;
            }
        }
        return null;
    }

    public abstract QuarterStrategy matchStrategy();
}
