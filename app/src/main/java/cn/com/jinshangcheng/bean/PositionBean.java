package cn.com.jinshangcheng.bean;

/**
 * 车辆位置
 */
public class PositionBean extends BaseBean<PositionBean> {


    /**
     * dataValid : true
     * obdTime : 20180922164920
     * speed : 0
     * altitude : 8
     * longitude : 117.48521099999999
     * latitude : 39.179431
     * satellites : 5
     * openCarId : 9784n2899726Z71320K9UGGMP
     * heading : 276
     * openId : 97842899s727Z215K335UMGUM
     */

    private boolean dataValid;
    private long obdTime;
    private String speed;
    private int altitude;
    private double longitude;
    private double latitude;
    private int satellites;
    private String openCarId;
    private int heading;
    private String openId;

    public boolean isDataValid() {
        return dataValid;
    }

    public void setDataValid(boolean dataValid) {
        this.dataValid = dataValid;
    }

    public long getObdTime() {
        return obdTime;
    }

    public void setObdTime(long obdTime) {
        this.obdTime = obdTime;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getSatellites() {
        return satellites;
    }

    public void setSatellites(int satellites) {
        this.satellites = satellites;
    }

    public String getOpenCarId() {
        return openCarId;
    }

    public void setOpenCarId(String openCarId) {
        this.openCarId = openCarId;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
