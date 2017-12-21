package com.ys168.gam.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author Kevin
 * @since 2017年5月24日
 */
public class JSONLoader {

    private static final Logger log = Logger.getLogger(JSONLoader.class);

    public static JSONArray loadArray(String filePath) {
        String string = loadString(filePath);
        return string == null ? null : JSON.parseArray(string);
    }

    public static JSONObject loadObject(String filePath) {
        String string = loadString(filePath);
        return string == null ? null : JSON.parseObject(string);
    }

    public static String loadString(String filePath) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }
}
