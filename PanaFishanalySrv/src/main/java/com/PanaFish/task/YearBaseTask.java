package com.PanaFish.task;

import com.PanaFish.entity.YearBase;
import com.PanaFish.map.YearBaseMap;
import com.PanaFish.reduce.YearBaseReduce;
import com.PanaFish.util.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.lang.reflect.Parameter;
import java.util.List;

public class YearBaseTask {
    public static void main(String[] args){
        final ParameterTool params = ParameterTool.fromArgs(args);

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<YearBase> mapresult = text.map(new YearBaseMap());
        DataSet<YearBase> reduceresutl = mapresult.groupBy("groupfield").reduce(new YearBaseReduce());
        try {
            List<YearBase> resultlist = reduceresutl.collect();
            for(YearBase yearBase:resultlist){
                String yeartype = yearBase.getYeartype();
                Long count = yearBase.getCount();

                Document doc = MongoUtils.findoneby("yearbasestatics", "PanaFishPortrait", yeartype);
                if(doc == null){
                    doc = new Document();
                    doc.put("info", yeartype);
                    doc.put("count", count);
                } else {
                    Long countpre = doc.getLong("count");
                    Long total = countpre+count;
                    doc.put("count", total);
                }
                MongoUtils.saveorupdatemongo("yearbasestatics", "PanaFishPortrait", doc);
            }
            env.execute("year base");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
