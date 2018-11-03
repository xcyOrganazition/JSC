package cn.com.jinshangcheng.bean;

import java.util.List;

public class OrderBean extends BaseBean<OrderBean> {

    /**
     * orderid : 940FE5E009364150B88FFA7114FBDBA2
     * ordertime : 1537855228000
     * total : 2840.0
     * status : 0
     * address : 李四丝丝,18810609610,北京市海淀区,百度大厦19层
     * isdel : false
     * userid : 1
     * orderitems : []
     */

    private String orderid;
    private long ordertime;
    private double total;
    private int status;
    private String address;
    private boolean isdel;
    private String userid;
    private List<Goods> orderitems;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<Goods> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<Goods> orderitems) {
        this.orderitems = orderitems;
    }
}
