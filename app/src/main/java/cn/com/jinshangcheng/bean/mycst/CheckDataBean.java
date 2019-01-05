package cn.com.jinshangcheng.bean.mycst;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 一键检测
 * 原CarConDectionResult.DataEntity.ObdDataEntity
 */
public class CheckDataBean implements Parcelable {
    public String checkid;
    public String carid;
    public double batteryvoltage;
    public double speed;
    public int rpm;
    public String residualfuel;
    public String perresidualfue;
    public int malfunctionnum;
    public String onflowct;
    public String coolantct;
    public String environmentct;
    public String imap;
    public String fuelpressure;
    public String airpressure;
    public String airflow;
    public String tvp;
    public String pedalposition;
    public String engineruntime;
    public String troublemileage;
    public String enginepayload;
    public String lfueltrim;
    public String ciaa;
    public long checkdate;
    public double mileage;
    public double fuel;



    @Override
    public String toString() {
        return "CheckDataBean{" +
                "checkid='" + checkid + '\'' +
                ", carid='" + carid + '\'' +
                ", batteryvoltage=" + batteryvoltage +
                ", speed=" + speed +
                ", rpm=" + rpm +
                ", residualfuel=" + residualfuel +
                ", perresidualfue=" + perresidualfue +
                ", malfunctionnum=" + malfunctionnum +
                ", onflowct=" + onflowct +
                ", coolantct=" + coolantct +
                ", environmentct=" + environmentct +
                ", imap=" + imap +
                ", fuelpressure=" + fuelpressure +
                ", airpressure=" + airpressure +
                ", airflow=" + airflow +
                ", tvp=" + tvp +
                ", pedalposition=" + pedalposition +
                ", engineruntime=" + engineruntime +
                ", troublemileage=" + troublemileage +
                ", enginepayload=" + enginepayload +
                ", lfueltrim=" + lfueltrim +
                ", ciaa=" + ciaa +
                ", checkdate=" + checkdate +
                ", mileage=" + mileage +
                ", fuel=" + fuel +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.checkid);
        dest.writeString(this.carid);
        dest.writeDouble(this.batteryvoltage);
        dest.writeDouble(this.speed);
        dest.writeInt(this.rpm);
        dest.writeString(this.residualfuel);
        dest.writeString(this.perresidualfue);
        dest.writeInt(this.malfunctionnum);
        dest.writeString(this.onflowct);
        dest.writeString(this.coolantct);
        dest.writeString(this.environmentct);
        dest.writeString(this.imap);
        dest.writeString(this.fuelpressure);
        dest.writeString(this.airpressure);
        dest.writeString(this.airflow);
        dest.writeString(this.tvp);
        dest.writeString(this.pedalposition);
        dest.writeString(this.engineruntime);
        dest.writeString(this.troublemileage);
        dest.writeString(this.enginepayload);
        dest.writeString(this.lfueltrim);
        dest.writeString(this.ciaa);
        dest.writeLong(this.checkdate);
        dest.writeDouble(this.mileage);
        dest.writeDouble(this.fuel);
    }

    public CheckDataBean() {
    }

    protected CheckDataBean(Parcel in) {
        this.checkid = in.readString();
        this.carid = in.readString();
        this.batteryvoltage = in.readDouble();
        this.speed = in.readDouble();
        this.rpm = in.readInt();
        this.residualfuel = in.readString();
        this.perresidualfue = in.readString();
        this.malfunctionnum = in.readInt();
        this.onflowct = in.readString();
        this.coolantct = in.readString();
        this.environmentct = in.readString();
        this.imap = in.readString();
        this.fuelpressure = in.readString();
        this.airpressure = in.readString();
        this.airflow = in.readString();
        this.tvp = in.readString();
        this.pedalposition = in.readString();
        this.engineruntime = in.readString();
        this.troublemileage = in.readString();
        this.enginepayload = in.readString();
        this.lfueltrim = in.readString();
        this.ciaa = in.readString();
        this.checkdate = in.readLong();
        this.mileage = in.readDouble();
        this.fuel = in.readDouble();
    }

    public static final Creator<CheckDataBean> CREATOR = new Creator<CheckDataBean>() {
        @Override
        public CheckDataBean createFromParcel(Parcel source) {
            return new CheckDataBean(source);
        }

        @Override
        public CheckDataBean[] newArray(int size) {
            return new CheckDataBean[size];
        }
    };
}