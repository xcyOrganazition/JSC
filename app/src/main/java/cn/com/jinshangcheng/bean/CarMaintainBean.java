package cn.com.jinshangcheng.bean;

/**
 * 获取车的保养保险年审信息
 */
public class CarMaintainBean extends BaseBean<CarMaintainBean>{

    /**
     * insurancedeadline : 1537372800000
     * cartype : 0
     * totalmileage : 500
     * maintain : {"maintainid":"0220BF7A9A8A4288BB644EF384F651F0","carid":"1648A38F4E0F4E7DACD87F105D4E3D36","lastmaintainmileage":20,"maintenanceinterval":3,"lastmaintaintime":1540010695000,"registdate":1537928161000,"annualtrialdeadline":1503417600000}
     * carregistdate : 1537372800000
     */

    private long insurancedeadline;
    private String cartype;
    private int totalmileage;
    private MaintainBean maintain;
    private long carregistdate;

    public long getInsurancedeadline() {
        return insurancedeadline;
    }

    public void setInsurancedeadline(long insurancedeadline) {
        this.insurancedeadline = insurancedeadline;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public int getTotalmileage() {
        return totalmileage;
    }

    public void setTotalmileage(int totalmileage) {
        this.totalmileage = totalmileage;
    }

    public MaintainBean getMaintain() {
        return maintain;
    }

    public void setMaintain(MaintainBean maintain) {
        this.maintain = maintain;
    }

    public long getCarregistdate() {
        return carregistdate;
    }

    public void setCarregistdate(long carregistdate) {
        this.carregistdate = carregistdate;
    }

    public static class MaintainBean {
        /**
         * maintainid : 0220BF7A9A8A4288BB644EF384F651F0
         * carid : 1648A38F4E0F4E7DACD87F105D4E3D36
         * lastmaintainmileage : 20
         * maintenanceinterval : 3
         * lastmaintaintime : 1540010695000
         * registdate : 1537928161000
         * annualtrialdeadline : 1503417600000
         */

        private String maintainid;
        private String carid;
        private int lastmaintainmileage;
        private int maintenanceinterval;
        private long lastmaintaintime;
        private long registdate;
        private long annualtrialdeadline;

        public String getMaintainid() {
            return maintainid;
        }

        public void setMaintainid(String maintainid) {
            this.maintainid = maintainid;
        }

        public String getCarid() {
            return carid;
        }

        public void setCarid(String carid) {
            this.carid = carid;
        }

        public int getLastmaintainmileage() {
            return lastmaintainmileage;
        }

        public void setLastmaintainmileage(int lastmaintainmileage) {
            this.lastmaintainmileage = lastmaintainmileage;
        }

        public int getMaintenanceinterval() {
            return maintenanceinterval;
        }

        public void setMaintenanceinterval(int maintenanceinterval) {
            this.maintenanceinterval = maintenanceinterval;
        }

        public long getLastmaintaintime() {
            return lastmaintaintime;
        }

        public void setLastmaintaintime(long lastmaintaintime) {
            this.lastmaintaintime = lastmaintaintime;
        }

        public long getRegistdate() {
            return registdate;
        }

        public void setRegistdate(long registdate) {
            this.registdate = registdate;
        }

        public long getAnnualtrialdeadline() {
            return annualtrialdeadline;
        }

        public void setAnnualtrialdeadline(long annualtrialdeadline) {
            this.annualtrialdeadline = annualtrialdeadline;
        }
    }
}
