package cn.com.jinshangcheng.bean;

/**
 * 用车报告
 */
public class ReportBean extends BaseBean {



    /* `date` varchar(10)  default NULL,*//*开车日期，格式：yyyyMMdd*//*
            `mile` double(5,2)  default NULL,*//*当天车辆驾驶里程*//*
            `duration` bigint(8)  default NULL,*//*当天车辆驾驶时长,单位：秒(S)*//*
            `accelerateTimes` int(4)  default 0, *//*当天车辆急加速次数*//*
            `dccelerateTimes` int(4)  default 0, *//*当天车辆急减速次数*//*
            `sharpTurnTimes` int(4)  default 0, *//*当天车辆急转弯次数*//*
            `fireTimes` int(4)  default 0, *//*车辆当天点火次数*//*
            `hasFatigueDriving` int(4)  default NULL,*//*0：有疲劳驾驶，1：没有疲劳驾驶*//*
            `hasDrivingOnLight` int(4)  default NULL,*//*0：否，1：是*//*
            `averageSpeed` double(5,2)  default 0.0, *//*当天平均车速*//*
            `maxSpeed` double(5,2)  default 0.0, *//*当天最大车速*//*
            `offLineTimes` int(4)  default NULL, *//*0：否，1：是*//*
            `isPulled` int(4)  default NULL, *//*0：否，1：是*//*
            `isOnMachine` float(8,2)  default NULL,*//*车机插在工装上概率*//*
            `isOnOthers` float(8,2)  default NULL,*//*车机插在另一辆车上概率*//*
            `fuel` DOUBLE(8,2)  default 0.0,  *//*当天总油耗*//*
            `fuelcost` DOUBLE(8,2)  default 0.0,  *//*当天总油费*//*
            `hasOverSpeed` int(8)  default 0,*//*当天超速次数*//*
            `registdate` datetime default NULL,     *//*注册时间*//*
            `updatedate` datetime default NULL,     *//*更新时间*/


    public String cardatereportid;
    public String carid;
    public String date;
    public String mile;
    public long duration;
    public String acceleratetimes;
    public String dcceleratetimes;
    public String sharpturntimes;
    public String firetimes;
    public String hasfatiguedriving;
    public String hasdrivingonlight;
    public String averagespeed;
    public String maxspeed;
    public String offlinetimes;
    public String ispulled;
    public String isonmachine;
    public String isonothers;
    public String fuel;
    public String fuelcost;
    public String hasoverspeed;
    public String registdate;
    public String updatedate;
    public String other1;
    public String other2;
    public String averagefuel;
    public String mileage;


}
