package com.goockr.industrialsprayjava;

import android.app.Application;
import android.content.Context;

import com.goockr.industrialsprayjava.tools.LanguageUtil;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;

import java.util.Locale;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //中英语言
        if (PreferenceUtils.getInstance().language.equals("en")) {
            LanguageUtil.changeAppLanguage(context, Locale.ENGLISH, true);
        } else {
            LanguageUtil.changeAppLanguage(context, Locale.CHINA,true);
        }

        //获取未捕获的异常
        //Thread.currentThread().setUncaughtExceptionHandler(new MyExceptionHander());
    }

    /**
     * 获取全局的context
     * @return 返回全局context对象
     */
    public static Context getContext(){
        return context;
    }

    //错误时写日志
    private class MyExceptionHander implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // Logger.i("MobileSafeApplication", "发生了异常,但是被哥捕获了..");
            /*LogUtils.d("MobileSafeApplication","发生了异常,但是被哥捕获了..");*/
            //并不能把异常消灭掉,只是在应用程序关掉前,来一个留遗嘱的事件

            try {
                //获取手机设备,发生错误的记录
                //才能知道是哪款设备发生什么问题
                /*Field[] fields = Build.class.getDeclaredFields();
                StringBuffer sb = new StringBuffer();
                for(Field field:fields){
                    String value = field.get(null).toString();
                    String name  = field.getName();
                    sb.append(name);
                    sb.append(":");
                    sb.append(value);
                    sb.append("\n");
                }

                //将日志写到sd卡里面
                FileOutputStream out = new FileOutputStream("/mnt/sdcard/error.log");
                StringWriter wr = new StringWriter();
                PrintWriter err = new PrintWriter(wr);
                ex.printStackTrace(err);
                String errorlog = wr.toString();
                sb.append(errorlog);
                out.write(sb.toString().getBytes());
                out.flush();
                out.close();*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            //让出现异常直接闪退
            //?专注自杀,早死早超生
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
