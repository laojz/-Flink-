package com.PanaFish.map;

import com.alibaba.fastjson.JSONObject;
import com.PanaFish.entity.ChaomanAndWomenInfo;
import com.PanaFish.entity.UserTypeInfo;
import com.PanaFish.kafka.KafkaEvent;
import com.PanaFish.log.ScanProductLog;
import com.PanaFish.util.HbaseUtils;
import com.PanaFish.utils.MapUtils;
import com.PanaFish.utils.ReadProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by li on 2019/1/6.
 */
public class ChaomanAndwomenMap implements FlatMapFunction<KafkaEvent,ChaomanAndWomenInfo>  {

    @Override
    public void flatMap(KafkaEvent kafkaEvent, Collector<ChaomanAndWomenInfo> collector) throws Exception {
            String data = kafkaEvent.getWord();
            ScanProductLog scanProductLog = JSONObject.parseObject(data,ScanProductLog.class);
            int userid = scanProductLog.getUserid();
            int productid = scanProductLog.getProductid();
            ChaomanAndWomenInfo chaomanAndWomenInfo = new ChaomanAndWomenInfo();
            chaomanAndWomenInfo.setUserid(userid+"");
            String chaotype = ReadProperties.getKey(productid+"","productChaoLiudic.properties");
            if(StringUtils.isNotBlank(chaotype)){
                chaomanAndWomenInfo.setChaotype(chaotype);
                chaomanAndWomenInfo.setCount(1l);
                chaomanAndWomenInfo.setGroupbyfield("chaomanAndWomen=="+userid);
                List<ChaomanAndWomenInfo> list = new ArrayList<ChaomanAndWomenInfo>();
                list.add(chaomanAndWomenInfo);
                collector.collect(chaomanAndWomenInfo);
            }

    }

}
