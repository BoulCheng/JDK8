package com.fota.option.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author Yuanming Tao
 * Created on 2019/4/8
 * Description
 * 单纯从功能上来讲，能用AtomicXXXFieldUpdater实现的并发控制，同步器和其它原子类都能实现，
 * 但是使用AtomicXXXFieldUpdater，符合面向对象设计的一个基本原则——开闭原则，尤其是对一些遗留代码的改造上。
 *
 * 另外，使用AtomicXXXFieldUpdater，不需要进行任何同步处理，单纯的使用CAS+自旋操作就可以实现同步的效果。这也是整个atomic包的设计理念之一。
 *
 * AtomicIntegerFieldUpdater只能处理int原始类型的字段，AtomicLongFieldUpdater只能处理long原始类型的字段，AtomicReferenceFieldUpdater可以处理所有引用类型的字段
 */
public class DemoAtomicIntegerFieldUpdater {

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(0);

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Task(account));
            list.add(t);
            t.start();
        }

        for (Thread t : list) {
            t.join();
        }

        System.out.println(account.toString());
    }

    private static class Task implements Runnable {
        private Account account;

        Task(Account account) {
            this.account = account;
        }

        @Override
        public void run() {
            account.increMoney();
        }
    }

    static class Account {
        private volatile int money;
        private static final AtomicIntegerFieldUpdater<Account> updater = AtomicIntegerFieldUpdater.newUpdater(Account.class, "money");
        // 引入AtomicIntegerFieldUpdater

        Account(int initial) {
            this.money = initial;
        }

        public void increMoney() {
            /**
             * 自旋 + cas
             */
            updater.incrementAndGet(this);
            // 通过AtomicIntegerFieldUpdater操作字段
        }

        public int getMoney() {
            return money;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "money=" + money +
                    '}';
        }
    }
}
