package com.PanaFish.task;

import com.PanaFish.entity.KeyWordEntity;
import com.PanaFish.entity.SexPreInfo;
import com.PanaFish.map.KeywordMap;
import com.PanaFish.map.KeywordMap2;
import com.PanaFish.map.SexPreMap;
import com.PanaFish.map.SexPresaveMap;
import com.PanaFish.reduce.KeyWordReduce2;
import com.PanaFish.reduce.KeywordReduce;
import com.PanaFish.reduce.SexpreReduce;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

import java.util.*;


public class MonthKeyWordTask {
    public static void main(String[] args) {
        final ParameterTool params = ParameterTool.fromArgs(args);

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);

        // get input data
        DataSet<String> text = env.readTextFile(params.get("input"));

        DataSet<KeyWordEntity> mapresult = text.map(new KeywordMap());
        DataSet<KeyWordEntity> reduceresutl = mapresult.groupBy("userid").reduce(new KeywordReduce());
        DataSet<KeyWordEntity> mapresult2 = reduceresutl.map(new KeywordMap2());
        DataSet<KeyWordEntity> reduceresult2 = mapresult2.reduce(new KeyWordReduce2());
        Long totaldoucment = 0l;
        try {
            totaldoucment = reduceresult2.collect().get(0).getTotaldocumet();
            DataSet<KeyWordEntity> mapfinalresult = mapresult.map(new KeyWordMapfinal(totaldoucment,3,"month"));
            mapfinalresult.writeAsText("hdfs://PanaFish/test/month");//hdfs的路径
            env.execute("MonthrKeyWordTask analy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
