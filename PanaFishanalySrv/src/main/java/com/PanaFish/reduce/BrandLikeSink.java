package com.PanaFish.reduce;

import com.PanaFish.entity.BrandLike;
import com.PanaFish.util.MongoUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.bson.Document;

public class BrandLikeSink implements SinkFunction<BrandLike> {
    @Override
    public void invoke(BrandLike value, Context context) throws Exception {
        String brand = value.getBrand();
        long count = value.getCount();
        Document doc = MongoUtils.findoneby("brandlikestatics","PanaFishPortrait",brand);
        if(doc == null){
            doc = new Document();
            doc.put("info",brand);
            doc.put("count",count);
        }else{
            Long countpre = doc.getLong("count");
            Long total = countpre+count;
            doc.put("count",total);
        }
        MongoUtils.saveorupdatemongo("brandlikestatics","PanaFishPortrait",doc);
    }
}
