package cn.com.jinshangcheng.bean;

import java.io.Serializable;
import java.util.List;

public class BaseListBean<T> implements Serializable {


    /**
     * beanList : [{"detaileid":"1DD77FCCFDC94C4FB5CDF3D7F3ACA058","userid":1,"accountid":null,"dealbalance":14,"registtime":1539054541000,"dealtype":1,"orderid":"949FE5E009364150B88FFA7114FBDBA3","updatetime":1539054541000,"other1":null,"other2":null,"orderitem":"8DFC52943B9141A48993FDA609E59E2C"},{"detaileid":"9D4432B90656423B89AB3292C4DF9CD3","userid":1,"accountid":null,"dealbalance":14,"registtime":1539054540000,"dealtype":1,"orderid":"949FE5E009364150B88FFA7114FBDBA3","updatetime":1539054540000,"other1":null,"other2":null,"orderitem":"8DFC52943B9141A48993FDA609E59E2C"}]
     * totalRecord : 2
     * pageSize : 5
     * totalPage : 1
     * currentPage : 1
     * prePage : 1
     * nextPage : 1
     * pageBar : [1]
     */

    private int totalRecord;
    private int pageSize;
    private int totalPage;
    private int currentPage;
    private int prePage;
    private int nextPage;
    private List<T> beanList;


    public List<T> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<T> beanList) {
        this.beanList = beanList;
    }


}
