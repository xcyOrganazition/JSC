package cn.com.jinshangcheng.bean;

public class Address extends BaseBean<Address> {


    /**
     * addressid : 2
     * receiver : 李四
     * phonenumber : 18810609610
     * city : 北京市海淀区
     * detailaddress : 百度大厦19层
     * isdel : false
     * userid : 1
     * isdefault : 0
     * registdate : 1537501863000
     * updatedate : 1537501863000
     * other1 : null
     * other2 : null
     */

    //`addressid` int(4) NOT NULL auto_increment,
    //`receiver` varchar(50) default NULL,/*张三*/
    //`phoneNumber` varchar(50) default NULL,
    //`city` varchar(50) default NULL,/*北京市海淀区*/
    //`detailaddress` varchar(100) default NULL,/*融科资讯大厦12层*/
    //`isdel` tinyint(1) default NULL,/*false 未删除，true删除*/
    //`userid` int(4) default NULL,
    //`isdefault` int(4) default NULL,/*0默认 1非默认 每次添加地址如果是唯一的一个，设为默认*/
    //`registdate` datetime default NULL,
    //`updatedate` datetime default NULL,

    private String addressid;
    private String receiver;
    private String phonenumber;
    private String city;
    private String detailaddress;
    private boolean isdel;
    private String userid;
    private int isdefault;
    private long registdate;
    private long updatedate;

    public Address(String receiver, String phonenumber, String city, String detailaddress, int isdefault) {
        this.receiver = receiver;
        this.phonenumber = phonenumber;
        this.city = city;
        this.detailaddress = detailaddress;
        this.isdefault = isdefault;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetailaddress() {
        return detailaddress;
    }

    public void setDetailaddress(String detailaddress) {
        this.detailaddress = detailaddress;
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

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
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

    @Override
    public String toString() {
        return "Address{" +
                "addressid='" + addressid + '\'' +
                ", receiver='" + receiver + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", city='" + city + '\'' +
                ", detailaddress='" + detailaddress + '\'' +
                ", isdel=" + isdel +
                ", userid=" + userid +
                ", isdefault=" + isdefault +
                ", registdate=" + registdate +
                ", updatedate=" + updatedate +
                '}';
    }
}
