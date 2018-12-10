package cn.com.jinshangcheng.bean;

/**
 * 车辆数据
 */
public class CarBean extends BaseBean<CarBean> {

    //   `carid` char(32) NOT NULL,
    //   `openCarId` char(32) NOT NULL,/*开放平台用户车辆唯一标识*/
    //   `userid` int(4) default NULL,
    //   `plateNumber` varchar(50) default NULL,/*车牌*/
    //   `vin` varchar(50) default NULL, /*车架码(车辆识别代号)*/
    //   `ein` varchar(50) default NULL, /*发动机号码*/
    //   `carType` varchar(2) default NULL,/*车辆行驶证上车辆品牌型号，注：modelId和carType如果都填以modelId为准，二者至少填一个*/
    //   `tplate`int(4) default 0, /*1 是临时车牌 0正式车牌*/
    //   `gasno` int(11) default 92,/*请传数字：89,92,95,98,0.分别代表：89号（原90汽油），92号（原93）汽油，95号（原97）汽油，98号汽油，0号柴油。*/
    //   `carregistdate` date default NULL,/*车辆注册日期*/
    //   `sn` varchar(50) default NULL,    /*车机序列号*/
    //   `din` varchar(50) default NULL,  /*驾图盒子唯一识别码（车机号）*/
    //   `totalmileage` decimal(8,2) default NULL,/*行驶里程*/
    //   `mileage` decimal(8,2) default 0.0,/*插入盒子之后的总里程*/
    //   `fuel` decimal(8,2) default 0.0,/*插入盒子之后的总油耗/car/obd/query*/
    //   `insurancedeadline` date default NULL,/*保险日期*/
    //   `annualtrialdeadline` date default NULL, /*年审日期*/
    //   `brandName` varchar(200) default NULL, /*品牌名称*/
    //   `brandPath` varchar(200) default NULL, /*品牌图片*/
    //   `typeName` varchar(200) default NULL, /*车型名称*/
    //   `typePath` varchar(200) default NULL, /*车型图片路径*/
    //   `model` varchar(200) default NULL, /*车款名称*/
    //   `modelPath` varchar(200) default NULL, /*车款图片路径*/
    //   `isdel` varchar(1) default NULL, /*车辆是否删除 0:已删除，1：未删除*/
    //   `registdate` datetime default NULL,     /*注册时间*/
    //   `updatedate` datetime default NULL,     /*更新时间*/
    //   `emergencyphonenum` varchar(50) default NULL, /*紧急联系电话*/

    private String carid;
    private String opencarid;
    private String userid;
    private String platenumber;
    private String vin;
    private String ein;
    private String cartype;
    private int tplate;
    private int gasno;
    private long carregistdate;
    private String sn;
    private String din;
    private String totalmileage;
    private String mileage;
    private String fuel;
    private long insurancedeadline;
    private long annualtrialdeadline;
    private String brandname;
    private String brandpath;
    private String typename;
    private String typepath;
    private String model;
    private String modelpath;
    private String isdel;
    private long registdate;
    private long updatedate;
    private String emergencyphonenum;

    @Override
    public String toString() {
        return "CarBean{" +
                "carid='" + carid + '\'' +
                ", opencarid='" + opencarid + '\'' +
                ", userid='" + userid + '\'' +
                ", platenumber='" + platenumber + '\'' +
                ", vin='" + vin + '\'' +
                ", ein='" + ein + '\'' +
                ", cartype='" + cartype + '\'' +
                ", tplate=" + tplate +
                ", gasno=" + gasno +
                ", carregistdate=" + carregistdate +
                ", sn='" + sn + '\'' +
                ", din='" + din + '\'' +
                ", totalmileage='" + totalmileage + '\'' +
                ", mileage='" + mileage + '\'' +
                ", fuel='" + fuel + '\'' +
                ", insurancedeadline=" + insurancedeadline +
                ", annualtrialdeadline=" + annualtrialdeadline +
                ", brandname='" + brandname + '\'' +
                ", brandpath='" + brandpath + '\'' +
                ", typename='" + typename + '\'' +
                ", typepath='" + typepath + '\'' +
                ", model='" + model + '\'' +
                ", modelpath='" + modelpath + '\'' +
                ", isdel='" + isdel + '\'' +
                ", registdate=" + registdate +
                ", updatedate=" + updatedate +
                '}';
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getOpencarid() {
        return opencarid;
    }

    public void setOpencarid(String opencarid) {
        this.opencarid = opencarid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPlatenumber() {
        return platenumber;
    }

    public void setPlatenumber(String platenumber) {
        this.platenumber = platenumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEin() {
        return ein;
    }

    public void setEin(String ein) {
        this.ein = ein;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public int getTplate() {
        return tplate;
    }

    public void setTplate(int tplate) {
        this.tplate = tplate;
    }

    public int getGasno() {
        return gasno;
    }

    public void setGasno(int gasno) {
        this.gasno = gasno;
    }

    public long getCarregistdate() {
        return carregistdate;
    }

    public void setCarregistdate(long carregistdate) {
        this.carregistdate = carregistdate;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDin() {
        return din;
    }

    public void setDin(String din) {
        this.din = din;
    }

    public String getTotalmileage() {
        return totalmileage;
    }

    public void setTotalmileage(String totalmileage) {
        this.totalmileage = totalmileage;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public long getInsurancedeadline() {
        return insurancedeadline;
    }

    public void setInsurancedeadline(long insurancedeadline) {
        this.insurancedeadline = insurancedeadline;
    }

    public long getAnnualtrialdeadline() {
        return annualtrialdeadline;
    }

    public void setAnnualtrialdeadline(long annualtrialdeadline) {
        this.annualtrialdeadline = annualtrialdeadline;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getBrandpath() {
        return brandpath;
    }

    public void setBrandpath(String brandpath) {
        this.brandpath = brandpath;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypepath() {
        return typepath;
    }

    public void setTypepath(String typepath) {
        this.typepath = typepath;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelpath() {
        return modelpath;
    }

    public void setModelpath(String modelpath) {
        this.modelpath = modelpath;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public long getRegistdate() {
        return registdate;
    }

    public void setRegistdate(long registdate) {
        this.registdate = registdate;
    }

    public long getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(long updatedate) {
        this.updatedate = updatedate;
    }

    public String getEmergencyphonenum() {
        return emergencyphonenum;
    }

    public void setEmergencyphonenum(String emergencyphonenum) {
        this.emergencyphonenum = emergencyphonenum;
    }
}
