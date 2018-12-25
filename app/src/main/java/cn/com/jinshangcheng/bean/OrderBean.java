package cn.com.jinshangcheng.bean;

import java.util.List;

public class OrderBean extends BaseBean<OrderBean> {

    /**
     * orderid : 9A4A279F656D40839FC5D99003A302DB
     * ordertime : 1543313620000
     * updatetime : null
     * total : 1390
     * status : 0
     * address : 张剑,13436641844,北京市海淀区中关村,融科资讯中心c座北楼12层
     * isdel : false
     * isextract : 0
     * userid : 49
     * ordertype : 1
     * waybillnumber : null
     * waybillcompany : null
     * other1 : null
     * other2 : null
     * paycode : 08492126
     */
    private String orderid;
    private long ordertime;
    private Object updatetime;
    private double total;
    private int status;
    private String address;
    private boolean isdel;
    private int isextract;
    private int userid;
    private int ordertype;
    private Object waybillnumber;
    private Object waybillcompany;
    private String paycode;
    private List<Goods> orderitems;

    public List<Goods> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<Goods> orderitems) {
        this.orderitems = orderitems;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public long getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(long ordertime) {
        this.ordertime = ordertime;
    }

    public Object getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Object updatetime) {
        this.updatetime = updatetime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIsdel() {
        return isdel;
    }

    public void setIsdel(boolean isdel) {
        this.isdel = isdel;
    }

    public int getIsextract() {
        return isextract;
    }

    public void setIsextract(int isextract) {
        this.isextract = isextract;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(int ordertype) {
        this.ordertype = ordertype;
    }

    public Object getWaybillnumber() {
        return waybillnumber;
    }

    public void setWaybillnumber(Object waybillnumber) {
        this.waybillnumber = waybillnumber;
    }

    public Object getWaybillcompany() {
        return waybillcompany;
    }

    public void setWaybillcompany(Object waybillcompany) {
        this.waybillcompany = waybillcompany;
    }

    public String getPaycode() {
        return paycode;
    }

    public void setPaycode(String paycode) {
        this.paycode = paycode;
    }
}
