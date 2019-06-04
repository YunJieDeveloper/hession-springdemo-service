package com.hession.services.springdemo.threadlocal;

/**
 * @program: hession-springdemo-service
 * @description: ThreadLocal使用工具类
 * @author: Hession
 * @create: 2019-06-04 22:40
 **/

/**
 * @author hession
 * ThreadLocal工具类
 * 可直接做静态工具类使用
 *
 * 测试案例 CommonThreadLocalTest
 */
public class CommonThreadLocal {

    /**
     * Object 根据使用场景自定义
     */

    private static ThreadLocal<Object> contextThreadLocal = new ThreadLocal<>();


    public static void set(Object entity) {
        contextThreadLocal.set(entity);
    }

    public static Object get() {
        return contextThreadLocal.get();
    }

    /**
     * threadLocal使用结束后 一定要调用此方法，避免内存泄漏
     */
    public static void remove() {
        contextThreadLocal.remove();
    }
}
