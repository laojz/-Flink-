package com.PanaFish.task;

import com.PanaFish.entity.CarrierInfo;
import com.PanaFish.map.CarrierMap;
import com.PanaFish.reduce.CarrierReduce;
import com.PanaFish.util.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

public class CarrierTask {
    public static void main(String[] args){
        final ParameterTool params = ParameterTool.fromArgs(args);

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<CarrierInfo> mapresult = text.map(new CarrierMap());
        DataSet<CarrierInfo> reduceresutl = mapresult.groupBy("groupfield").reduce(new CarrierReduce());
        try {
            List<CarrierInfo> resultlist = reduceresutl.collect();
            for(CarrierInfo carrierInfo:resultlist){
                String carrier = carrierInfo.getCarrier();
                Long count = carrierInfo.getCount();

                Document doc = MongoUtils.findoneby("carrierstatics", "PanaFishPortrait", carrier);
                if(doc == null){
                    doc = new Document();
                    doc.put("info", carrier);
                    doc.put("count", count);
                } else {
                    Long countpre = doc.getLong("count");
                    Long total = countpre+count;
                    doc.put("count", total);
                }
                MongoUtils.saveorupdatemongo("carrierstatics", "PanaFishPortrait", doc);
            }
            env.execute("carrier analy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
