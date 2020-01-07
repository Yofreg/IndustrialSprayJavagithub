package com.goockr.industrialsprayjava.tools;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanguageUtil {
    /**
     * 更改应用语言
     *
     * @param context
     * @param locale      语言地区
     * @param persistence 是否持久化
     */
    public static void changeAppLanguage(Context context, Locale locale,
                                         boolean persistence) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        configuration.setLayoutDirection(Locale.ENGLISH);
        resources.updateConfiguration(configuration, metrics);

        if (persistence) {
            if (locale.getLanguage().equals("zh")) {
                saveLanguageSetting("zh");
            } else {
                saveLanguageSetting("en");
            }
        }
    }

    private static void saveLanguageSetting(String language) {
        PreferenceUtils.getInstance().setLanguage(language);
    }

}
