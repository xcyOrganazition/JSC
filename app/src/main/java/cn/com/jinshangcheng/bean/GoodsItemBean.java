package cn.com.jinshangcheng.bean;

/**
 * 商品条目Bean
 * 商品在购物车
 */
public class GoodsItemBean extends BaseBean {

    /**
     * cartitemid : 123123
     * quantity : 2
     * goodsid : 1
     * tGoods : {"goodsid":"12312312213","goodsname":"盒子","price":1390,"imagelist":"/images/goods/list/box.png","imagepath":null,"imagedetail":null,"textdetail":null,"registtime":null,"updatetime":null,"orderby":null,"other1":null,"other2":null}
     * userid : 2131231312
     * cartitemtime : null
     * other1 : null
     * other2 : null
     */

    public String cartitemid;
    public int quantity;
    public String goodsid;
    public Goods goods;
    public String userid;
    public long cartitemtime;


}
