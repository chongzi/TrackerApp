package com.meb.tracker.aop;

import android.util.Log;

import com.meb.tracker.TrackerManager;
import com.meb.tracker.TrackerSDK;
import com.meb.tracker.net.CollectBody;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class MebAspect {

    @Before("execution(@com.meb.tracker.aop.ClickTrace * *(..))")
    public void clickEvent(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 通过Method对象得到切点上的注解
        ClickTrace annotation = method.getAnnotation(ClickTrace.class);

        String eventId = annotation.eventId();
        Log.i("helloAOP", "aspect:::eventId:" + eventId);
        TrackerSDK.getInstance().addClickEvent("");

    }


    @Before("execution(@com.meb.tracker.aop.NavigationTrace * *(..))")
    public void navigationEvent(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 通过Method对象得到切点上的注解
        NavigationTrace annotation = method.getAnnotation(NavigationTrace.class);

        String fromPageId = annotation.fromPageId();
        String toPageId = annotation.toPageId();
        Log.i("helloAOP", "aspect:::fromPageId:" + fromPageId + "toPageId" + toPageId);

//        CollectBody collectBody = TrackerSDK.getInstance().creatCommonCollect();
//        TrackerManager.getInstance().addCollect(collectBody);

    }
}
