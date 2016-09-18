package com.server;

/**
 * @author nullzZ
 * 
 */
public enum ChannelEnum {

    NONE(0), ANY_SDK(1000);
    public int value;

    private ChannelEnum(int value) {
	this.value = value;
    }

    public static ChannelEnum get(int value) {
	for (ChannelEnum c : values()) {
	    if (value == c.value) {
		return c;
	    }
	}
	return null;

    }

}
