package cn.com.jinshangcheng.bean;

/**
 * 车辆数据
 */
public class CarBean extends BaseBean<CarBean> {


    /**
     * carid : 1648A38F4E0F4E7DACD87F105D4E3D36
     * opencarid : 9784n28997271692K75UGUGUU
     * userid : 1
     * platenumber : 冀B12313
     * vin : LGBH52E01CY035590
     * ein : B4204T61007236
     * cartype : 0
     * tplate : 0
     * gasno : 98
     * carregistdate : null
     * sn : null
     * din : null
     * totalmileage : null
     * mileage : null
     * fuel : null
     * insurancedeadline : null
     * annualtrialdeadline : null
     * brandname : abc
     * brandpath : /pic/go.png
     * typename : aaa
     * typepath : /sdf/sdf.png
     * model : 保时捷918 2014款 Spyder Weissach package 4.6L
     * modelpath : http://file.kartor.cn/resize/image/carType/20151228/201512280950480209.jpg
     * isdel : 1
     * registdate : 1537864180000
     * updatedate : null
     * other1 : null
     * other2 : null
     */

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
}
