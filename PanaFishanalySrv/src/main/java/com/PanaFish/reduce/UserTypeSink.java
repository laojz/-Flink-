package com.PanaFish.reduce;

import com.PanaFish.entity.UserTypeInfo;
import com.PanaFish.util.MongoUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.bson.Document;

public class UserTypeSink implements SinkFunction<UserTypeInfo> {
    @Override
    public void invoke(UserTypeInfo value, Context context) throws Exception {
        String usertype = value.getUsertype();
        long count = value.getCount();
        Document doc = MongoUtils.findoneby("usertypestatics","PanaFishPortrait",usertype);
        if(doc == null){
            doc = new Document();
            doc.put("info",usertype);
            doc.put("count",count);
        }else{
            Long countpre = doc.getLong("count");
            Long total = countpre+count;
            doc.put("count",total);
        }
        MongoUtils.saveorupdatemongo("usertypestatics","PanaFishPortrait",doc);
    }
}
