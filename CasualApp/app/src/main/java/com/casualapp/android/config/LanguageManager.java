package com.casualapp.android.config;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;

public class LanguageManager {

    private static final String PREFS_NAME = "LanguagePrefs";
    private static final String LANGUAGE_KEY = "selected_language";
    private static final String LANG_ENGLISH = "en";
    private static final String LANG_CHINESE = "zh";

    public static void setLanguage(Context context, String languageCode) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(LANGUAGE_KEY, languageCode).apply();
        applyLanguage(context, languageCode);
    }

    public static String getSelectedLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(LANGUAGE_KEY, LANG_ENGLISH);
    }

    public static void applyLanguage(Context context, String languageCode) {
        Locale locale;

        if (LANG_CHINESE.equals(languageCode)) {
            locale = Locale.TRADITIONAL_CHINESE;
        } else {
            locale = Locale.ENGLISH;
        }

        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
        }

        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static void applyLanguageOnStartup(Context context) {
        String savedLanguage = getSelectedLanguage(context);
        applyLanguage(context, savedLanguage);
    }
}
