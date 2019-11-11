package com.stock.putaostock.core;

import com.stock.putaostock.element.IncrementRatio;
import com.stock.putaostock.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 批量下载器
 * Created by putao on  2019/9/19 18:28
 **/
public class BatchDownloader {
    private static final Logger logger = LoggerFactory.getLogger(BatchDownloader.class);

    public static List<ExcelExport> batchDownload(String prefix,int baseCode){
        List<ExcelExport> excelExports = new ArrayList<>();
        double dpInc = 0.15;
        double drInc = 0.1;
        for ( int i = 1 ; i <= 500 ; i++ ) {
            try {
                CalculateStock cp = new CalculateStock();
                logger.info("开始下载：{}",baseCode);
                String existCode = prefix + baseCode;
                cp.setBaseInformation(DataUtil.getBaseInformation(existCode));
                cp.setCapitalStock(DataUtil.getCapitalStock(existCode));
                cp.setIncrementRatio(IncrementRatio.build(dpInc,drInc));
                cp.setQuarterData(DataUtil.getQuarterData(existCode));
                excelExports.add(cp.export());
                logger.info("下载成功，股票名称：{} ，股票代码：{}",cp.getBaseInformation().getStockName(),cp.getBaseInformation().getStockCode());
            }catch ( Exception e ){
                logger.info("下载失败：{}",baseCode);
            }
            baseCode++;
        }

        return excelExports;
    }
}
