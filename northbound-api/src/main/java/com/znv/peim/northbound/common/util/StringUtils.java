/*
 * <pre>
 * 标  题: StringUtils.java.
 * 版权所有: 版权所有(C)2001-2019
 * 公   司: 深圳中兴力维技术有限公司
 * 内容摘要: // 简要描述本文件的内容，包括主要模块、函数及其功能的说明
 * 其他说明: // 其它内容的说明
 * 完成日期: 2019-09-02 // 输入完成日期
 * </pre>
 * <pre>
 * 修改记录1:
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * @version 1.0
 * @author Chenfei
 */

package com.znv.peim.northbound.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author Chenfei
 */
public class StringUtils {
    private static Logger logger = LoggerFactory.getLogger(StringUtils.class);
    private StringUtils(){

    }

    public static int str2Int(String str) {
        return str2Int(str, 0);
    }

    public static int str2Int(String str, int defValue) {
        int i = defValue;
        if (!org.apache.commons.lang.StringUtils.isBlank(str)) {
            try {
                int dotIdx = str.indexOf(".");
                if (dotIdx > 0) {
                    str = str.substring(0, dotIdx);
                }
                i = Integer.parseInt(str.trim());
            } catch (NumberFormatException e) {
                logger.error("String [{}] parseInt error", str, e);
            }
        }
        return i;
    }

    public static long str2Long(String str) {
        return str2Long(str, 0L);
    }

    public static long str2Long(String str, long defValue) {
        long i = defValue;
        if (!org.apache.commons.lang.StringUtils.isBlank(str)) {
            try {
                int dotIdx = str.indexOf(".");
                if (dotIdx > 0) {
                    str = str.substring(0, dotIdx);
                }
                i = Long.parseLong(str.trim());
            } catch (NumberFormatException e) {
                logger.error("String [\"{}\"] parseLong error", str, e);
            }
        }
        return i;
    }

    public static double str2Double(String str) {
        return str2Double(str, 0D);
    }

    public static double str2Double(String str, double defValue) {
        double i = defValue;
        if (!org.apache.commons.lang.StringUtils.isBlank(str)) {
            try {
                i = Double.parseDouble(str.trim());
            } catch (NumberFormatException e) {
                logger.error("String [\"{}\"] parseLong error", str, e);
            }
        }
        return i;
    }

    public static String numScale(double f, int dotNum) {
        return numScale(f, dotNum, "0");
    }

    public static String numScale(double f, int dotNum, String defaultValue) {
        BigDecimal bg = null;
        try {
            bg = BigDecimal.valueOf(f);
        } catch (Exception e) {
            bg = new BigDecimal(defaultValue);
        }
        return bg.setScale(dotNum, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String numScale(String f, int dotNum) {
        return numScale(f, dotNum, "0");
    }

    public static String numScale(String f, int dotNum, String defaultValue) {
        BigDecimal bg = null;
        try {
            bg = new BigDecimal(f);
        } catch (Exception e) {
            bg = new BigDecimal(defaultValue);
        }
        return bg.setScale(dotNum, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String percent(String m, String n, int dotNum) {
        if (new BigDecimal(m).doubleValue() == 0D) {
            return "0.0";
        }
        BigDecimal numerator = new BigDecimal(n);
        BigDecimal percent = numerator.multiply(new BigDecimal("100"));
        percent = percent.divide(new BigDecimal(m), 4, BigDecimal.ROUND_HALF_UP);
        return percent.setScale(dotNum, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 转义'，避免无法存入数据库
     *
     */
    public static Object escapeSeparator(Object str) {
        try {
            if (str != null && str instanceof String) {
                String arg = (String) str;
                if (!isNullOrEmpty(arg)) {
                    int oLen = arg.length();
                    String arg2 = arg.replaceAll("'", "");
                    int nLen = arg2.length();
                    if ((oLen - nLen) % 2 != 0) {
                        int idx = arg.lastIndexOf("'");
                        arg = arg.substring(0, idx) + "'" + arg.substring(idx);
                    }
                    return arg;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return str;
    }

    /**
     * 判断字符串是否为空或者是否为空字符串.
     * @param source
     * @return
     */
    public static boolean isNullOrEmpty(String source) {
        return source == null || source.trim().length() == 0;
    }

    public static String join(Object obj[]) {
        return join(obj, ",");
    }

    public static String join(Object obj[], String splitString) {
        if (obj == null) {
            return null;
        }
        if(obj.length == 0){
            return "";
        }
        StringBuilder stb = new StringBuilder();
        for (Object o : obj) {
            stb.append(splitString);
            stb.append(o.toString());
        }
        String s = stb.toString();
        return s.substring(splitString.length());
    }

}
