/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cc.ubw.artemis.commom;

import java.util.Map;

/**
 * @author Allen
 * 2019年11月5日 下午3:01:25
 * V1.0
 */
public final class SQLConstant {
    public static final String SQL_LIMIT_STR = "limit";

    public static final String SQL_LIMIT_SIZE_STR = "limit_size";

    public static final int SQL_LIMIT_ALL = -1;

    public static boolean addLimitStr(Map<String, Object> map, int begin, int size) {
        if (map == null) {
            return false;
        }
        if (size != SQLConstant.SQL_LIMIT_ALL) {
            if (size < 0 || begin < 0) {
                return false;
            } else {
                map.put(SQL_LIMIT_STR, begin);
                map.put(SQL_LIMIT_SIZE_STR, size);
            }
        }
        return true;
    }
}
