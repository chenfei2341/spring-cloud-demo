/*
 * <pre>
 * 标  题: CommonUtils.java.
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

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.comparator.ComparableComparator;

import java.io.File;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * @author Chenfei
 */
public class CommonUtils {
    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private CommonUtils() {
    }

    public static <E extends Comparable<? super E>> void sort(List<?> list, String key, String desc) {
        try {
            Comparator<?> comparator = ComparableComparator.INSTANCE;
            comparator = ComparatorUtils.nullLowComparator(comparator);
            if ("desc".equals(desc)) {
                comparator = ComparatorUtils.reversedComparator(comparator);
            }
            Collections.sort(list, new BeanComparator<Object>(key, comparator));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static <E extends Comparable<? super E>> void sortChinese(List<?> list, String key, String desc) {
        try {
            Comparator<?> comparator = new Comparator<E>() {
                @Override
                public int compare(E obj1, E obj2) {
                    if (obj1 instanceof Number && obj2 instanceof Number) {
                        return obj1.compareTo(obj2);
                    }
                    // 定义中文语境
                    Collator instance = Collator.getInstance(Locale.CHINA);
                    return instance.compare(obj1, obj2);
                }

            };
            comparator = ComparatorUtils.nullLowComparator(comparator);
            if ("desc".equals(desc)) {
                comparator = ComparatorUtils.reversedComparator(comparator);
            }
            Collections.sort(list, new BeanComparator<Object>(key, comparator));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 组合排序
     */
    public static <E extends Comparable<? super E>> void sortMultiChinese(List<?> list, String desc, String... keys) {
        try {
            Comparator<?> comparator = new Comparator<E>() {
                @Override
                public int compare(E obj1, E obj2) {
                    if (obj1 instanceof Number && obj2 instanceof Number) {
                        return obj1.compareTo(obj2);
                    }
                    // 定义中文语境
                    Collator instance = Collator.getInstance(Locale.CHINA);
                    return instance.compare(obj1, obj2);
                }

            };
            comparator = ComparatorUtils.nullLowComparator(comparator);
            if ("desc".equals(desc)) {
                comparator = ComparatorUtils.reversedComparator(comparator);
            }

            // 创建一个排序链
            ComparatorChain<Object> multiSort = new ComparatorChain<>();
            for (String key : keys) {
                // 批量添加排序规则
                multiSort.addComparator(new BeanComparator<Object>(key, comparator));
            }

            // 开始真正的排序，按照先添加先排序的规则
            Collections.sort(list, multiSort);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static String localLogPath() {
        String logPath = "";
        try {
            Resource resource = new ClassPathResource("log4j2.xml");
            logPath = resource.getURI().getSchemeSpecificPart();
            int index = logPath.indexOf("!/");
            if (index >= 0) {
                logPath = logPath.substring(0, index);
                logPath = ResourceUtils.getFile(logPath).getParent() + File.separatorChar + "logs";
            } else {
                logPath = "/webapp/logs/northbound-api";
            }
            logPath = ResourceUtils.getFile(logPath).getAbsolutePath();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return logPath;
    }
}
