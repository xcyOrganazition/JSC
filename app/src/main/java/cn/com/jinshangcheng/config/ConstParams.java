package cn.com.jinshangcheng.config;

public class ConstParams {

    public static final boolean DEBUG = false;//是否debug模式

    //网络请求相关
    public static final long WRITE_TIMEOUT = 60;//写超时
    public static final long CONNECT_TIMEOUT = 15;//请求超时
    public static final long READ_TIMEOUT = 15;//连接超时

    public static final int PAGE_COUNT = 10;//一页有多少条数据


    //车牌 城市缩写
    private static String allCityText = "京 津 沪 渝 冀 豫 云 辽 黑 湘 皖 鲁 新 苏 浙 赣 鄂 桂 甘 晋 蒙 陕 吉 闽 贵 粤 青 藏 川 宁 琼 使 领";
    public static String[] cityArray = allCityText.split(" ");

    //道路救援URL
    public static String readSaveUrl = "http://www.banyar.cn/Weixin/Order/Publish/insurance_id/713/lat/0.006/lng/0.0065.html";

    public static final String APP_FOLDER_NAME = "JSC";

    /*微信相关*/
    public static final String WX_APP_ID = "wx1771419e229ef963";
    public static final String PARTNER_ID = "1521738051";//商户id

    //客服电话
    public static final String CUSTOMER_SERVICE = "0771-4957133";


}
