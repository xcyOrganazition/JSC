package cn.com.jinshangcheng.bean;

/**
 * 车辆故障详情
 */
public class TroubleBean {

    /**
     * causeAnalysis : 可变进气控制是指发动机进气系统通过电磁阀控制挡板改变进气长度，以控制进气量。常见的原因：1.可变进气电磁阀损坏。2.可变进气电磁阀控制线路短路。3.进气歧管内的挡板或者进气机构机械系损坏。4.进气歧管总成损坏。
     * consequences : 可变进气控制一般情况不影响其正常启动。可能出现：急加速无力、进气系统异响、油耗增加、动力输出受限、启动有“咔咔”异响声、仪表故障灯点亮等现象。
     * dtc : P0026
     * dtcName : 可变进气电磁阀故障
     * grade : 0
     * knowledge : 凸轮轴的作用是控制气门的开启和闭合。可变气门正时系统中，电子凸轮轴调节阀（或机油控制阀，OCV）根据来自发动机控制模块（ECM）的指令通过改变到凸轮轴调节器（机械式）的机油压力的方式来调整凸轮轴的角度，以确保气门在最佳时间打开和关闭。如果进气凸轮轴的实际位置跟理想位置相差超过20度，该故障码会出现。
     * obdTime : 1535348431000
     * suggestion : 定期保养检查车辆，更换正规的机油，行驶一定里程定期检查清理积碳（一般3-4万公里建议清洗）。出现故障码时，如果仪表故障灯未亮（车辆无异常）时，建议先观察使用。
     * translationChinese : 进气阀门控制电磁阀电路范围/性能 （第1排）
     */

    public String causeAnalysis;
    public String consequences;
    public String dtc;
    public String dtcName;
    public String grade;
    public String knowledge;
    public String obdTime;
    public String suggestion;
    public String translationChinese;

}
