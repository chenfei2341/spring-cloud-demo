/*
 * <pre>
 * 标  题: ManageDao.java.
 * 版权所有: 版权所有(C)2001-2019
 * 公   司: 深圳中兴力维技术有限公司
 * 内容摘要: // 简要描述本文件的内容，包括主要模块、函数及其功能的说明
 * 其他说明: // 其它内容的说明
 * 完成日期: 2019-09-05 // 输入完成日期
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

package com.znv.peim.northbound.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Repository
public interface ManageDao {
    /**
     * 根据查询字段查询监控服务所有信息
     * @param recordMap 查询条件
     * @return 查询结果
     */
    List<Map<String,Object>> queryUser(Map<String, Object> recordMap);

    /**
     * 修改服务信息
     * @param recordMap
     */
    void updateUser(Map<String, Object> recordMap);



}
