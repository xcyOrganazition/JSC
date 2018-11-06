package cn.com.jinshangcheng.bean.mycst;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 一键检测
 * 原CarConDectionResult.DataEntity.ObdDataEntity
 */
public class CheckDataBean implements Parcelable {
    public double lfuelTrim;
    public double coolantCt;
    public double troubleMileage;
    public double onflowCt;
    public double mileage;
    public double speed;
    public double residualFuel;
    public double batteryVoltage;
    public double perResidualFuel;
    public long time;
    public double malfunctionNum;
    public double imap;
    public double tvp;
    public double enginePayload;
    public double environmentCt;
    public double ciaa;
    public int rpm;
    public double fuelPressure;
    public double fuel;
    public double airFlow;
    public double engineRuntime;
    public double pedalPosition;
    public double airPressure;
    public String openCarId;

    public static final Parcelable.Creator<CheckDataBean> CREATOR = new Parcelable.Creator<CheckDataBean>() {
        public final CheckDataBean createFromParcel(Parcel var1) {
            return new CheckDataBean(var1);
        }

        public final CheckDataBean[] newArray(int var1) {
            return new CheckDataBean[var1];
        }
    };

    public CheckDataBean() {
    }

    public String toString() {
        return "ObdDataEntity{lfuelTrim=" + this.lfuelTrim + ", coolantCt=" + this.coolantCt + ", troubleMileage=" + this.troubleMileage + ", onflowCt=" + this.onflowCt + ", mileage=" + this.mileage + ", speed=" + this.speed + ", residualFuel=" + this.residualFuel + ", batteryVoltage=" + this.batteryVoltage + ", perResidualFuel=" + this.perResidualFuel + ", time=" + this.time + ", malfunctionNum=" + this.malfunctionNum + ", imap=" + this.imap + ", tvp=" + this.tvp + ", enginePayload=" + this.enginePayload + ", environmentCt=" + this.environmentCt + ", ciaa=" + this.ciaa + ", rpm=" + this.rpm + ", fuelPressure=" + this.fuelPressure + ", fuel=" + this.fuel + ", airFlow=" + this.airFlow + ", engineRuntime=" + this.engineRuntime + ", pedalPosition=" + this.pedalPosition + ", airPressure=" + this.airPressure + ", openCarId='" + this.openCarId + '\'' + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel var1, int var2) {
        var1.writeDouble(this.lfuelTrim);
        var1.writeDouble(this.coolantCt);
        var1.writeDouble(this.troubleMileage);
        var1.writeDouble(this.onflowCt);
        var1.writeDouble(this.mileage);
        var1.writeDouble(this.speed);
        var1.writeDouble(this.residualFuel);
        var1.writeDouble(this.batteryVoltage);
        var1.writeDouble(this.perResidualFuel);
        var1.writeLong(this.time);
        var1.writeDouble(this.malfunctionNum);
        var1.writeDouble(this.imap);
        var1.writeDouble(this.tvp);
        var1.writeDouble(this.enginePayload);
        var1.writeDouble(this.environmentCt);
        var1.writeDouble(this.ciaa);
        var1.writeInt(this.rpm);
        var1.writeDouble(this.fuelPressure);
        var1.writeDouble(this.fuel);
        var1.writeDouble(this.airFlow);
        var1.writeDouble(this.engineRuntime);
        var1.writeDouble(this.pedalPosition);
        var1.writeString(this.openCarId);
        var1.writeDouble(this.airPressure);
    }

    protected CheckDataBean(Parcel var1) {
        this.lfuelTrim = var1.readDouble();
        this.coolantCt = var1.readDouble();
        this.troubleMileage = var1.readDouble();
        this.onflowCt = var1.readDouble();
        this.mileage = var1.readDouble();
        this.speed = var1.readDouble();
        this.residualFuel = var1.readDouble();
        this.batteryVoltage = var1.readDouble();
        this.perResidualFuel = var1.readDouble();
        this.time = var1.readLong();
        this.malfunctionNum = var1.readDouble();
        this.imap = var1.readDouble();
        this.tvp = var1.readDouble();
        this.enginePayload = var1.readDouble();
        this.environmentCt = var1.readDouble();
        this.ciaa = var1.readDouble();
        this.rpm = var1.readInt();
        this.fuelPressure = var1.readDouble();
        this.fuel = var1.readDouble();
        this.airFlow = var1.readDouble();
        this.engineRuntime = var1.readDouble();
        this.pedalPosition = var1.readDouble();
        this.openCarId = var1.readString();
        this.airPressure = var1.readDouble();
    }
}