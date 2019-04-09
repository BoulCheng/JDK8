package com.fota.option.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author Yuanming Tao
 * Created on 2019/4/8
 * Description
 */
public class DemoAtomicReferenceFieldUpdater {


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
        private volatile Integer money;
        private static final AtomicReferenceFieldUpdater<Account, Integer> updater = AtomicReferenceFieldUpdater.newUpdater(Account.class, Integer.class, "money");
        // 引入AtomicIntegerFieldUpdater

        Account(int initial) {
            this.money = initial;
        }

        public void increMoney() {
//            // 存在cas失败的情况 所以要加上自旋
//            Integer integer = updater.get(this);
//            boolean b = updater.compareAndSet(this, integer, ++integer);
//            if (b == false) {
//                System.out.println(false);
//            }
            //debug模式可以看到其他线程更新了，导致cas失败的情景
            Integer pre, next;
            do {
                pre = updater.get(this);
                next = pre + 1;
            } while (!updater.compareAndSet(this, pre, next));
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
