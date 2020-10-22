package com.hgy.it.future;

import java.util.concurrent.*;

public class MyFutureTask<V> implements Runnable, Future<V> {
    private Callable<V> callable;

    private V v;


    public MyFutureTask(final Callable<V> callable) {
        if (callable == null)
            throw new NullPointerException();
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            v = callable.call();
            synchronized (this) {
                this.notifyAll(); // 唤醒
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public V get() throws InterruptedException, ExecutionException {
        synchronized (this) {
            this.wait(); // 阻塞
        }
        return v;
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }


    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
