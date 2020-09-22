package com.lottery.biying.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.util.ArrayMap;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by Administrator on 2018/1/23.
 */
public class Test {
    public static final String TAG = "Test";
    public static int i = 0;
    public static DexClassLoader mClassLoader;

    public Test(Activity activity) {
        Log.d(TAG, "替换之前系统的classLoader  "+Environment.getExternalStorageDirectory().getAbsolutePath());
        showClassLoader(activity);
        try {
            String cachePath = activity.getCacheDir().getAbsolutePath();
            String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/app-debug.apk" ;
            mClassLoader = new DexClassLoader(apkPath, cachePath, cachePath, activity.getClassLoader());
            loadApkClassLoader(mClassLoader,activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "替换之后系统的classLoader");
        showClassLoader(activity);
        inject(mClassLoader);
        Class clazz = null;
        try {
            clazz = mClassLoader.loadClass("biying.lottery.com.test.MainActivity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }


    @SuppressLint("NewApi")
    public void loadApkClassLoader(DexClassLoader loader, Activity activity) {
        try {
            Object currentActivityThread = ReflectHelper.invokeMethod("android.app.ActivityThread", "currentActivityThread", new Class[]{}, new Object[]{});
            String packageName = activity.getPackageName();
            ArrayMap mpackages = (ArrayMap) ReflectHelper.getField("android.app.ActivityThread", "mPackages", currentActivityThread);
            WeakReference wr = (WeakReference) mpackages.get(packageName);
            Log.e(TAG, "mClassLoader:" + wr.get());
            ReflectHelper.setField("android.app.LoadedApk", "mClassLoader", wr.get(), loader);
            Log.e(TAG, "load:" + loader);
        } catch (Exception e) {
            Log.e(TAG, "load apk classloader error:" + Log.getStackTraceString(e));
        }
    }


    /**
     * 打印系统的classLoader
     */
    public void showClassLoader(Activity activity) {
        ClassLoader classLoader = activity.getClassLoader();
        if (classLoader != null) {
            Log.i(TAG, "[onCreate] classLoader " + i + " : " + classLoader.toString());
            while (classLoader.getParent() != null) {
                classLoader = classLoader.getParent();
                Log.i(TAG, "[onCreate] classLoader " + i + " : " + classLoader.toString());
                i++;
            }
        }
    }


    private void inject(DexClassLoader loader){

        PathClassLoader pathLoader = (PathClassLoader)Test.class.getClassLoader();
        try {
            Object dexElements = combineArray(
                    getDexElements(getPathList(pathLoader)),
                    getDexElements(getPathList(loader)));
            Object pathList = getPathList(pathLoader);
            setField(pathList, pathList.getClass(), "dexElements", dexElements);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Object getPathList(Object baseDexClassLoader)
            throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        ClassLoader bc = (ClassLoader)baseDexClassLoader;
        return getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    private static Object getField(Object obj, Class<?> cl, String field)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    private static Object getDexElements(Object paramObject)
            throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        return getField(paramObject, paramObject.getClass(), "dexElements");
    }
    private static void setField(Object obj, Class<?> cl, String field,
                                 Object value) throws NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {

        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        localField.set(obj, value);
    }
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }
}
