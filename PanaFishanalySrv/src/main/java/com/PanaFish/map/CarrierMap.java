package com.PanaFish.map;

import com.PanaFish.entity.CarrierInfo;
import com.PanaFish.entity.YearBase;
import com.PanaFish.util.CarrierUtils;
import com.PanaFish.util.DateUtils;
import com.PanaFish.util.HbaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

public class CarrierMap implements MapFunction<String, CarrierInfo> {
    @Override
    public CarrierInfo map(String s) throws Exception{
        if(StringUtils.isBlank(s)){
            return null;
        }
        String[] userinfos = s.split(",");
        String userid = userinfos[0];
        String username = userinfos[1];
        String sex = userinfos[2];
        String telphone = userinfos[3];
        String email = userinfos[4];
        String age = userinfos[5];
        String registerTime = userinfos[6];
        String usertype = userinfos[7]; //终端类型 0，pc；1，移动端；2，小程序

        int carriertype = CarrierUtils.getCarrierByTel(telphone);
        String carriertypestring = carriertype==0 ? "未知运营商": carriertype==1 ? "移动用户": carriertype==2 ?
                "联通用户": "电信用户";

        String tablename = "userflaginfo";
        String rowkey = userid;
        String famliyname = "baseinfo";
        String colum = "carrierinfo";//运营商
        //String tablename, String rowkey, String famliyname,String colum,String data
        HbaseUtils.putdata(tablename, rowkey, famliyname, colum, carriertypestring);
        CarrierInfo carrierInfo = new CarrierInfo();
        String groupfield = "carrierInfo=="+carriertype;
        carrierInfo.setCount(1l);
        carrierInfo.setCarrier(carriertypestring);
        carrierInfo.setGroupfield(groupfield);
        return carrierInfo;
    }
}
