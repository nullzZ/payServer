package com.server.sdkImpl.anySdk;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.server.ChannelEnum;
import com.server.Config;
import com.server.core.service.AbsSdkService;
import com.server.util.AnySdkPayNotify;

/**
 * 
 * @author nullzZ
 *
 */
@Service
public class AnySdkService extends AbsSdkService {
    private static final Logger logger = Logger.getLogger(AnySdkService.class);

    @Override
    public boolean checkGProductId(String productId, int amount, String channelId, String serverId) {
	short keyLength = (short) Config.KEY.length();
	short valueLength = (short) Config.VALUE.length();
	short productIdLength = (short) productId.length();

	short totalLength = (short) (2 + keyLength + valueLength + productIdLength + 10);
	ByteBuffer wbb = ByteBuffer.allocate(totalLength + 2);
	wbb.putShort(totalLength);

	wbb.put((byte) 18);
	wbb.put((byte) 118);
	wbb.putShort(keyLength);
	wbb.put(Config.KEY.getBytes());
	wbb.putShort(valueLength);
	wbb.put(Config.VALUE.getBytes());
	// wbb.putLong(Long.parseLong(roleId));
	wbb.putShort(productIdLength);
	wbb.put(productId.getBytes());
	wbb.putInt(amount);//

	byte[] oarray = wbb.array();

	Socket socket = null;
	InputStream sInputStream = null;
	OutputStream sOutputStream = null;
	// BufferedInputStream br = null;
	DataInputStream br = null;

	try {
	    String host = Config.getServerHost(ChannelEnum.ANY_SDK.value, channelId, serverId);// 这里必须用channelId+ServerId来区分服务器的唯一
	    if (socket == null)
		socket = new Socket(host, Config.PORT);
	    if (sInputStream == null)
		sInputStream = socket.getInputStream();
	    if (sOutputStream == null)
		sOutputStream = socket.getOutputStream();

	    sOutputStream.write(oarray);
	    // 接收服务器的相应
	    br = new DataInputStream(sInputStream);
	    br.readInt();// short+byte+byte
	    byte c = br.readByte();

	    if (c == 0) {
		return true;
	    } else {
		return false;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	} finally {
	    try {

		if (null != sInputStream) {
		    sInputStream.close();
		}
		if (null != sOutputStream) {
		    sOutputStream.close();
		}
		if (null != br) {
		    br.close();
		}
		if (null != socket) {
		    socket.close();
		}

	    } catch (Exception e2) {
		return false;
	    }
	}
    }

    public boolean checkSign(String data, String originSign) {
	AnySdkPayNotify paynotify = new AnySdkPayNotify();
	/**
	 * AnySDK分配的 PrivateKey
	 * 
	 * 正式使用时记得用AnySDK分配的正式的PrivateKey
	 */
	paynotify.setPrivateKey(Config.PrivateKey);
	if (!paynotify.checkSign(data, originSign)) {
	    logger.info("[验证签名失败]" + data);
	    return false;
	}
	logger.info("[验证签名成功]" + data);
	return true;
    }

    /**
     * 将参数名从小到大排序，结果如：adfd,bcdr,bff,zx
     * 
     * @param List<String>
     *            paramNames
     */
    private void sortParamNames(List<String> paramNames) {
	Collections.sort(paramNames, new Comparator<String>() {
	    public int compare(String str1, String str2) {
		return str1.compareTo(str2);
	    }
	});
    }

    public String getValues(HttpServletRequest request) {
	Enumeration<String> requestParams = request.getParameterNames();// 获得所有的参数名
	List<String> params = new ArrayList<String>();
	while (requestParams.hasMoreElements()) {
	    params.add((String) requestParams.nextElement());
	}
	sortParamNames(params);// 将参数名从小到大排序，结果如：adfd,bcdr,bff,zx
	String paramValues = "";
	for (String param : params) {// 拼接参数值
	    // if (param.equals("sign")) {
	    // originSign = request.getParameter(param);
	    // continue;
	    // }
	    String paramValue = request.getParameter(param);
	    if (paramValue != null) {
		paramValues += paramValue;
	    }
	}

	return paramValues;
    }

}
