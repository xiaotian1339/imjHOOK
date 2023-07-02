package com.tcgg;

import android.util.Log;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {
    private static final String TAG = "HOOK IMJ";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.beauty.drama")){
            return;
        }
        System.out.println("Hook img");
        ClassLoader classLoader=lpparam.classLoader;
        XposedHelpers.findAndHookMethod("com.television.amj.view.bean.UserBean", classLoader, "getId", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                System.out.println();
                Object thisObject = param.thisObject;
                Class<?> clazz = thisObject.getClass();
                Field vipType = clazz.getDeclaredField("vipType");
                vipType.setAccessible(true);
                vipType.setInt(thisObject,5);
                Field vipHonour = clazz.getDeclaredField("vipHonour");
                vipHonour.setAccessible(true);
                vipHonour.setBoolean(thisObject,true);
                Log.d(TAG, "UserBean: Hook Vip Type To 5;VipHonour To True");
            }
        });
        XposedHelpers.findAndHookMethod("com.television.amj.view.bean.UserBean", classLoader, "getVipType", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(5);
            }
        });

//        XposedHelpers.findAndHookMethod("com.television.amj.application.UserModel", classLoader, "isVip", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                Log.d("HOOK IMJ", "afterHookedMethod: HookVIP");
//                param.setResult(true);
//            }
//        });


    }
}