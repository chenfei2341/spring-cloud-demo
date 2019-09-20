package com.znv.peim.northbound;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Chenfei
 */
public class Test {
    public static void main(String[] args) {
        Flag flag = new Test().new Flag();
        new Thread(new Test().new Print(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'}, flag, 1)).start();
        new Thread(new Test().new Print(new char[]{'1', '2', '3', '4', '5', '6', '7', '8'}, flag, 2)).start();
        new Thread(new Test().new Print(new char[]{'!', '@', '#', '$', '%', '^', '&', '*'}, flag, 3)).start();
        LinkedList list = new LinkedList();
        list.add("");
        ArrayList list2 = new ArrayList();
    }

    class Print implements Runnable {
        char[] strs = null;
        Integer idx = null;
        Flag flag = null;

        public Print(char[] strs, Flag flag, Integer idx) {
            this.strs = strs;
            this.flag = flag;
            this.idx = idx;
        }

        @Override
        public void run() {
            int i = 0;
            while (true) {
                synchronized (flag) {
                    // 判断是哪一个线程获取到锁，若非符合规则的线程获取到锁，则等待
                    if (flag.getI() % 3 != idx.intValue() % 3) {
                        try {
                            flag.wait();
                            // 被唤醒后的线程必须重新进入循环，判断是否符合重入规则
                            continue;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int m = i % strs.length;
                    System.out.printf("%s\t%s\t%s:%c\n", flag.getI(), flag.getI() % 3, idx.intValue(), strs[m]);
                    i++;
                    // 输出字符后标志位 +1
                    flag.atomic();
                    // 唤醒所有线程，让他们重新竞争锁
                    flag.notifyAll();
                    // 打印出前5个字母后退出循环
                    if (i == 5) {
                        return;
                    }
                }

            }
        }
    }

    /**
     * 锁对象，用来作为
     */
    class Flag {
        AtomicInteger i = new AtomicInteger(1);

        public int getI() {
            return i.intValue();
        }

        public void atomic() {
            i.incrementAndGet();
        }
    }
}
