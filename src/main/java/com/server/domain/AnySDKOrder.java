package com.server.domain;

/**
 * 
 * @author nullzZ
 *
 */
public class AnySDKOrder {

    /**
     * 订单号，AnySDK产生的订单号
     */
    private String order_id;
    /**
     * 要购买商品数量（暂不提供具体数量）
     */
    private String product_count;
    /**
     * 支付金额，单位元 值根据不同渠道的要求可能为浮点类型
     */
    private String amount;
    /**
     * 支付状态，1为成功，非1则为其他异常状态，游服请在成功的状态下发货
     */
    private String pay_status;
    /**
     * 支付时间，YYYY-mm-dd HH:ii:ss格式
     */
    private String pay_time;
    /**
     * 用户id，用户系统的用户id
     */
    private String user_id;
    /**
     * 支付方式，详见[支付渠道标识表]
     */
    private String order_type;
    /**
     * 游戏内用户id,支付时传入的Role_Id参数
     */
    private String game_user_id;
    /**
     * 服务器id，支付时传入的Server_Id 参数
     */
    private String server_id;
    /**
     * 商品名称，支付时传入的Product_Name 参数
     */
    private String product_name;
    /**
     * 商品id,支付时传入的Product_Id 参数
     */
    private String product_id;
    /**
     * product_id字段的值对应的渠道商品ID，对应关系可以在dev后台 -》游戏列表 -》管理商品 页面进行配置。
     */
    private String channel_product_id;
    /**
     * 自定义参数，调用客户端支付函数时传入的EXT参数，透传给游戏服务器
     */
    private String private_data;
    /**
     * 渠道编号 [渠道列表]
     */
    private String channel_number;
    /**
     * 通用签名串，通用验签参考签名算法
     */
    private String sign;
    /**
     * 渠道服务器通知 AnySDK 时请求的参数
     */
    private String source;
    /**
     * 增强签名串，验签参考签名算法（有增强密钥的游戏有效）
     */
    private String enhanced_sign;
    /**
     * 渠道订单号，如果渠道通知过来的参数没有渠道订单号则为空。
     */
    private String channel_order_id;
    /**
     * 游戏ID，AnySDK服务端为游戏分配的唯一标识
     */
    private String game_id;
    /**
     * 插件ID，AnySDK插件数字唯一标识
     */
    private String plugin_id;

    public String getOrder_id() {
	return order_id;
    }

    public void setOrder_id(String order_id) {
	this.order_id = order_id;
    }

    public String getProduct_count() {
	return product_count;
    }

    public void setProduct_count(String product_count) {
	this.product_count = product_count;
    }

    public String getAmount() {
	return amount;
    }

    public void setAmount(String amount) {
	this.amount = amount;
    }

    public String getPay_status() {
	return pay_status;
    }

    public void setPay_status(String pay_status) {
	this.pay_status = pay_status;
    }

    public String getPay_time() {
	return pay_time;
    }

    public void setPay_time(String pay_time) {
	this.pay_time = pay_time;
    }

    public String getUser_id() {
	return user_id;
    }

    public void setUser_id(String user_id) {
	this.user_id = user_id;
    }

    public String getOrder_type() {
	return order_type;
    }

    public void setOrder_type(String order_type) {
	this.order_type = order_type;
    }

    public String getGame_user_id() {
	return game_user_id;
    }

    public void setGame_user_id(String game_user_id) {
	this.game_user_id = game_user_id;
    }

    public String getServer_id() {
	return server_id;
    }

    public void setServer_id(String server_id) {
	this.server_id = server_id;
    }

    public String getProduct_name() {
	return product_name;
    }

    public void setProduct_name(String product_name) {
	this.product_name = product_name;
    }

    public String getProduct_id() {
	return product_id;
    }

    public void setProduct_id(String product_id) {
	this.product_id = product_id;
    }

    public String getChannel_product_id() {
	return channel_product_id;
    }

    public void setChannel_product_id(String channel_product_id) {
	this.channel_product_id = channel_product_id;
    }

    public String getPrivate_data() {
	return private_data;
    }

    public void setPrivate_data(String private_data) {
	this.private_data = private_data;
    }

    public String getChannel_number() {
	return channel_number;
    }

    public void setChannel_number(String channel_number) {
	this.channel_number = channel_number;
    }

    public String getSign() {
	return sign;
    }

    public void setSign(String sign) {
	this.sign = sign;
    }

    public String getSource() {
	return source;
    }

    public void setSource(String source) {
	this.source = source;
    }

    public String getEnhanced_sign() {
	return enhanced_sign;
    }

    public void setEnhanced_sign(String enhanced_sign) {
	this.enhanced_sign = enhanced_sign;
    }

    public String getChannel_order_id() {
	return channel_order_id;
    }

    public void setChannel_order_id(String channel_order_id) {
	this.channel_order_id = channel_order_id;
    }

    public String getGame_id() {
	return game_id;
    }

    public void setGame_id(String game_id) {
	this.game_id = game_id;
    }

    public String getPlugin_id() {
	return plugin_id;
    }

    public void setPlugin_id(String plugin_id) {
	this.plugin_id = plugin_id;
    }

}
