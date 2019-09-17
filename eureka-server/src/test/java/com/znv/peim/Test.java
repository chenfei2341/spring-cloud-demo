/*
 * <pre>
 * 标  题: Test.java.
 * 版权所有: 版权所有(C)2001-2019
 * 公   司: 深圳中兴力维技术有限公司
 * 内容摘要: // 简要描述本文件的内容，包括主要模块、函数及其功能的说明
 * 其他说明: // 其它内容的说明
 * 完成日期: 2019-08-29 // 输入完成日期
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

package com.znv.peim;

/**
 * @author Chenfei
 */
public class Test {
    ThreadLocal<Integer> local = ThreadLocal.withInitial(() -> new Integer(1));

    public static void main(String[] args) {
        ThreadTest t1 = new Test(). new ThreadTest();
        ThreadTest t2 = new Test(). new ThreadTest();
        ThreadTest t3 = new Test(). new ThreadTest();
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
    }

    public class ThreadTest implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Integer j = local.get();
                System.out.println("j: " + j);
                j++;
                local.set(j);
            }
        }
    }
}
