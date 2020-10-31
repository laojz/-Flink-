package com.PanaFish.reduce;

import com.PanaFish.entity.UserTypeInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

public class UserTypeReduce implements ReduceFunction<UserTypeInfo> {
    @Override
    public UserTypeInfo reduce(UserTypeInfo userTypeInfo, UserTypeInfo t1) throws Exception {
        String brand = userTypeInfo.getUsertype();
        long count1 = userTypeInfo.getCount();
        long count2 = t1.getCount();
        UserTypeInfo userTypeInfofinal = new UserTypeInfo();
        userTypeInfofinal.setUsertype(brand);
        userTypeInfofinal.setCount(count1+count2);
        return userTypeInfofinal;
    }
}
