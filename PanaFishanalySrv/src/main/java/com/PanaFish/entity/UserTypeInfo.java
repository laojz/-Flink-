package com.PanaFish.entity;

public class UserTypeInfo {
    private String usertype;//运营商
    private Long count;//数量
    private String groupfield;//分组

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getGroupfield() {
        return groupfield;
    }

    public void setGroupfield(String groupfield) {
        this.groupfield = groupfield;
    }

    public String getUsertype() {

        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
