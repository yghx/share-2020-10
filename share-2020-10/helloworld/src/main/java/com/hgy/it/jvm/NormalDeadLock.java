package com.hgy.it.jvm;

/**
 * 类说明：演示死锁的产生(这里只演示现象--原理在并发编专题课中)
 */
public class NormalDeadLock {

    private static Object ob1 = new Object();//第一个锁
    private static Object ob2 = new Object();//第二个锁

    //第一个拿锁的方法
    private static void peterDo() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (ob1) {
            System.out.println(threadName + " get ob1");
            Thread.sleep(100);
            synchronized (ob2) {
                System.out.println(threadName + " get ob2");
            }
        }
    }

    //第二个拿锁的方法
    private static void kingDo() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (ob2) {
            System.out.println(threadName + " get ob2");
            Thread.sleep(100);
            synchronized (ob1) {
                System.out.println(threadName + " get ob1");
            }
        }
    }

    private static class Peter extends Thread {

        private String name;

        public Peter(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            try {
                peterDo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("th1");
        Peter peter = new Peter("th2");
        peter.start();
        kingDo();
    }

}
