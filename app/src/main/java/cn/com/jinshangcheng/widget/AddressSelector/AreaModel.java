package cn.com.jinshangcheng.widget.AddressSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域选择
 * Created by Sangyr
 * on 2017/6/20--->17:49
 */

public class AreaModel {
    public String pid = ""; //父id
    public String name = "";//地区名称
    public String id = ""; // 当前地区id

    public List<AreaModel> child = new ArrayList<>();

    @Override
    public String toString() {
        return "AreaModel{" +
                "pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", child=" + child +
                '}';
    }
}
