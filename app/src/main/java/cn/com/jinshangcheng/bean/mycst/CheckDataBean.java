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
    public double residualfuel;
    public double perresidualfue;
    public double malfunctionnum;
    public double onflowct;
    public double coolantct;
    public double environmentct;
    public double imap;
    public double fuelpressure;
    public double airpressure;
    public double airflow;
    public double tvp;
    public double pedalposition;
    public double engineruntime;
    public double troublemileage;
    public double enginepayload;
    public double lfueltrim;
    public double ciaa;
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
        dest.writeDouble(this.residualfuel);
        dest.writeDouble(this.perresidualfue);
        dest.writeDouble(this.malfunctionnum);
        dest.writeDouble(this.onflowct);
        dest.writeDouble(this.coolantct);
        dest.writeDouble(this.environmentct);
        dest.writeDouble(this.imap);
        dest.writeDouble(this.fuelpressure);
        dest.writeDouble(this.airpressure);
        dest.writeDouble(this.airflow);
        dest.writeDouble(this.tvp);
        dest.writeDouble(this.pedalposition);
        dest.writeDouble(this.engineruntime);
        dest.writeDouble(this.troublemileage);
        dest.writeDouble(this.enginepayload);
        dest.writeDouble(this.lfueltrim);
        dest.writeDouble(this.ciaa);
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
        this.residualfuel = in.readDouble();
        this.perresidualfue = in.readDouble();
        this.malfunctionnum = in.readDouble();
        this.onflowct = in.readDouble();
        this.coolantct = in.readDouble();
        this.environmentct = in.readDouble();
        this.imap = in.readDouble();
        this.fuelpressure = in.readDouble();
        this.airpressure = in.readDouble();
        this.airflow = in.readDouble();
        this.tvp = in.readDouble();
        this.pedalposition = in.readDouble();
        this.engineruntime = in.readDouble();
        this.troublemileage = in.readDouble();
        this.enginepayload = in.readDouble();
        this.lfueltrim = in.readDouble();
        this.ciaa = in.readDouble();
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