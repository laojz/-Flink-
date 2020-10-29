package com.PanaFish.log;

public class BuyCartProductLog {
    private int productid;
    private int producttypeid;
    private String operatortime;
    private int operatortype;//操作类型 0，加入 1，删除
    private int userid;
    private int usertype;//终端类型 0，pc；1，移动端；2，小程序
    private String ip;//用户ip

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getProducttypeid() {
        return producttypeid;
    }

    public void setProducttypeid(int producttypeid) {
        this.producttypeid = producttypeid;
    }

    public String getOperatortime() {
        return operatortime;
    }

    public void setOperatortime(String operatortime) {
        this.operatortime = operatortime;
    }

    public int getOperatortype() {
        return operatortype;
    }

    public void setOperatortype(int operatortype) {
        this.operatortype = operatortype;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
