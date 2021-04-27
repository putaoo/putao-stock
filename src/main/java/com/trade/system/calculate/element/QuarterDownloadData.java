package com.trade.system.calculate.element;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * Description 下载数据
 * Created by putao on  2019/9/17 8:15
 **/
@Data
public class QuarterDownloadData implements Serializable{
    //报告期
    @JSONField(name = "date")
    private String date;
    //营收
    @JSONField(name = "yyzsr")
    private String revenue;
    //营收增速
    @JSONField(name = "yyzsrtbzz")
    private String revenueIncrementRatio;
    //归属净利润
    @JSONField(name = "gsjlr")
    private String pureProfit;
    //归属净利润同比增长
    @JSONField(name = "gsjlrtbzz")
    private String pureProfitIncrementRatio;
}
