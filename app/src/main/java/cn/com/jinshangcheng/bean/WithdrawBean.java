package cn.com.jinshangcheng.bean;

import java.io.Serializable;

public class WithdrawBean implements Serializable {

    /**
     * detaileid : 1DD77FCCFDC94C4FB5CDF3D7F3ACA058
     * userid : 1
     * accountid : null
     * dealbalance : -14.0
     * registtime : 1539054541000
     * dealtype : 2
     * orderid : 949FE5E009364150B88FFA7114FBDBA3
     * updatetime : 1539054541000
     * other1 : null
     * other2 : null
     * orderitem : 8DFC52943B9141A48993FDA609E59E2C
     */

    //registtime(时间)，dealtype(处理类型)，dealbalance（金额）
    private String detaileid;
    private String userid;
    private Object accountid;
    private double dealbalance;
    private long registtime;
    private int dealtype;// 0直推奖 1级差奖 2.已申请提现 3.已打款 4已退款
    private String orderid;
    private long updatetime;

    public String getDetaileid() {
        return detaileid;
    }

    public void setDetaileid(String detaileid) {
        this.detaileid = detaileid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Object getAccountid() {
        return accountid;
    }

    public void setAccountid(Object accountid) {
        this.accountid = accountid;
    }

    public double getDealbalance() {
        return dealbalance;
    }

    public void setDealbalance(double dealbalance) {
        this.dealbalance = dealbalance;
    }

    public long getRegisttime() {
        return registtime;
    }

    public void setRegisttime(long registtime) {
        this.registtime = registtime;
    }

    public int getDealtype() {
        return dealtype;
    }

    public void setDealtype(int dealtype) {
        this.dealtype = dealtype;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }
}
