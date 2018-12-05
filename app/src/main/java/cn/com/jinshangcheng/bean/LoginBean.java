package cn.com.jinshangcheng.bean;


public class LoginBean {

    public String code;// 登录名 ,
    public String message;// message ,
    public String userid;//登陆人ID ,

    @Override
    public String toString() {
        return "LoginBean{" +
                ", userId='" + userid + '\'' +
                '}';
    }
}