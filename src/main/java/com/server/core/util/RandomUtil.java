package com.server.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author nullzZ
 *
 */
public class RandomUtil {

    // 返回 [0-n) 中不重复的m个数，主要用于实现 从某个长度为n的数组中随机得到m个元素组成的新数组
    public static List<Integer> getRandomMFromN(int n, int m) {
	List<Integer> base = new ArrayList<Integer>();
	List<Integer> result = new ArrayList<Integer>();
	for (int i = 0; i <= n; i++) {
	    base.add(i, i);
	}
	int endpos = n > m ? m : n;
	for (int j = 0; j < endpos; j++) {
	    int size = base.size();
	    double tmp = Math.ceil(Math.random() * (size - 1));
	    int pos = (int) tmp;
	    result.add(base.get(pos) - 1);
	    base.remove(pos);
	}
	return result;

    }

    /**
     * 返回[min,max]间随机数
     * 
     * @param min
     * @param max
     * @return
     */
    public static int getRangedRandom(int min, int max) {
	min = min - 1;
	max = max - min;
	return RandomUtil.getRandomOneToN(max) + min;
    }

    // 返回[1-n]间的一个随机整数
    private static int getRandomOneToN(int n) {
	double tmp = Math.ceil(Math.random() * n);
	return (int) tmp;
    }

}
