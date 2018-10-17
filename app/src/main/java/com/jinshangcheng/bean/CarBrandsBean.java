package com.jinshangcheng.bean;

/**
 * 汽车品牌
 */
public class CarBrandsBean extends BaseBean {
    public String picturePath;
    public String brandName;
    public String brandId;
    public String nameIndex;

    public CarBrandsBean(String picturePath, String brandName, String brandId, String nameIndex) {
        this.picturePath = picturePath;
        this.brandName = brandName;
        this.brandId = brandId;
        this.nameIndex = nameIndex;
    }

    public CarBrandsBean() {
    }

    @Override
    public String toString() {
        return "CarBrandsBean{" +
                "picturePath='" + picturePath + '\'' +
                ", brandName='" + brandName + '\'' +
                ", brandId='" + brandId + '\'' +
                ", nameIndex='" + nameIndex + '\'' +
                '}';
    }
}
