package com.stock.putaostock.element;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Description 股本
 * Created by putao on  2019/9/16 11:51
 **/
@Data
@AllArgsConstructor
public class CapitalStock implements Serializable {
    //总股本
    private double capitalStock;

    public static CapitalStock build(String capitalStock){
        return new CapitalStock(Double.parseDouble(capitalStock));
    }
}
