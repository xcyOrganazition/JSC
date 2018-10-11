package com.jinshangcheng.utils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

/**
 * 把map转成Json作为content
 * Created by xu on 2017/6/7.
 */
public class Map2JsonUtils {

    public static String map2Json(HashMap<String, Object> map) {
        String jsonString = "";
        if (map == null || map.size() == 0) {
            return jsonString;
        } else {
            Gson gson = new Gson();
            jsonString = gson.toJson(map);

        }
        Logger.e("Json  =====    ", jsonString);
        return jsonString;


    }
}
