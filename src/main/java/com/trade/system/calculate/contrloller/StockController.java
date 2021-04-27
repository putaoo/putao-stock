package com.trade.system.calculate.contrloller;

import com.trade.system.calculate.core.CalculateStock;
import com.trade.system.calculate.core.ExcelExport;
import com.trade.system.calculate.core.Response;
import com.trade.system.calculate.dto.ParamsDTO;
import com.trade.system.calculate.element.IncrementRatio;
import com.trade.system.calculate.util.DataUtil;
import com.trade.system.calculate.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Description d
 * Created by putao on  2019/11/4 13:58
 **/
@Controller
@ResponseBody
@RequestMapping("/stock")
public class StockController {
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @PostMapping("/query")
    public Result<ExcelExport> greetingForm( @RequestBody ParamsDTO params) {
        logger.info("开始下载：{}",params.buildRealCode());
        CalculateStock calculateStock = CalculateStock.builder()
                .baseInformation(DataUtil.getBaseInformation(params.buildRealCode()))
                .capitalStock(DataUtil.getCapitalStock(params.buildRealCode()))
                .incrementRatio(IncrementRatio.build(params.getProfitInc() , params.getRevenueInc()))
                .build();

        calculateStock.setQuarterData(DataUtil.getQuarterData(params.buildRealCode()));

        return Result.success(calculateStock.export());
    }

    @PostMapping("/restQuery")
    public Result<Response> greetingForm1( @RequestBody ParamsDTO params) {
        CalculateStock cp = new CalculateStock();
        logger.info("开始下载：{}",params.buildRealCode());
        cp.setBaseInformation(DataUtil.getBaseInformation(params.buildRealCode()));
        cp.setCapitalStock(DataUtil.getCapitalStock(params.buildRealCode()));
        cp.setIncrementRatio(IncrementRatio.build(params.getProfitInc(),params.getRevenueInc()));
        cp.setQuarterData(DataUtil.getQuarterData(params.buildRealCode()));

        return Result.success(ExcelExport.buildResponse(cp.export()));
    }
}
