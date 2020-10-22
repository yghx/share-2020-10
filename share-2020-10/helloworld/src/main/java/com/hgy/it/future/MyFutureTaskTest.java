package com.hgy.it.future;

public class MyFutureTaskTest {
    public static void main(String[] args) {
        System.out.println("start");
        System.out.println(new MyFutureTaskTest().getUserInfo());
    }
    public String getUserInfo() {
        MyFutureTask<String> userTask = new MyFutureTask<>(() -> "userCall");
        MyFutureTask<String> moneyTask = new MyFutureTask<>(() -> "moneyCall");
        new Thread(userTask,"userTask").start();
        new Thread(moneyTask,"moneyTask").start();
        StringBuilder result = new StringBuilder();
        try {
            System.out.println("get user");
            result.append(userTask.get()).append(" | "); // 阻塞
            System.out.println("get money");
            result.append(moneyTask.get());
            System.out.println("get done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result.toString();
    }
}

