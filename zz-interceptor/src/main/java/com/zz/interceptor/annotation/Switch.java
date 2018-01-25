package com.zz.interceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangzuizui on 2017/11/7.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Switch {

    /**
     * 兼容复杂的对象组合结构.使用spring表达式实现.
     *
     * @return
     * @WARN 如果使用el表达式取值, 要求:
     * <pre>
     * 1.属性值必须以'#{'开头,'}'结尾;
     * 2.参数列表为多个的时候,需要当成数组来访问.
     * eg: commit(Object object1,Object2 object2);
     * 可以写成 #{[0].field},代表,从第一个参数Object对象中获取field属性.
     * List复杂对象
     *   @Swich(targetField = "#{[0].List[0].jdpin}")
     *    @Swich(targetField="#{[0].userInfo.clientAccount}",bizType="001")
     * </pre>
     */

    /**
     * 业务类型
     * @return
     */
    String bizType() default "";

    /**
     * 目标字段
     * @return
     */
    String targetFieldExpr() default "";

}
