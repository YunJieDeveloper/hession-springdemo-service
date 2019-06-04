package com.hession.services.springdemo.threadlocal;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class CommonThreadLocalTest {

    @Test
    public void test() {
        CommonThreadLocal.set("2311");
        System.out.println(CommonThreadLocal.get());
        CommonThreadLocal.remove();
        System.out.println(CommonThreadLocal.get());

    }

    @Test
    public void testThreadLocal() {
        CommonThreadLocal.set("2311");
        Object o = CommonThreadLocal.get();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {

            //todo 1。特别注意 CommonThreadLocal是线程私有的，异步新线程的时候就获取不到了
            System.out.println(CommonThreadLocal.get());
            //todo 2。可通过参数方式传入
            System.out.println(o);
            CommonThreadLocal.remove();
            System.out.println(CommonThreadLocal.get());
        });
    }
}