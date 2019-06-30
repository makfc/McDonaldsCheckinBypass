package com.xposed.checkinbypass;

import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Main implements IXposedHookLoadPackage{
    public void handleLoadPackage(final LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("com.mcdonalds.gma.hongkong"))
            return;

        XposedBridge.log("Loaded app: " + lpparam.packageName);

        findAndHookMethod("com.mcdonalds.app.ordering.payment.PaymentPresenter", lpparam.classLoader, "scanQRCode", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                XposedBridge.log("callMethod: finishCheckin()");
                callMethod(param.thisObject,"finishCheckin");
                param.setResult(null);
            }
        });
    }
}
