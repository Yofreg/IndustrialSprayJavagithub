package com.goockr.pmj.utils;

import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author yofreg
 * @time 2018/6/17 21:13
 * @desc ${TODD}
 */
public class LockUtils {

    private static Lock lock;

    public static void add(List list, String aa) {
        lock.lock();
        try {
            list.add(aa);
        } finally {
            lock.unlock();
        }
    }

    public static void removeData(List list, String aa) {
        lock.lock();
        try {
            list.remove(aa);
        } finally {
            lock.unlock();
        }
    }
}
