/*
 * <pre>
 * 标  题: PageData.java.
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

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Vector;

/**
 * 分页实体类
 *
 * @param <E>实际数据抽象类
 */
public class PageData<E> {
    /**
     * 总条数
     */
    private int recordsTotal = 0;
    /**
     * 过滤后的总记录数
     */
    private int recordsFiltered = 0;
    /**
     * 当前页
     */
    private int pageNo = 1;
    /**
     * 总页数
     */
    private int totalPages = 0;
    /**
     * 每页数据条数
     */
    private int pageSize = 10;
    /**
     * 起始位置，用于数据库查询
     */
    private int fromIndex = 0;
    /**
     * dataTable的计数器，由页面传来，并原样传回页面
     */
    private int draw = 1;
    /**
     * 排序方式
     */
    private String orderBy = "";
    /**
     * 数据
     */
    private List<E> data;

    public PageData() {

    }

    public PageData(int pageNo) {
        if (pageNo > 0) {
            this.pageNo = pageNo;
        }
    }

    public PageData(int pageNoParam, int pageSizeParam) {
        if (pageSize > 0) {
            this.pageSize = pageSizeParam;
        }

        if (pageNoParam > 0) {
            this.pageNo = pageNoParam;
        }

        this.fromIndex = (this.pageNo - 1) * this.pageSize;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public PageData<E> setRecordsTotal(int totalRecords) {
        this.recordsTotal = totalRecords;
        this.totalPages = this.recordsTotal / this.pageSize;
        if (this.recordsTotal % this.pageSize != 0) {
            ++this.totalPages;
        }

        if (this.recordsTotal == 0) {
            this.data = new Vector<E>();
        } else {
            this.fromIndex = (this.pageNo - 1) * this.pageSize;
        }
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public PageData<E> setPageNo(int pageNo) {

        if (pageNo <= 0) {
            pageNo = 1;
        }

        this.pageNo = pageNo;
        this.fromIndex = (this.pageNo - 1) * this.pageSize;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageData<E> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.fromIndex = (this.pageNo - 1) * this.pageSize;
        return this;
    }

    public List<E> getData() {
        return data;
    }

    public PageData<E> setData(List<E> data) {
        this.data = data;
        return this;
    }

    public int getDraw() {
        return draw;
    }

    public PageData<E> setDraw(int drawParam) {
        this.draw = drawParam;
        return this;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public PageData<E> setOrderBy(String orderBy) {
        if (!StringUtils.isEmpty(orderBy)) {
            this.orderBy = orderBy;
        }
        return this;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

}
