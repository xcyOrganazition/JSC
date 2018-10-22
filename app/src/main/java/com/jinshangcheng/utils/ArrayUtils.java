package com.jinshangcheng.utils;

import java.util.List;

public class ArrayUtils {

    /**
     * 是否有数据
     *
     * @param list
     * @return
     */
    public static boolean hasContent(List list) {
        if (list == null || list.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

}
