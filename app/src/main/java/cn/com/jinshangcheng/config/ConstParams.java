package cn.com.jinshangcheng.config;

public class ConstParams {

    public static final boolean DEBUG = true;//debug模式

    //网络请求相关
    public static final long WRITE_TIMEOUT = 60;//写超时
    public static final long CONNECT_TIMEOUT = 15;//请求超时
    public static final long READ_TIMEOUT = 15;//连接超时

    public static final int PAGE_COUNT = 10;//一页有多少条数据

    //车牌 城市缩写
    private static String allCityText = "京 津 沪 渝 冀 豫 云 辽 黑 湘 皖 鲁 新 苏 浙 赣 鄂 桂 甘 晋 蒙 陕 吉 闽 贵 粤 青 藏 川 宁 琼 使 领";
    public static String[] cityArray = allCityText.split(" ");

}
