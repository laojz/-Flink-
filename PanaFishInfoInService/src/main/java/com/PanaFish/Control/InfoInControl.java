package com.PanaFish.Control;

import com.PanaFish.entity.ResultMessage;
import com.PanaFish.log.AttentionProductLog;
import com.PanaFish.log.BuyCartProductLog;
import com.PanaFish.log.CollectProductLog;
import com.PanaFish.log.ScanProductLog;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("infolog")

public class InfoInControl {

    @RequestMapping(value = "helloworld", method = RequestMethod.GET)
    public String helloworld(HttpServletRequest req){
        String ip = req.getRemoteAddr();
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setMessage("hello" + ip);
        resultMessage.setStatus("success");
        String result = JSONObject.toJSONString(resultMessage);
        return result;
    }

    /*
    AttentionProductLog:{productid:productid...}
    BuyCartProductLog:{productid:productid...}
    CollectProductLog:{productid:productid...}
    ScanProductLog:{productid:productid...}
     */
    @RequestMapping(value = "receivelog", method = RequestMethod.POST)
    public String helloworld(String receivelog, HttpServletRequest req){
        if(StringUtils.isBlank(receivelog)){
            return null;
        }
        String[] rearrays =  receivelog.split(":", 2);
        String classname = rearrays[0];
        String data = rearrays[1];
        String resultmessage = "";
        if("AttentionProductLog".equals(classname)){
            AttentionProductLog attentionProductLog = JSONObject.parseObject(data, AttentionProductLog.class);
            resultmessage = JSONObject.toJSONString(attentionProductLog);
        }else if("BuyCartProductLog".equals(classname)){
            BuyCartProductLog buyCartProductLog = JSONObject.parseObject(data, BuyCartProductLog.class);
            resultmessage = JSONObject.toJSONString(buyCartProductLog);
        }else if("CollectProductLog".equals(classname)){
            CollectProductLog collectProductLog = JSONObject.parseObject(data, CollectProductLog.class);
            resultmessage = JSONObject.toJSONString(collectProductLog);
        }else if("ScanProductLog".equals(classname)){
            ScanProductLog scanProductLog = JSONObject.parseObject(data, ScanProductLog.class);
            resultmessage = JSONObject.toJSONString(scanProductLog);
        }

        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setMessage(resultmessage);
        resultMessage.setStatus("success");
        String result = JSONObject.toJSONString(resultMessage);
        return result;
    }
}
