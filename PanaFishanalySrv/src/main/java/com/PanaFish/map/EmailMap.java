package com.PanaFish.map;

import com.PanaFish.entity.EmailInfo;
import com.PanaFish.util.EmailUtils;
import com.PanaFish.util.HbaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

public class EmailMap implements MapFunction<String, EmailInfo> {
    @Override
    public EmailInfo map(String s) throws Exception{
        if(StringUtils.isBlank(s)){
            return null;
        }
        String[] userinfos = s.split(",");
        String userid = userinfos[0];
        String username = userinfos[1];
        String sex = userinfos[2];
        String telphone = userinfos[3];
        String email = userinfos[4];
        String age = userinfos[5];
        String registerTime = userinfos[6];
        String usertype = userinfos[7]; //终端类型 0，pc；1，移动端；2，小程序

        String emailtype = EmailUtils.getEmailtypeBy(email);

        String tablename = "userflaginfo";
        String rowkey = userid;
        String famliyname = "baseinfo";
        String colum = "emailinfo";//邮箱
        //String tablename, String rowkey, String famliyname,String colum,String data
        HbaseUtils.putdata(tablename, rowkey, famliyname, colum, emailtype);
        EmailInfo emailInfo = new EmailInfo();
        String groupfield = "emailInfo=="+emailtype;
        emailInfo.setCount(1l);
        emailInfo.setEmailtype(emailtype);
        emailInfo.setGroupfield(groupfield);
        return emailInfo;
    }
}
