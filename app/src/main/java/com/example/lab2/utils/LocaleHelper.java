package com.example.lab2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class LocaleHelper {
    private static final String PREF_LANGUAGE = "app_language";

    // Apply the locale and return the updated context
    public static Context setLocale(Context context, String language) {
        persist(context, language);
        return updateResources(context, language);
    }

    // Persist the selected language in SharedPreferences
    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_LANGUAGE, language);
        editor.apply();
    }

    // Update resources for all API levels
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.setLocale(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            return context;
        }
    }

    public static String getLanguageFromPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LANGUAGE, "en");
    }

    public static Context attachBaseContext(Context context) {
        String language = getLanguageFromPreferences(context);
        return updateResources(context, language);
    }
}
