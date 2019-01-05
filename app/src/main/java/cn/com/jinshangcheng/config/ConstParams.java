package cn.com.jinshangcheng.config;

public class ConstParams {

    public static final boolean DEBUG = true;//是否debug模式

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


    /* 支付宝相关*/
    //RSA2_PRIVATE 私钥
    public static String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCoHQPHH5eDCUG74KwFGJZr3JxJ+ESMsXiMzKyEBCb7Zb8O9ivAh6PW5hwlht9uutzYTinor/DwThaCTlUSf5QCQIgc1RO9SClFai28qfyuAAbOMEtU3CG6a1XBDXXODQ1/AV7eXYMSvuRYwdMSewuECxsn/XgLKjhW6HO3onYEqjGhlOzskQTPHvZQJAA84+4cMCo2ueLHWUQQDh/PO6GdY8YakBFT3Wz7wfz9gQF4/WNRyCO2gyqbFqZcJh7wbZmP5yfauBxWBqs4jSIXzLCDMEh63uNmFdzRPGpD2yaHf/geEyLj5J1UfXfmdSx5P9JjrYqepsDWkt+7+mvpjV75AgMBAAECggEBAJLZxZMLZa5xR5p2h81rzPorm9qBpF/CJyvXHluzxNmV52+KWm20DsqJ98xQJJz1XjUVEkbQageALulkw6uFBiVxeXS2LKgLgQHB3nIPi0nze/5g8W0PXecaG66ns2Bqxn9LEzPrO7QETDO8HETodwbBLNeh8lrOZqiEJZEjuTQYjPUd48IJd8XH160ChW26Porag2AX6aZfhs8E6ROZ1OcOC7XKOtVNX/2x3o/IB1vEmWTOMu7Y/BWoXEld+ZJEgePkbUd53w4n/0JG46BMsatmcdPXhU47SDQ8AFUGulHXwuTyc2uuNPXnEHuEWq2aJR5Mh+28MN6INvFcBKdxUAECgYEA1L93JRmanw5X1MUXXgkZc/VsC0sa60UkQVZWHIJ8St59Tg6555jcaT8LQvaWsCpThpvoOyNPG54YREW+GuM+wuEu9PEku5zWOUf77KWF7s+m3q5DR1EI88CbNszy4vXKeWR2bh4ZQ1zIuZRT1HO2KVRKksBznQ9gPi0ZUt5VHVECgYEAykqHRlJnCtGFmqcfS/xFV6RvoU48VGLT7qB7O21cRyTqP6Yjykl918HJD04l4q0ybMRLH/d1+ix7XpLtFSaFCSh3cT/Uvmp0WyQymG/p8xrjSlfxNlCTds3thc/hwYcJ+PHA8ZjazZJEq5BIvUD5DIvNQuBT6kbe93HjkzuhnSkCgYBtDcBLOjayNa38GRODVnK/qBw8JcGkvAp7m4AEmTaRQj9FFfNhrk+9e12Qc8qGfwvVwnFmbxEL/Rw/UujB656uUrO5RysnKJv/PND8W1SuN684EmfWVMmb+S0XCDOw94FUfJTgmXkluJgD2O6nxrCnuteHJjOdXNZeyMtkWSeUIQKBgQCW3Gvz8sIr3EOiKV56wYFJMjWZ+4mceTOKc55TnGREHnCf1Rw94P77p+Z1BpfUa8v+N4m7JkaSsxeyWSp8KoTuZoJlL/BNrYBm9CDTdCrqBTRanikik4bWBA9OStEXDclPIaP9yTvsMVgLBohos4rIY35JkJikUAQ6T3IjvEXzuQKBgA2Ajkqs6S+4MP8qiPa+S+pHpsrofAwGr87AgnboStPWfB3pMwURLXnhCN0eCPgx8r3jmNp+hSrRtnnCER1NOe2oGYtuDng1GwIsw6h+m81h+s4SzU3FGXt/MbriJ5HKBDsQ7MSbK2CsmILMxtNsA5ps83UEcjv6j8z7dOr9k3a2";
    //APPID aliPay
    public static String APPID = "2016092300574560";


}
