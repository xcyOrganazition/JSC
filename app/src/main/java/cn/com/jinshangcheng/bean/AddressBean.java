package cn.com.jinshangcheng.bean;

public class AddressBean extends BaseBean {

    public String name;
    public String phone;
    public String address;
    public String id;
    public boolean isDefault;

    public AddressBean(String name, String phone, String address, String id, boolean isDefault) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.id = id;
        this.isDefault = isDefault;
    }
}
