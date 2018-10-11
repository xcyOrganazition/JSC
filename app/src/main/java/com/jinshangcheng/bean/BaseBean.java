package com.jinshangcheng.bean;

import java.io.Serializable;

/**
 * on 2017/5/10--->13:38
 */

public class BaseBean<T extends BaseBean> implements Serializable {
    public String errorMsg;//错误描述
    public int errorCode = 0;
    public boolean success;//是否成功
    public boolean paging;//是否分页
    public PageBean page;
    public T data;

    @Override
    public String toString() {
        return "BaseBean{" +
                "errorMsg='" + errorMsg + '\'' +
                ", errorCode=" + errorCode +
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
