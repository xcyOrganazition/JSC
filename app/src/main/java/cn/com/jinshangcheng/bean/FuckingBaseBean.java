package cn.com.jinshangcheng.bean;

import java.io.Serializable;

/**
 * 通用数据
 */

public class FuckingBaseBean<T> extends BaseBean<T> implements Serializable {

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

}
