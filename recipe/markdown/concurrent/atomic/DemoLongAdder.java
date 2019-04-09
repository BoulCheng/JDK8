package com.fota.option.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Yuanming Tao
 * Created on 2019/4/8
 * Description
 */
public class DemoLongAdder {

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(new LongAdder());

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
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
        private LongAdder money;


        public Account(LongAdder money) {
            this.money = money;
        }


        public void increMoney() {
            money.increment();
        }



        @Override
        public String toString() {
            return "Account{" +
                    "money=" + money.sum() +
                    '}';
        }
    }
}
