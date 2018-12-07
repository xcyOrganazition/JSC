package cn.com.jinshangcheng.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViolationBean extends BaseBean {

    /**
     * province : HAN
     * city : HAN_HAIKOU
     * hphm : 琼A290K1
     * hpzl : 02
     * lists : [{"date":"2018-02-08 00:15:23","area":"凤翔西路富源圆酒店人行过街西向东","act":"机动车违反禁止标线指示的","code":"13455","fen":"3","money":"100.00","handled":"0","archiveno":"","wzcity":""},{"date":"2018-04-08 16:31:12","area":"海口2号_人民桥北_2期","act":"驾驶时拨打接听手持电话的","code":"1223","fen":"2","money":"50.00","handled":"0","archiveno":"","wzcity":""}]
     */

    private String province;
    private String city;
    private String hphm;
    private String hpzl;
    @SerializedName("lists")
    private List<ViolationDetail> violationDetailList;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getHpzl() {
        return hpzl;
    }

    public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
    }

    public List<ViolationDetail> getViolationDetailList() {
        return violationDetailList;
    }

    public void setViolationDetailList(List<ViolationDetail> violationDetailList) {
        this.violationDetailList = violationDetailList;
    }

    public static class ViolationDetail {
        /**
         * date : 2018-02-08 00:15:23
         * area : 凤翔西路富源圆酒店人行过街西向东
         * act : 机动车违反禁止标线指示的
         * code : 13455
         * fen : 3
         * money : 100.00
         * handled : 0
         * archiveno :
         * wzcity :
         */

        private String date;
        private String area;
        private String act;
        @SerializedName("code")
        private String codeX;
        private String fen;
        private String money;
        private String handled;
        private String archiveno;
        private String wzcity;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getCodeX() {
            return codeX;
        }

        public void setCodeX(String codeX) {
            this.codeX = codeX;
        }

        public String getFen() {
            return fen;
        }

        public void setFen(String fen) {
            this.fen = fen;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getHandled() {
            return handled;
        }

        public void setHandled(String handled) {
            this.handled = handled;
        }

        public String getArchiveno() {
            return archiveno;
        }

        public void setArchiveno(String archiveno) {
            this.archiveno = archiveno;
        }

        public String getWzcity() {
            return wzcity;
        }

        public void setWzcity(String wzcity) {
            this.wzcity = wzcity;
        }
    }
}
