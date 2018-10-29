package cn.com.jinshangcheng.bean;

public class Goods extends BaseBean<Goods> {

    /**
     * goodsid : 2
     * goodsname : 带子
     * price : 20.0
     * imagelist : /images/goods/list/boxu.png
     * imagepath : /images/goods/web/boxu.png
     * imagedetail : /images/goods/detail/boxu.png
     * textdetail : 这个带子确实好用
     * registtime : 1537426527000
     * updatetime : 1537426527000
     * orderby : 2
     */

    private String goodsid;
    private String goodsname;
    private double price;
    private String imagelist;
    private String imagepath;
    private String imagedetail;
    private String textdetail;
    private long registtime;
    private long updatetime;
    private int orderby;

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagelist() {
        return imagelist;
    }

    public void setImagelist(String imagelist) {
        this.imagelist = imagelist;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getImagedetail() {
        return imagedetail;
    }

    public void setImagedetail(String imagedetail) {
        this.imagedetail = imagedetail;
    }

    public String getTextdetail() {
        return textdetail;
    }

    public void setTextdetail(String textdetail) {
        this.textdetail = textdetail;
    }

    public long getRegisttime() {
        return registtime;
    }

    public void setRegisttime(long registtime) {
        this.registtime = registtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsid='" + goodsid + '\'' +
                ", goodsname='" + goodsname + '\'' +
                ", price=" + price +
                ", imagelist='" + imagelist + '\'' +
                ", imagepath='" + imagepath + '\'' +
                ", imagedetail='" + imagedetail + '\'' +
                ", textdetail='" + textdetail + '\'' +
                ", registtime=" + registtime +
                ", updatetime=" + updatetime +
                ", orderby=" + orderby +
                '}';
    }
}
