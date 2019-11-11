package com.stock.putaostock.contrloller;

import com.stock.putaostock.core.CalculateStock;
import com.stock.putaostock.core.ExcelExport;
import com.stock.putaostock.core.Response;
import com.stock.putaostock.dto.ParamsDTO;
import com.stock.putaostock.element.IncrementRatio;
import com.stock.putaostock.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * Description d
 * Created by putao on  2019/11/4 13:58
 **/
@Controller
public class StockController {
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @PostMapping("/query")
    public ModelAndView greetingForm(@RequestBody ParamsDTO params) {
        ModelAndView mv = new ModelAndView();
        try{
            CalculateStock cp = new CalculateStock();
            logger.info("开始下载：{}",params.buildRealCode());
            cp.setBaseInformation(DataUtil.getBaseInformation(params.buildRealCode()));
            cp.setCapitalStock(DataUtil.getCapitalStock(params.buildRealCode()));
            cp.setIncrementRatio(IncrementRatio.build(params.getProfitInc(),params.getRevenueInc()));
            cp.setQuarterData(DataUtil.getQuarterData(params.buildRealCode()));
            logger.info("下载成功，股票名称：{} ，股票代码：{}",cp.getBaseInformation().getStockName(),cp.getBaseInformation().getStockCode());
            mv.addObject("excelExport",cp.export());
            mv.setViewName("/show.html");
        }catch ( Exception e ){
            mv.addObject("error",e.getMessage());
            mv.setViewName("/error.html");
        }
        return mv;
    }

    @GetMapping("/query")
    public String greetingForm(Model model) {
        model.addAttribute("params", new ParamsDTO());
        return "/query.html";
    }

    @PostMapping("/restQuery")
    @ResponseBody
    public Response greetingForm1( @RequestBody ParamsDTO params) {
        try{
            CalculateStock cp = new CalculateStock();
            logger.info("开始下载：{}",params.buildRealCode());
            cp.setBaseInformation(DataUtil.getBaseInformation(params.buildRealCode()));
            cp.setCapitalStock(DataUtil.getCapitalStock(params.buildRealCode()));
            cp.setIncrementRatio(IncrementRatio.build(params.getProfitInc(),params.getRevenueInc()));
            cp.setQuarterData(DataUtil.getQuarterData(params.buildRealCode()));

            return ExcelExport.buildResponse(cp.export());
        }catch ( Exception e ){
            return null;
        }
    }
}
