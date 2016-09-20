package com.server.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * 
 * @author nullzZ
 *
 */
public class HttpUtil {
    public static JSONObject doPost(String url, byte[] postData) throws Exception {
	OutputStream os = null;
	BufferedReader reader = null;
	try {
	    String data = "";
	    URL dataUrl = new URL(url);
	    HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
	    con.setRequestMethod("POST");
	    con.setRequestProperty("Proxy-Connection", "Keep-Alive");
	    con.setRequestProperty("accept", "*/*");
	    con.setRequestProperty("connection", "Keep-Alive");
	    // con.setRequestProperty("Content-type", "application/json"
	    // + ";charset=UTF-8");
	    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    con.setDoOutput(true);
	    con.setDoInput(true);
	    os = con.getOutputStream();
	    os.write(postData);
	    os.flush();

	    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    String lines;
	    StringBuffer sb = new StringBuffer("");
	    while ((lines = reader.readLine()) != null) {
		lines = new String(lines.getBytes(), "utf-8");
		sb.append(lines);
	    }

	    data = sb.toString();
	    reader.close();
	    con.disconnect();
	    JSONObject ret = null;
	    if (data != null && !data.equals("")) {
		ret = new JSONObject(data);
	    }
	    return ret;
	} catch (Exception e) {
	    throw e;
	} finally {
	    try {
		if (os != null) {
		    os.close();
		}
		if (reader != null) {
		    reader.close();
		}
	    } catch (Exception e2) {
		throw e2;
	    }
	}

    }

    public static String doPostStr(String url, byte[] postData) throws Exception {
	OutputStream os = null;
	BufferedReader reader = null;
	try {
	    URL dataUrl = new URL(url);
	    HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
	    con.setRequestMethod("POST");
	    con.setRequestProperty("Proxy-Connection", "Keep-Alive");
	    con.setRequestProperty("accept", "*/*");
	    con.setRequestProperty("connection", "Keep-Alive");
	    // con.setRequestProperty("Content-type", "application/json"
	    // + ";charset=UTF-8");
	    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    con.setDoOutput(true);
	    con.setDoInput(true);
	    os = con.getOutputStream();
	    os.write(postData);
	    os.flush();

	    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    String lines;
	    StringBuffer sb = new StringBuffer("");
	    while ((lines = reader.readLine()) != null) {
		lines = new String(lines.getBytes(), "utf-8");
		sb.append(lines);
	    }
	    con.disconnect();
	    return sb.toString();
	} catch (Exception e) {
	    throw e;
	} finally {
	    try {
		if (os != null) {
		    os.close();
		}
		if (reader != null) {
		    reader.close();
		}
	    } catch (Exception e2) {
		throw e2;
	    }
	}

    }

    public static void write(HttpServletResponse response, JSONObject obj) {
	try {
	    response.setCharacterEncoding("UTF-8");
	    String str = obj.toString();
	    if (str != null && !str.equals("")) {
		response.getWriter().write(str);
	    }
	    response.flushBuffer();
	} catch (Exception e) {
	} finally {
	    try {
		if (response.getWriter() != null) {
		    response.getWriter().close();
		}
	    } catch (Exception e2) {
		e2.printStackTrace();
	    }
	}
    }

    public static void write(HttpServletResponse response, String str) {
	try {
	    response.setCharacterEncoding("UTF-8");
	    if (str != null && !str.equals("")) {
		response.getWriter().write(str);
	    }
	    response.flushBuffer();
	} catch (Exception e) {
	} finally {
	    try {
		if (response.getWriter() != null) {
		    response.getWriter().close();
		}
	    } catch (Exception e2) {
		e2.printStackTrace();
	    }
	}
    }

    public static String doPost(String reqUrl, Map<String, String> parameters) throws RuntimeException {
	HttpURLConnection urlConn = null;
	try {
	    urlConn = sendPost(reqUrl, parameters);
	    String responseContent = getContent(urlConn);

	    return responseContent.trim();
	} catch (RuntimeException e) {
	    throw e;
	} finally {
	    if (urlConn != null) {
		urlConn.disconnect();
		urlConn = null;
	    }
	}

    }

