package com.jinshangcheng.bean;


public class LoginBean extends BaseBean<LoginBean> {

    public String loginName;// 登录名 ,
    public String token;// 登陆token ,
    public String userId;//登陆人ID ,
    public String username;//用户名
    public String tokenCode;//用户名

    @Override
    public String toString() {
        return "LoginBean{" +
                "loginName='" + loginName + '\'' +
                ", token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}