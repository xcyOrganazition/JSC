package cn.com.jinshangcheng.bean;

public class UserBean extends BaseBean {

//          `userid` int(8) NOT NULL auto_increment,              /*用户id*/
//`             name` varchar(50) default NULL,		/*姓名*/
//            `openId` varchar(50) default NULL,      /*驾图盒子id*/
//            `accid` varchar(50) default NULL,       /*网易云信id*/
//            `token` varchar(50) default NULL,       /*网易云信登录密码*/
//            `weixinname` varchar(50) default NULL,  /*微信昵称*/
//            `weixinid` varchar(50) default NULL,    /*微信id*/
//            `weixinpic` varchar(500) default NULL,   /*微信头像*/
//            `phoneNumber` varchar(50) default NULL, /*手机号*/
//            `registdate` datetime default NULL,     /*注册时间*/
//            `updatedate` datetime default NULL,     /*更新时间*/
//            `QRcodepath` varchar(500) default NULL,  /*二维码地址*/
//            `parentid` int(4) default NULL,         /*父id*/
//            `userlevel` int(4) default 0,        /*用户等级*/
//            `teamnum`   int(8) default 0,        /*团队人数*/
//            `straightpush` int(8) default 0,        /*直推数量*/
//            `travelprotect` int(1) default 0,      /*2018-10-29：是否轨迹保护 0 不保护，1 保护 */
//            `userclient` int(4) default NULL, 		/*用户来源 0 微信公众号  1  Android   2  ios   3 其他*/
//            `province` varchar(50) default NULL,  /*2018-10-29：省*/
//            `city` varchar(50) default NULL,  /*2018-10-29：市*/

    public String userid;
    public String name;
    public String openid;
    public String accid;
    public String token;
    public String weixinname;
    public String weixinid;
    public String weixinpic;
    public String phonenumber;
    public long registdate;
    public long updatedate;
    public String qrcodepath;
    public String parentid;
    public String province;
    public String city;
    public int teamnum;
    public int straightpush;
    public int travelprotect;/*2018-10-29：是否轨迹保护 0 不保护，1 保护 */
    public int userclient;/*用户来源 0 微信公众号  1  Android   2  ios   3 其他*/
    public int userlevel;


    @Override
    public String toString() {
        return "UserBean{" +
                "userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", openid='" + openid + '\'' +
                ", accid='" + accid + '\'' +
                ", token='" + token + '\'' +
                ", weixinname='" + weixinname + '\'' +
                ", weixinid='" + weixinid + '\'' +
                ", weixinpic='" + weixinpic + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", registdate=" + registdate +
                ", updatedate=" + updatedate +
                ", qrcodepath='" + qrcodepath + '\'' +
                ", parentid='" + parentid + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", teamnum=" + teamnum +
                ", straightpush=" + straightpush +
                ", travelprotect=" + travelprotect +
                ", userclient=" + userclient +
                ", userlevel=" + userlevel +
                '}';
    }
}
