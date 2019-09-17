/*
 * <pre>
 * 标  题: ErrorMessageResult.java.
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

package com.znv.peim.northbound.common.bean;

import com.znv.peim.northbound.common.exception.ResultCodeEnum;

/**
 * @author Chenfei
 */
public class ErrorMessageResult {
    private Integer code;
    private String message;

    public ErrorMessageResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorMessageResult(ResultCodeEnum codeEnum) {
//        this.message = codeEnum.getName();
        this.code = codeEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
