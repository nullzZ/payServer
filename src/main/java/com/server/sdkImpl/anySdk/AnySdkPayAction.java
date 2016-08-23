package com.server.sdkImpl.anySdk;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.server.ChannelEnum;
import com.server.action.ISdkPayAction;
import com.server.service.impl.OrderService;
import com.server.util.HttpUtil;

/**
 * 
 * @author nullzZ
 *
 */
@Controller
@RequestMapping(value = "/any_sdk")
public class AnySdkPayAction implements ISdkPayAction {

    private static final Logger logger = Logger.getLogger(AnySdkPayAction.class);
    @Resource
    private AnySdkService anySdkService;
    @Resource
    private OrderService orderService;

    @Override
    @RequestMapping(value = "/clientReCall", method = RequestMethod.POST)
    public void clientReCall(HttpServletRequest request, HttpServletResponse response) {
	String channelId = "";
	String serverId = "";
	String productId = "";
	String roleId = "";
	String userId = "";
	String amount = "";

	int amount2 = (int) (Float.parseFloat(amount) * 100);
	boolean ret = anySdkService.clientReCall(ChannelEnum.ANY_SDK, channelId, serverId, productId, 1, amount2,
		roleId, userId, "", "");
	if (!ret) {
	    HttpUtil.write(response, "fail");
	    return;
	}
	HttpUtil.write(response, "ok");
    }

    /**
     * 充值回调
     * 
     * @param request
     * @param response
     */
    @Override
    @RequestMapping(value = "/reCall", method = RequestMethod.GET)
    public void reCall(HttpServletRequest request, HttpServletResponse response) {
//	 long orderId = 8888888l;
//	 String payStatus = "1";
//	 String amount = "1.1";
//	 String channelId = "0";
//	 String serverId = "1";
//	 String productId = "1001";
//	 String roleId = "81064793292668928";
//	 String userId = "4652218415336747444";
//	 String pay_time = "2016-01-01 12:12:12";
//	 OrderRecord order = orderService.createOrder(ChannelEnum.ANY_SDK, channelId, serverId, orderId, productId, 1,
//			110, roleId, userId, System.currentTimeMillis(), "", "");// 创建订单
	try {
	    String originSign = request.getParameter("sign");
	    String data = anySdkService.getValues(request);
	    String payDes = "ok";

	    if (!anySdkService.checkSign(data, originSign)) {
		HttpUtil.write(response, "fail");
		return;
	    }

	    String sdkOrderId = request.getParameter("orderId");
	    String payStatus = request.getParameter("pay_status");
	    if (!"1".equals(payStatus)) {
		payDes = "[订单异常]SDK| sdkOrderId:" + sdkOrderId + "|payStatus:" + payStatus;
		logger.error(payDes);
		HttpUtil.write(response, "fail");
		return;
	    }
	    String amount = request.getParameter("amount");
	    String channelId = request.getParameter("channel_number");
	    String serverId = request.getParameter("server_id");
	    String productId = request.getParameter("product_id");
	    String roleId = request.getParameter("game_user_id");
	    String userId = request.getParameter("user_id");
	    long orderId = Long.parseLong(request.getParameter("private_data"));// 扩展字段自己的orderId
	    int amount2 = (int) (Float.parseFloat(amount) * 100);

	    boolean ret = anySdkService.dispatchOrder(orderId, channelId, productId, amount2, userId, roleId, serverId,
		    anySdkService.getValues(request));

	    if (!ret) {
		HttpUtil.write(response, "fail");
		return;
	    }

	    HttpUtil.write(response, "ok");
	} catch (Exception e) {
	    logger.error("[订单异常]");
	    HttpUtil.write(response, "fail");
	}
    }

    // /**
    // * 充值回调
    // *
    // * @param request
    // * @param response
    // */
    // @Override
    // @RequestMapping(value = "/reCall", method = RequestMethod.POST)
    // public void reCall(HttpServletRequest request, HttpServletResponse
    // response) {
    //
    // // String orderId = "9999999999";
    // // String payStatus = "1";
    // // String amount = "1.1";
    // // String channelId = "0";
    // // String serverId = "1";
    // // String productId = "1.1";
    // // String roleId = "11111111";
    // // String userId = "22222222";
    // // String pay_time = "2016-01-01 12:12:12";
    // String originSign = request.getParameter("sign");
    // String data = anySdkService.getValues(request);
    //
    // if (!anySdkService.checkSign(data, originSign)) {
    // HttpUtil.write(response, "fail");
    // return;
    // } else {
    // HttpUtil.write(response, "ok");
    // }
    //
    // String orderId = request.getParameter("order_id");// sdk的ID
    // String ext_orderId = request.getParameter("private_data");//
    // 扩展字段自己的orderId
    // String payStatus = request.getParameter("pay_status");
    // String amount = request.getParameter("amount");
    // String channelId = request.getParameter("channel_number");
    // String serverId = request.getParameter("server_id");
    // String productId = request.getParameter("product_id");
    // String roleId = request.getParameter("game_user_id");
    // String userId = request.getParameter("user_id");
    // String pay_time = request.getParameter("pay_time");
    // long payTime = 0;
    // try {
    // payTime = new SimpleDateFormat("yyyy-MM-dd
    // HH:mm:ss").parse(pay_time).getTime();
    // } catch (ParseException e) {
    // logger.error("[订单异常] orderId:" + orderId);
    // return;
    // }
    //
    // OrderRecord order = orderService.selectOrderRecord(orderId);
    // String payDes = "ok";
    // if (null != order) {
    // payDes = "[订单异常][添加重复订单]orderId:" + orderId + "|productId:" + productId;
    // logger.error(payDes);
    // return;
    // } else {
    // order = orderService.createOrder(channelId, serverId, orderId, productId,
    // 1, roleId, userId, payTime, "",
    // data);// 创建订单
    // }
    //
    // if (!"1".equals(payStatus)) {
    // payDes = "[订单异常] orderId:" + orderId + "|payStatus:" + payStatus;
    // logger.error(payDes);
    // order.setPaydes(payDes);
    // orderService.updateOrder(order);
    // return;
    // }
    //
    // ProductRecord product =
    // ProductManager.getInstance().getProductData(channelId, productId);
    // if (null == product) {
    // payDes = "[订单异常][产品id异常]orderId:" + orderId + "|productId:" + productId;
    // order.setPaydes(payDes);
    // orderService.updateOrder(order);
    // logger.error(payDes);
    // return;
    // } else {
    // product = ProductManager.getInstance().getProductData(channelId,
    // productId);
    // order.setProductAmount(product.getRmb());
    // order.setVirtualCurrency(product.getVirtualCurrency());
    // orderService.updateOrder(order);
    // }
    // if ((Float.parseFloat(amount) * 100) != product.getRmb()) {
    // payDes = "[订单异常][产品金额异常]orderId:" + orderId + "|productId:" + productId +
    // "|amount:" + amount + "|pAmount:"
    // + product.getRmb();
    // order.setPaydes(payDes);
    // orderService.updateOrder(order);
    // logger.error(payDes);
    // return;
    // }
    //
    // order.setOrderState(OrderStateEnum.SUCCESS.getType());
    // orderService.updateOrder(order);
    // orderService.offerNewOrder(order);
    //
    // }

}
