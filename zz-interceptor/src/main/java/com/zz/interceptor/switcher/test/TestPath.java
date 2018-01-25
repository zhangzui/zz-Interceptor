package com.zz.interceptor.switcher.test;


/**
 * Created by zhangzuizui on 2018/1/24.
 */
public class TestPath {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("interceptor/").getPath());
        System.out.println(TestPath.class.getClassLoader().getResource("interceptor/").toString());
        System.out.println(System.getProperty("user.dir"));
    }
}
