package com.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.server.db.dao.ProductRecordMapper;
import com.server.db.model.ProductRecord;
import com.server.db.model.ProductRecordExample;
import com.server.manager.ProductManager;
import com.server.service.IProductService;

/**
 * 
 * @author nullzZ
 *
 */
@Service
public class ProductService implements IProductService {

    private static final Logger logger = Logger.getLogger(ProductService.class);
    @Resource
    private ProductRecordMapper productRecordMapper;

    @Override
    public List<ProductRecord> selectProducts() {
	ProductRecordExample example = new ProductRecordExample();
	example.createCriteria().andUidIsNotNull();
	return productRecordMapper.selectByExample(example);
    }

    @Override
    public void load() {
	ProductManager.getInstance().clear();
	List<ProductRecord> list = selectProducts();
	for (ProductRecord record : list) {
	    ProductManager.getInstance().addProduct(record);
	}
	logger.info("商品信息加载======>成功");
    }

    @Override
    public void reload() {
	List<ProductRecord> list = selectProducts();
	ProductManager.getInstance().reload(list);
    }
}
