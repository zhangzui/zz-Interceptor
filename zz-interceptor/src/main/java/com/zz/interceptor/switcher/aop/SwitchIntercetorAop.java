package com.zz.interceptor.switcher.aop;


import com.zz.interceptor.switcher.SwitchManager;
import com.zz.interceptor.annotation.Switch;
import com.zz.interceptor.switcher.bean.SwitchDimension;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by zhangzuizui on 2018/1/10.
 */
@Aspect
@Component
@Order(1000)
public class SwitchIntercetorAop {

    private static Logger logger = LoggerFactory.getLogger(SwitchIntercetorAop.class);

    private static String FIELD_EXPR_OPEN = "#{";
    private static String FIELD_EXPR_CLOSE = "}";

    @Pointcut("@annotation(com.jd.overseas.iou.switcher.annotation.Switch)")
    public void aopPoint() {
    }

    @Around("aopPoint()")
    public Object aroundSwitch(ProceedingJoinPoint joinPoint) throws Throwable {
        long t2 = System.currentTimeMillis();
        boolean result = true;
        //获取注解值
        Switch mySwitch = getSwich(joinPoint);
        //获取注解值
        SwitchDimension switchObject = new SwitchDimension();
        String bizType = mySwitch.bizType();
        switchObject.setBizType(bizType);
        switchObject.setBizType(StringUtils.isBlank(bizType) ? joinPoint.getSignature().getName() : bizType);
        String targetFieldExpr = mySwitch.targetFieldExpr();

        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            String targetFieldValue = null;
            //指定对象的属性
            String temp = fetchValueWithExpr(targetFieldExpr, args);
            if (checkKeyRule(temp)) {
                targetFieldValue = temp;
                switchObject.setPin(targetFieldValue);
            }
            SwitchDimension switchDimension = new SwitchDimension();
            switchDimension.setPin(targetFieldValue);
            switchDimension.setBizType(bizType);
            result = SwitchManager.getSwitchManager().doSwitch(switchDimension);
        }
        logger.debug("aroundSwitch------------------{}ms", System.currentTimeMillis() - t2);
        if (result) {
            logger.info("命中规则，中断方法，业务类型：[{}]",switchObject.getBizType());
            Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();
            return returnType.newInstance();
        } else {
            logger.info("没有命中规则，正常执行，业务类型：[{}]",bizType);
            return joinPoint.proceed();
        }
    }


    /**
     * 使用校验算法，验证key是否是业务需要的key。
     *
     * @param routeFieldValue
     * @return
     */
    private boolean checkKeyRule(String routeFieldValue) {
        return true;
    }

    /**
     * 通过spring的表达式获取对象值
     *
     * @param expressionStr
     * @param obj
     * @return
     * @WARN 如果使用el表达式取值, 要求:
     * 1.属性值必须以'#{'开头,'}'结尾;
     * eg: commit(Loan loan,Plan plan);
     * 可以写成 #{[0].platPin},代表,从第一个参数loan对象中获取platPin属性.
     */
    private String fetchValueWithExpr(String expressionStr, Object obj) {
        expressionStr = StringUtils.substringBetween(expressionStr, FIELD_EXPR_OPEN, FIELD_EXPR_CLOSE);// 去掉标示符号,spring不要这个.
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expressionStr);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setRootObject(obj);
        return exp.getValue(context, String.class);
    }


    private Switch getSwich(JoinPoint jp) throws NoSuchMethodException {
        Method method = getMethod(jp);
        return method.getAnnotation(Switch.class);
    }

    Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        return getClass(jp).getMethod(msig.getName(), msig.getParameterTypes());
    }

    private Class<? extends Object> getClass(JoinPoint jp)
            throws NoSuchMethodException {
        return jp.getTarget().getClass();
    }
}
