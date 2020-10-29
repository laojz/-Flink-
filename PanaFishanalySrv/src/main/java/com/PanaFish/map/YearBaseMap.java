package com.PanaFish.map;

import com.PanaFish.entity.YearBase;
import com.PanaFish.util.DateUtils;
import com.PanaFish.util.HbaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.time.Year;

public class YearBaseMap implements MapFunction<String, YearBase> {
    @Override
    public YearBase map(String s) throws Exception{
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

        String yearbasetype = DateUtils.getYearbasebyAge(age);
        String tablename = "userflaginfo";
        String rowkey = userid;
        String famliyname = "baseinfo";
        String colum = "yearbase";//年代
        //String tablename, String rowkey, String famliyname,String colum,String data
        HbaseUtils.putdata(tablename, rowkey, famliyname, colum, yearbasetype);
        YearBase yearBase = new YearBase();
        String groupfield = "yearbase=="+yearbasetype;
        yearBase.setCount(1l);
        yearBase.setYeartype(yearbasetype);
        yearBase.setGroupfield(groupfield);
        return yearBase;
    }
}
