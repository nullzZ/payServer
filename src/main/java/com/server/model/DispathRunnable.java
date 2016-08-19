package com.server.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import com.server.Config;
import com.server.SpringContextUtil;
import com.server.db.model.OrderRecord;
import com.server.service.impl.OrderService;

/**
 * 
 * @author nullzZ
 *
 */
public class DispathRunnable implements Runnable {
    private static Logger logger = Logger.getLogger(DispathRunnable.class);

    private OrderRecord order;

    public DispathRunnable(OrderRecord order) {
	this.order = order;
    }

    @Override
    public void run() {
	if (this.order != null) {
	    OrderService orderService = (OrderService) SpringContextUtil.getBean("orderService");
	    try {
		if (sendGServer()) {
		    order.setDispatchState(DispatchStateEnum.FINISH.getType());
		    order.setDispatchTime(System.currentTimeMillis());
		} else {
		    orderService.reDispath(order);
		}
	    } catch (Exception e) {
		orderService.reDispath(order);
	    }
	    orderService.updateOrder(order);
	}
    }

    private boolean sendGServer() throws Exception {
	String serverUrl = Config.SERVERS_CONFIG.getString(order.getChannelId() + "_" + order.getServerId());
	if (serverUrl == null) {
	    logger.error("发货失败",
		    new RuntimeException("订单[" + order.getOrderId() + "]的区[" + order.getServerId() + "]不存在"));
	    return false;
	}
	int amount = order.getProductAmount();
	String orderId = String.valueOf(order.getOrderId());
	String serverId = order.getServerId();
	String ext = "";
	long roleId = Long.parseLong(order.getRoleId());
	String productId = order.getProductId();
	String channelId = order.getChannelId();

	short keyLength = (short) Config.KEY.length();
	short valueLength = (short) Config.VALUE.length();
	short orderIdLength = (short) orderId.length();
	short productIdLength = (short) productId.length();
	short extLength = (short) ext.length();

	// 所占字节数 2：有两个byte 16:4个int 8:4个short 8：一个long
	short totalLength = (short) (2 + keyLength + valueLength + orderIdLength + productIdLength + extLength + 16 + 10
		+ 8);
	ByteBuffer wbb = ByteBuffer.allocate(totalLength + 2);
	wbb.putShort(totalLength);

	wbb.put((byte) 18);
	wbb.put((byte) 32);
	wbb.putShort(keyLength);// 21
	wbb.put(Config.KEY.getBytes());// fjalwrjfkfj4723894723
	wbb.putShort(valueLength);// 21
	wbb.put(Config.VALUE.getBytes());// 235sgdsgds435435567df
	wbb.putLong(roleId);// 81064793292668928L
	wbb.putInt(Integer.parseInt(serverId));// 18
	wbb.putShort(extLength);// 23
	wbb.put(ext.getBytes());// PB708515122510385009544
	wbb.putShort(productIdLength);// 4
	wbb.put(productId.getBytes());// 1001
	wbb.putInt(amount);//
	wbb.putInt(1);//
	wbb.putInt(Integer.parseInt(channelId));
	wbb.putShort(orderIdLength);// 23
	wbb.put(orderId.getBytes());// PB708515122510385009544

	byte[] oarray = wbb.array();

	Socket socket = null;
	InputStream sInputStream = null;
	OutputStream sOutputStream = null;

	try {
	    String host = Config.SERVERS_CONFIG.getString(channelId + "_" + serverId);// 这里必须用channelId+ServerId来区分服务器的唯一
	    if (socket == null)
		socket = new Socket(host, Config.PORT);
	    if (sInputStream == null)
		sInputStream = socket.getInputStream();
	    if (sOutputStream == null)
		sOutputStream = socket.getOutputStream();

	    sOutputStream.write(oarray);
	    return true;

	    // // 等待游戏服务器返回accessToken
	    // int readtime = 100;
	    // boolean hasGetInfo = false;
	    // for (int i = 0; i < readtime; i++) {
	    // int available = sInputStream.available();
	    // if (available != 0) {
	    // hasGetInfo = true;
	    // }
	    //
	    // if (!hasGetInfo)
	    // Thread.sleep(100);
	    // else {
	    // Thread.sleep(50);
	    // }
	    // }
	} catch (Exception e) {
	    logger.error("[发送游戏服务器订单]服务器异常" + orderId, e);
	    throw e;
	} finally {
	    if (null != sInputStream) {
		sInputStream.close();
		sInputStream = null;
	    }
	    if (null != sOutputStream) {
		sOutputStream.close();
		sOutputStream = null;
	    }
	    if (null != socket) {
		socket.close();
		socket = null;
	    }

	}

    }

    // private String getSignature(String roleId, String orderId, int
    // virtualCurrency) {
    // List<String> verifyList = new ArrayList<String>();
    // verifyList.add(roleId);
    // verifyList.add("" + virtualCurrency);
    // verifyList.add(orderId);
    // verifyList.add(Config.KEY);
    // Collections.sort(verifyList);
    // StringBuffer buffer = new StringBuffer();
    // for (String s : verifyList) {
    // buffer.append(s);
    // }
    // return MD5Utils.encrypt(buffer.toString());
    // }

}
