package com.cuining.myapplication.aop;

import android.util.Log;

import com.meb.tracker.TrackerManager;
import com.meb.tracker.TrackerSDK;
import com.meb.tracker.net.CollectBody;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class TestAspect {

    //    @Around("execution( * com.cuining.myapplication.FirstActivity.jumpPage(..))")
    @Before("execution(* com.cuining.myapplication.FirstActivity.jumpPage(..))")
    public void jumpInfo(JoinPoint joinPoint) {
//        System.out.println("------------>切入了");
        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());


//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        String name = signature.getName(); // 方法名：test
//        Method method = signature.getMethod(); // 方法：public void com.lqr.androidaopdemo.FirstActivity.test(android.view.View)
//        Class returnType = signature.getReturnType(); // 返回值类型：void
//        Class declaringType = signature.getDeclaringType(); // 方法所在类名：FirstActivity
//        String[] parameterNames = signature.getParameterNames(); // 参数名：view
//        Class[] parameterTypes = signature.getParameterTypes(); // 参数类型：View
    }

    @Before("execution(@com.cuining.myapplication.aop.TestAnnoTrace * *(..))")
    public void heihei(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 通过Method对象得到切点上的注解
        TestAnnoTrace annotation = method.getAnnotation(TestAnnoTrace.class);
        String value = annotation.haha();
        int type = annotation.type();

        Log.i("helloAOP", "aspect:::value:" + value + "type:" + type);

        CollectBody collectBody = TrackerSDK.getInstance().creatCommonCollect();
        TrackerManager.getInstance().addCollect(collectBody);

    }

}
