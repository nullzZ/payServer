package com.server.sdkImpl.anySdk;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.server.ChannelEnum;
import com.server.Config;
import com.server.core.model.IDispathHandle;
import com.server.db.model.OrderRecord;

/**
 * @author nullzZ
 * 
 */
public class AnySdkDispathHandle implements IDispathHandle {

    @Override
    public boolean sendServer(OrderRecord order) {
	int amount = order.getProductAmount();
	String orderId = String.valueOf(order.getOrderId());
	String serverId = order.getServerId();
	long roleId = Long.parseLong(order.getRoleId());
	String productId = order.getProductId();
	String channelId = order.getChannelId();

	short keyLength = (short) Config.KEY.length();
	short valueLength = (short) Config.VALUE.length();
	short orderIdLength = (short) orderId.length();
	short productIdLength = (short) productId.length();

	short totalLength = (short) (2 + keyLength + valueLength + orderIdLength + productIdLength + 28);
	ByteBuffer wbb = ByteBuffer.allocate(totalLength + 2);
	wbb.putShort(totalLength);

	wbb.put((byte) 18);
	wbb.put((byte) 117);
	wbb.putShort(keyLength);
	wbb.put(Config.KEY.getBytes());
	wbb.putShort(valueLength);
	wbb.put(Config.VALUE.getBytes());
	wbb.putLong(roleId);
	wbb.putInt(Integer.parseInt(serverId));
	wbb.putShort(orderIdLength);
	wbb.put(orderId.getBytes());
	wbb.putShort(productIdLength);
	wbb.put(productId.getBytes());
	wbb.putInt(amount);//
	wbb.putInt(Integer.parseInt(channelId));

	byte[] oarray = wbb.array();

	Socket socket = null;
	InputStream sInputStream = null;
	OutputStream sOutputStream = null;
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

}
