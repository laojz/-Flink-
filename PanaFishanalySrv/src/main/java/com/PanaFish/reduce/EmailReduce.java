package com.PanaFish.reduce;

import com.PanaFish.entity.EmailInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

public class EmailReduce implements ReduceFunction<EmailInfo> {

    @Override
    public EmailInfo reduce(EmailInfo emailInfo, EmailInfo t1) throws Exception {
        String emailtype = emailInfo.getEmailtype();
        Long count1 = emailInfo.getCount();
        Long count2 = t1.getCount();

        EmailInfo emailInfofinal = new EmailInfo();
        emailInfofinal.setEmailtype(emailtype);
        emailInfofinal.setCount(count1+count2);
        return emailInfofinal;
    }
}
