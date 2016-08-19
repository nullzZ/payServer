package com.server.service.anySdk;

/**
 * 
 * @author nullzZ
 *
 */
public interface IAnySdkService {
    public boolean checkSign(String data, String originSign);
}
