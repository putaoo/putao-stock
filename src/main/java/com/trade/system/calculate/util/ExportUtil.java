package com.trade.system.calculate.util;


import com.trade.system.calculate.core.ExcelExport;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Description Excel工具类
 * Created by putao on  2019/9/18 22:47
 **/
public class ExportUtil {
    /**
     * 把数据导出到path
     *
     * @param excelExports
     */
    public static void exportStocks ( List<ExcelExport> excelExports , String path , String[] titles , String sheetName) {
        //创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //从工作簿里面创建sheet
        //workbook.createSheet();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //设置sheet
        //sheet.setColumnWidth(2, 100);//设置某一个下标的列宽
        //sheet.setDefaultColumnStyle(column, style);//设置某一列的默认样式
        sheet.setDefaultColumnWidth(25);//设置所有列的列宽
        //sheet.setColumnHidden(columnIndex, hidden);//设置某一列是否隐藏
        //sheet.setDefaultRowHeight((short)(30*20));//设置行高60
        //sheet.setDefaultRowHeightInPoints(30);//设置行高30
        //合并
        //CellRangeAddress region1 = new CellRangeAddress(0 , 0 , 0 , 2);
        //CellRangeAddress region2 = new CellRangeAddress(1 , 1 , 0 , 2);
        //sheet.addMergedRegion(region1);
        //sheet.addMergedRegion(region2);

        //的sheet上创建行
        int rownum = 0;
        //HSSFRow row01 = sheet.createRow(rownum);
        //在row01上创建单元格
        //HSSFCell cell_row01 = row01.createCell(0);
        //向cell_row01写东西
        //cell_row01.setCellValue("股票数据");
        //设置标题样式
        //HSSFCellStyle titleStyle = createTitleCellStyle(workbook);
        //cell_row01.setCellStyle(titleStyle);

        //第二行
        //rownum++;
        //HSSFRow row02 = sheet.createRow(rownum);
        //HSSFCell cell_row02 = row02.createCell(0);
        //cell_row02.setCellValue("总数:" + excelExports.size() + "，导出时间:" + new Date().toLocaleString());
        //设置小标题样式
        //HSSFCellStyle secondTitleStyle = createSecondTitleStyle(workbook);
        //cell_row02.setCellStyle(secondTitleStyle);

        //第三行
        //rownum++;
        HSSFRow row03 = sheet.createRow(rownum);
        //得到表头的样式
        HSSFCellStyle tableTitleStyle = createTableTitleStyle(workbook);

        for ( int i = 0 ; i < titles.length ; i++ ) {
            HSSFCell cell = row03.createCell(i);
            cell.setCellValue(titles[ i ]);
            cell.setCellStyle(tableTitleStyle);
        }

        for ( int i = 0 ; i < excelExports.size() ; i++ ) {
            rownum++;
            try{
                HSSFRow row = sheet.createRow(rownum);
                ExcelExport data = excelExports.get(i);
                HSSFCellStyle tableBodyStyle = setRowCellCenter(workbook,data.getStockPrice().getLowerRatio() > 0.1 && "yes".equals(data.getStockPrice().getIsLower()) && "yes".equals(data.getBaseInformation().getIncrement()));
                String[] stringArray = data.toStringArray();
                for ( int j = 0 ; j < titles.length ; j++ ) {
                    HSSFCell dataCell = row.createCell(j);
                    dataCell.setCellValue(stringArray[j]);
                    dataCell.setCellStyle(tableBodyStyle);
                }
            }catch ( Exception e ){
                rownum--;
            }
        }

        //导出数据
        try {
            workbook.write(new File(path));
            System.out.println("导出完成");
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    /**
     * 得到表头样式
     *
     * @param workbook
     * @return
     */
    public static HSSFCellStyle createTableTitleStyle ( HSSFWorkbook workbook ) {
        HSSFCellStyle cellStyle = setRowCellCenter(workbook,false);//水平居中
        //设置字体
        HSSFFont font = setFontCellStyle(workbook , ( short ) 15 , HSSFColor.HSSFColorPredefined.BLACK.getIndex() , true , false , HSSFFont.U_NONE);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 创建小标题的样式
     *
     * @param workbook
     * @return
     */
    public static HSSFCellStyle createSecondTitleStyle ( HSSFWorkbook workbook ) {
        HSSFCellStyle cellStyle = setRowCellCenter(workbook,false);//水平居中
        //设置字体
        HSSFFont font = setFontCellStyle(workbook , ( short ) 18 , HSSFColor.HSSFColorPredefined.GOLD.getIndex() , true , false , HSSFFont.U_NONE);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 创建表头的样式
     *
     * @param workbook
     * @return
     */
    public static HSSFCellStyle createTitleCellStyle ( HSSFWorkbook workbook ) {
        HSSFCellStyle cellStyle = setRowCellCenter(workbook,false);
        //设置字体
        HSSFFont font = setFontCellStyle(workbook , ( short ) 30 , HSSFColor.HSSFColorPredefined.RED.getIndex() , true , false , HSSFFont.U_DOUBLE);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * @param workbook   工作簿
     * @param fontSize   字体大小
     * @param colorIndex 字体颜色  @see HSSFColorPredefined
     * @param bold       是否加粗
     * @param italic     是否斜体
     * @param undderLine 下划线风格  @see HSSFFont.U_DOUBLE
     * @return
     */
    public static HSSFFont setFontCellStyle ( HSSFWorkbook workbook ,
                                              short fontSize , short colorIndex , boolean bold , boolean italic ,
                                              byte undderLine ) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);//字体大小
        font.setColor(colorIndex);//设置字体颜色
        font.setBold(bold);//加粗
        font.setItalic(italic);//设置非斜体
        font.setUnderline(undderLine);//设置下划线
        return font;
    }

    /**
     * 创建水平和垂直居 的方法
     *
     * @param workbook
     * @return
     */
    public static HSSFCellStyle setRowCellCenter ( HSSFWorkbook workbook ,boolean isRed) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        HSSFFont font = setFontCellStyle(workbook , ( short ) 14 , isRed ? HSSFColor.HSSFColorPredefined.RED.getIndex() : HSSFColor.HSSFColorPredefined.BLACK.getIndex(), false , false , HSSFFont.U_NONE);
        cellStyle.setFont(font);
        return cellStyle;
    }
}


