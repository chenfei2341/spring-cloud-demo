/*
 * <pre>
 * 标  题: ManageController.java.
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

package com.znv.peim.northbound.controller;

import com.znv.peim.northbound.common.bean.PageData;
import com.znv.peim.northbound.service.ManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
@RestController
@RequestMapping("/manage")
public class ManageController {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ManageController.class);

    /**
     * redis引用
     */
//    @Resource
    //	RedisTemplate<String,Object>  redisTemplate;

    /**
     * 服务层接口
     */
    @Autowired
    @Qualifier("manageServiceImpl")
    private ManageService manageService;

    /**
     * 根据查询条件查询服务信息
     * @param id id
     * @param serverName 服务名
     * @param status 服务状态
     * @return 服务列表
     */
    @RequestMapping(value = "/server-list")
    public Object serverList(@RequestParam(required = false, name = "id") String id,
        @RequestParam(required = false, name = "serverName") String serverName,
        @RequestParam(required = false, name = "status") String status, @RequestParam(value = "pageNum", required = false) Integer pageNum,
        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        //TODO  前端请求参数过多请使用对象接收

        //设置分页参数  也可添加排序参数等，具体可看官方文档说明，这里demo是对type降序
        Map<String, Object> recordMap = new HashMap<>(5);
        recordMap.put("id", id);
        recordMap.put("serverName", serverName);
        //保存到REDIS中
        //        redisTemplate.opsForValue().set("recordMap", recordMap);
        List<Map<String, Object>> list = manageService.queryServer(recordMap);

        PageData<Map<String, Object>> pageInfo = new PageData<>();
        pageInfo.setData(list);

        return pageInfo;
    }

    /**
     * 启动服务
     * @param id id
     * @return 启动服务状态结果
     * @throws Exception
     */
    @RequestMapping(value = "/server-start")
    public Object startServer(@RequestParam(required = true, name = "id") String id) throws Exception {
        return manageService.startServer(id);
    }

}
