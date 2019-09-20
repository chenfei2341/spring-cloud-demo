/*
 * <pre>
 * 标  题: ManageServiceImpl.java.
 * 版权所有: 版权所有(C)2001-2019
 * 公   司: 深圳中兴力维技术有限公司
 * 内容摘要: // 简要描述本文件的内容，包括主要模块、函数及其功能的说明
 * 其他说明: // 其它内容的说明
 * 完成日期: 2019-09-03 // 输入完成日期
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

package com.znv.peim.northbound.service.impl;

import com.znv.peim.northbound.dao.ManageDao;
import com.znv.peim.northbound.service.ManageService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 时智超
 * @ClassName:
 * @Description: 服务层
 * @date 2018/5/18 14:57
 */
@Service("manageServiceImpl")
public class ManageServiceImpl implements ManageService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ManageServiceImpl.class);

    /**
     * dao管理类
     */
    @Resource
    private ManageDao manageDao;


    /**
     *  启动服务
     * @param id id号
     * @return
     */
    @Override
    public Object startServer(String id) throws Exception{
        //TODO 多个参数使用{} ,{} ,{}
        logger.info("启动服务....   服务id={}",id);
        Map<String,Object> recordMap = new HashMap<>(2);
        recordMap.put("id",id);

        try {
            List<Map<String, Object>> list = manageDao.queryUser(recordMap);
            if(!CollectionUtils.isEmpty(list)){
                //TODO 业务逻辑
            }
        } catch (Exception e) {
            //TODO 异常日志说明
            logger.error("异常说明xxx",e);
//            recordMap.put("status","-1");
//            manageDao.updateServer(recordMap);
//            //TODO 返回状态码 以及状态信息封装
//            throw new BusinessException(ResultCodeEnum.SYSTEMERROR.getCode(), "启动异常");
        }
        logger.info("服务启动成功....   服务id={}",id);
        return new String("启动成功");
    }

    @Override
    public List<Map<String,Object>> queryServer(Map<String, Object> recordMap){
        List<Map<String,Object>> list = new ArrayList<>();
        recordMap.put("port", "9621");
        list.add(recordMap);
    	return  list;
    }

}
