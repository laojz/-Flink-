package com.PanaFish.task;

import com.PanaFish.entity.EmailInfo;
import com.PanaFish.map.EmailMap;
import com.PanaFish.reduce.EmailReduce;
import com.PanaFish.util.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

public class EmailTask {
    public static void main(String[] args){
        final ParameterTool params = ParameterTool.fromArgs(args);

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<EmailInfo> mapresult = text.map(new EmailMap());
        DataSet<EmailInfo> reduceresutl = mapresult.groupBy("groupfield").reduce(new EmailReduce());
        try {
            List<EmailInfo> resultlist = reduceresutl.collect();
            for(EmailInfo emailInfo:resultlist){
                String emailtype = emailInfo.getEmailtype();
                Long count = emailInfo.getCount();

                Document doc = MongoUtils.findoneby("emailstatics", "PanaFishPortrait", emailtype);
                if(doc == null){
                    doc = new Document();
                    doc.put("info", emailtype);
                    doc.put("count", count);
                } else {
                    Long countpre = doc.getLong("count");
                    Long total = countpre+count;
                    doc.put("count", total);
                }
                MongoUtils.saveorupdatemongo("emailstatics", "PanaFishPortrait", doc);
            }
            env.execute("email analy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
