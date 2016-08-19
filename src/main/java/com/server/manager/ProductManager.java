package com.server.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.server.db.model.ProductRecord;

/**
 * 
 * @author nullzZ
 *
 */
public class ProductManager {

    private static final ProductManager instance = new ProductManager();

    private ProductManager() {

    }

    public static ProductManager getInstance() {
	return instance;
    }

    private Map<String, Map<String, ProductRecord>> products = new HashMap<>();

    public void addProduct(ProductRecord record) {
	if (!products.containsKey(record.getChannelId())) {
	    Map<String, ProductRecord> map = new HashMap<>();
	    map.put(record.getProductId(), record);
	    products.put(record.getChannelId(), map);
	} else {
	    products.get(record.getChannelId()).put(record.getProductId(), record);
	}
    }

    public void clear() {
	synchronized (products) {
	    products.clear();
	}
    }

    public void reload(List<ProductRecord> list) {
	synchronized (products) {
	    for (ProductRecord record : list) {
		this.addProduct(record);
	    }
	}
    }

    public ProductRecord getProductData(String channelId, String productId) {
	Map<String, ProductRecord> map = products.get(channelId);
	if (map != null) {
	    return map.get(productId);
	}
	return null;
    }
}
