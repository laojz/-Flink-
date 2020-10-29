package com.PanaFish.log;

public class AttentionProductLog {
    private int productid;
    private int producttypeid;
    private String opertortime;//操作时间
    private int opertortype;//操作类型 0，关注 1，取消
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

    public String getOpertortime() {
        return opertortime;
    }

    public void setOpertortime(String opertortime) {
        this.opertortime = opertortime;
    }

    public int getOpertortype() {
        return opertortype;
    }

    public void setOpertortype(int opertortype) {
        this.opertortype = opertortype;
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
