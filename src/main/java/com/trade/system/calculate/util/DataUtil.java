package com.trade.system.calculate.util;

import com.trade.system.calculate.element.BaseInformation;
import com.trade.system.calculate.element.CapitalStock;
import com.trade.system.calculate.element.QuarterData;
import com.trade.system.calculate.element.QuarterDownloadData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description 抓取数据工具类
 * Created by putao on  2019/9/16 18:34
 **/
public class DataUtil {

    /**
    *  获取季度数据
    **/
    public static List<QuarterData> getQuarterData( String stockCode){
        String url = "http://emweb.securities.eastmoney.com/NewFinanceAnalysis/MainTargetAjax?type=2&code=" + stockCode;
        Map<String,String> header = new HashMap<>();
        header.put("Accept","*/*");
        Document doc = OkhttpUtils.get( url , header);
        String text = doc.body().text();
        List<QuarterDownloadData> quarterData = JsonUtils.parseArray(text , QuarterDownloadData.class);
        return quarterData.stream().map(QuarterData::buildByDownloadData).collect(Collectors.toList());
    }

    /**
    *  获取总股本
    **/
    public static CapitalStock getCapitalStock( String stockCode){
        String url = "http://emweb.securities.eastmoney.com/OperationsRequired/OperationsRequiredAjax?times=1&code="+stockCode+"&code=" + stockCode;
        Map<String,String> header = new HashMap<>();
        header.put("Accept","*/*");
        Document doc = OkhttpUtils.get(url , header);
        String text = doc.body().text();
        Map map = JsonUtils.parseObject(text , Map.class);
        Document zxzb1 = Jsoup.parse(String.valueOf(map.get("zxzb1")));
        Elements trs = zxzb1.select("tr");
        Element element = trs.get(2);
        Element stockElement = element.children().get(5);
        String stockStr = stockElement.text().replaceAll("," , "");
        return CapitalStock.build(new BigDecimal(stockStr).divide(BigDecimal.valueOf(10000),10,BigDecimal.ROUND_HALF_UP).toString());
    }

    /**
    *  获取市盈率
    **/
    public static BaseInformation getBaseInformation ( String stockCode){
        String code = stockCode.substring(2);
        String secid = code.startsWith("6") ? "1." + code : "0." + code;
        String timeStamp = String.valueOf(DateUtils.currentTimeMillis());
        String url = "http://push2.eastmoney.com/api/qt/stock/get?ut=fa5fd1943c7b386f172d6893dbfba10b&fltt=2&invt=2&fields=f120,f121,f122,f174,f175,f59,f163,f43,f57,f58,f169,f170,f46,f44,f51,f168,f47,f164,f116,f60,f45,f52,f50,f48,f167,f117,f71,f161,f49,f530,f135,f136,f137,f138,f139,f141,f142,f144,f145,f147,f148,f140,f143,f146,f149,f55,f62,f162,f92,f173,f104,f105,f84,f85,f183,f184,f185,f186,f187,f188,f189,f190,f191,f192,f107,f111,f86,f177,f78,f110,f262,f263,f264,f267,f268,f255,f256,f257,f258,f127,f199,f128,f198,f259,f260,f261,f171,f277,f278,f279,f288,f152,f250,f251,f252,f253,f254,f269,f270,f271,f272,f273,f274,f275,f276,f265,f266,f289,f290,f286,f285&secid="+secid+"&cb=jQuery112302564520756647981_"+timeStamp+"&_="+timeStamp;
        Map<String,String> header = new HashMap<>();
        header.put("Accept","*/*");
        Document doc = OkhttpUtils.get(url , header);
        String text = doc.body().text();
        String data = text.substring(text.indexOf("(") + 1 , text.indexOf(")"));
        Map dataMap = JsonUtils.parseObject(JsonUtils.parseObject(data , Map.class).get("data").toString(),Map.class);
        double staticPE = Double.valueOf(dataMap.get("f163").toString());
        double dynamicPE = Double.valueOf(dataMap.get("f162").toString());
        double currentStockPrice = Double.valueOf(dataMap.get("f43").toString());
        String stockName = dataMap.get("f58").toString();
        String industry = dataMap.get("f127").toString();

        return BaseInformation.build(staticPE,dynamicPE,currentStockPrice,stockCode,stockName,industry);
    }
}
