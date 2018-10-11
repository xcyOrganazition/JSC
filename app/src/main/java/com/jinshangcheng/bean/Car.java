package com.jinshangcheng.bean;

import java.io.Serializable;

public class Car implements Serializable {
    public String carName;//车名字
    public String licenseNum;//车牌号
    public String carBand;//车品牌
    public String carType;//车型号

    public Car(String carName, String licenseNum, String carBand, String carType) {
        this.carName = carName;
        this.licenseNum = licenseNum;
        this.carBand = carBand;
        this.carType = carType;
    }
}
