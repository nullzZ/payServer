package com.server.service;

/**
 * 
 * @author nullzZ
 *
 */
import java.util.List;

import com.server.db.model.ProductRecord;

public interface IProductService {
    public void load();

    public List<ProductRecord> selectProducts();

    public void reload();
}
