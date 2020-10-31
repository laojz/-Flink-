package com.PanaFish.map;

import com.PanaFish.entity.UserTypeInfo;
import com.PanaFish.kafka.KafkaEvent;
import com.PanaFish.log.ScanProductLog;
import com.PanaFish.util.HbaseUtils;
import com.PanaFish.utils.MapUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Map;

public class UserTypeMap implements FlatMapFunction<KafkaEvent, UserTypeInfo>  {

    @Override
    public void flatMap(KafkaEvent kafkaEvent, Collector<UserTypeInfo> collector) throws Exception {
        String data = kafkaEvent.getWord();
        ScanProductLog scanProductLog = JSONObject.parseObject(data,ScanProductLog.class);
        int userid = scanProductLog.getUserid();
        int usetype = scanProductLog.getUsertype();////终端类型：0、pc端；1、移动端；2、小程序端
        String usetypename = usetype == 0?"pc端":usetype == 1?"移动端":"小程序端";
        String tablename = "userflaginfo";
        String rowkey = userid+"";
        String famliyname = "userbehavior";
        String colum = "usertypelist";//运营
        String mapdata = HbaseUtils.getdata(tablename,rowkey,famliyname,colum);
        Map<String,Long> map = new HashMap<String,Long>();
        if(StringUtils.isNotBlank(mapdata)){
            map = JSONObject.parseObject(mapdata,Map.class);
        }
        //获取之前的终端偏好
        String maxpreusertype = MapUtils.getmaxbyMap(map);

        long preusetype = map.get(usetypename)==null?0l:map.get(usetypename);
        map.put(usetypename,preusetype+1);
        String finalstring = JSONObject.toJSONString(map);
        HbaseUtils.putdata(tablename,rowkey,famliyname,colum,finalstring);

        String maxusertype = MapUtils.getmaxbyMap(map);
        if(StringUtils.isNotBlank(maxusertype)&&!maxpreusertype.equals(maxusertype)){
            UserTypeInfo userTypeInfo = new UserTypeInfo();
            userTypeInfo.setUsertype(maxpreusertype);
            userTypeInfo.setCount(-1l);
            userTypeInfo.setGroupfield("==usertypeinfo=="+maxpreusertype);
            collector.collect(userTypeInfo);
        }

        UserTypeInfo userTypeInfo = new UserTypeInfo();
        userTypeInfo.setUsertype(maxusertype);
        userTypeInfo.setCount(1l);
        userTypeInfo.setGroupfield("==usertypeinfo=="+maxusertype);
        collector.collect(userTypeInfo);
        colum = "usertype";
        HbaseUtils.putdata(tablename,rowkey,famliyname,colum,maxusertype);

    }

}