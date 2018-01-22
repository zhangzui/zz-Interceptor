//package com.zz.interceptor.switcher.config;
//
//import com.jd.jr.cf.ucc.AbstractUccPathConfig;
//import com.zz.interceptor.utils.SwitchUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Map;
//
///**
// * Created by ChenTiantao on 2016/11/29.
// */
//public class SwitchUccConfigListener extends AbstractUccPathConfig {
//
//    static final Logger logger = LoggerFactory.getLogger(SwitchUccConfigListener.class);
//
//    @Override
//    public void handleConfig(Map<String, String> map) {
//        SwitchUtils.initSwitchConfig(map);
//    }
//
//    @Override
//    public void exceptionCaught(Throwable throwable) {
//        logger.info("", throwable);
//    }
//
//}