    private static String getContent(HttpURLConnection urlConn) {
	try {
	    String responseContent = null;
	    InputStream in = urlConn.getInputStream();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	    String tempLine = rd.readLine();
	    StringBuffer tempStr = new StringBuffer();
	    String crlf = System.getProperty("line.separator");
	    while (tempLine != null) {
		tempStr.append(tempLine);
		tempStr.append(crlf);
		tempLine = rd.readLine();
	    }
	    responseContent = tempStr.toString();
	    rd.close();
	    in.close();
	    return responseContent;
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage(), e);
	}
    }

    private static HttpURLConnection sendPost(String reqUrl, Map<String, String> parameters) {
	HttpURLConnection urlConn = null;
	OutputStream os = null;
	try {
	    String params = generatorParamString(parameters);
	    URL url = new URL(reqUrl);
	    urlConn = (HttpURLConnection) url.openConnection();
	    urlConn.setRequestMethod("POST");
	    // urlConn
	    // .setRequestProperty(
	    // "User-Agent",
	    // "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.3)
	    // Gecko/20100401 Firefox/3.6.3");
	    urlConn.setConnectTimeout(30 * 1000);
	    urlConn.setReadTimeout(30 * 1000);
	    urlConn.setDoOutput(true);
	    byte[] b = params.getBytes();
	    // System.err.println(params);
	    os = urlConn.getOutputStream();
	    os.write(b, 0, b.length);
	    os.flush();
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage(), e);
	} finally {
	    try {
		if (os != null) {
		    os.close();
		}
	    } catch (Exception e2) {
		e2.printStackTrace();
	    }
	}
	return urlConn;
    }

    /**
     * 把map转换为键值对的字符串用于url请求
     * 
     * @param parameters
     * @return
     */
    public static String generatorParamString(Map<String, String> parameters) {
	StringBuffer params = new StringBuffer();
	if (parameters != null) {
	    for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
		String name = iter.next();
		String value = parameters.get(name);
		params.append(name + "=");
		try {
		    params.append(URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		    throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
		    String message = String.format("'%s'='%s'", name, value);
		    throw new RuntimeException(message, e);
		}
		if (iter.hasNext()) {
		    params.append("&");
		}
	    }
	}
	return params.toString();
    }

    /**
     * 获取IP地址
     * 
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
	String ip = request.getHeader("x-forwarded-for");
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getHeader("Proxy-Client-IP");
	}
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getHeader("WL-Proxy-Client-IP");
	}
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getRemoteAddr();
	}

	if (ip != null)
	    ip = getFirstIp(ip);

	return ip;
    }

    private static String getFirstIp(String ipString) {
	String ip = null;
	String[] ipList = ipString.split(",");
	if (ipList != null && ipList.length > 1) {
	    ip = ipList[0];
	} else {
	    ip = ipString;
	}
	return ip;
    }

    public static JSONObject doPost(String url) throws Exception {
	String data = "";
	URL dataUrl = new URL(url);
	HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
	con.setRequestMethod("POST");
	con.setRequestProperty("Proxy-Connection", "Keep-Alive");
	con.setRequestProperty("accept", "*/*");
	con.setRequestProperty("connection", "Keep-Alive");
	// con.setConnectTimeout(5000);
	// con.setRequestProperty("Content-type", "application/json"
	// + ";charset=UTF-8");
	con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	con.setDoOutput(true);
	con.setDoInput(true);

	BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	String lines;
	StringBuffer sb = new StringBuffer("");
	while ((lines = reader.readLine()) != null) {
	    lines = new String(lines.getBytes(), "utf-8");
	    sb.append(lines);
	}

	data = sb.toString();
	reader.close();
	con.disconnect();
	JSONObject ret = null;
	if (data != null && !data.equals("")) {
	    ret = new JSONObject(data);
	}
	return ret;
    }
}
