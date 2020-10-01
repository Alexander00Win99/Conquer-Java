package com.conquer_java.knowledge.lock;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DemoReadWriteLock {
    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        new Thread(() -> {
            readWriteLock.readLock().lock();
            try {
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\tstart with read lock!");
                Thread.sleep(9000);
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\tend with read lock!");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readWriteLock.readLock().unlock();
            }
        }, "Thread-01").start();

        new Thread(() -> {
            readWriteLock.readLock().lock();
            try {
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\tstart with read lock!");
                Thread.sleep(9000);
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\tend with read lock!");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readWriteLock.readLock().unlock();
            }
        }, "Thread-02").start();

        new Thread(() -> {
            Lock lock = readWriteLock.writeLock();
            lock.lock();
            try {
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\tstart with write lock!");
                Thread.sleep(9000);
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\tend with write lock!");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "Thread-03").start();
    }
}
