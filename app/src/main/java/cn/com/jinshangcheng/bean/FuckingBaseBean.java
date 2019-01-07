package cn.com.jinshangcheng.bean;

import java.io.Serializable;

/**
 * 通用数据
 */

public class FuckingBaseBean<T> implements Serializable {
    public String errorMsg;//错误描述
    public String message;//错误描述
    public String code;
    public boolean success;//是否成功
    public boolean paging;//是否分页
    public PageBean page;
    public T data;
    public T tAccount;
    public T address;

    @Override
    public String toString() {
        return "BaseBean{" +
                "errorMsg='" + errorMsg + '\'' +
                ", errorCode=" + code +
                ", success=" + success +
                ", paging=" + paging +
                ", page=" + page +
                ", data=" + data +
                '}';
    }

    public class PageBean {
        public int index;//页号 默认1 ,
        public int size;//每页条数 默认10 ,
        public int totalResult;//总条数
    }

}
