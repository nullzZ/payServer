package com.server.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.JavaType;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author nullzZ
 *
 */
public class JSONUtils {

    // 里面的mapper官方说可以全局公用
    private static JsonBinder binder = JsonBinder.buildNonDefaultBinder();

    public static <T> T fromJson(String json, Class<T> clazz) {

	T bean = binder.fromJson(json, clazz);
	return bean;
    }

    public static String toJson(Object obj) {
	return binder.toJson(obj);
    }

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
	JsonBinder binder = JsonBinder.buildNonDefaultBinder();
	JavaType type = binder.getMapper().getTypeFactory().constructParametricType(ArrayList.class, clazz);
	try {
	    return binder.getMapper().readValue(json, type);
	} catch (JsonParseException e) {
	    e.printStackTrace();
	} catch (JsonMappingException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static JSONObject readJOSON(InputStream in) throws JSONException, IOException {
	// DataInputStream dis = new DataInputStream(in);
	// JSONObject obj = new JSONObject(dis.readUTF());

	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	String lines;
	StringBuffer sb = new StringBuffer("");
	while ((lines = reader.readLine()) != null) {
	    lines = new String(lines.getBytes(), "utf-8");
	    sb.append(lines);
	}

	if (sb.toString().equals("")) {
	    return new JSONObject();
	}

	return new JSONObject(URLDecoder.decode(sb.toString(), "UTF-8"));
    }

}
